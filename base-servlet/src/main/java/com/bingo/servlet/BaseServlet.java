package com.bingo.servlet;

import com.bingo.exception.BaseException;
import com.bingo.exception.WarningException;
import com.yidian.wemedia.mail.Mailman;
import com.yidian.wemedia.mail.WarningMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by zhangbing on 15/9/25.
 */
public class BaseServlet extends HttpServlet{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final Mailman mailman;

    public BaseServlet(Mailman mailman){

        checkNotNull(mailman, "mailman");

        this.mailman = mailman;
    }

    private long startTimeStamp = 0;
    private long endTimeStamp = 0;

    public void markStart(){
        startTimeStamp = System.currentTimeMillis();
    }

    public void markEnd(){
        endTimeStamp = System.currentTimeMillis();
    }

    public long getElapse(){
        return endTimeStamp - startTimeStamp;
    }

    public void printElapse(){
        System.out.println(endTimeStamp - startTimeStamp);
    }

    /**
     */
    private static final long serialVersionUID = -1365413910549690751L;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;

        if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;

        try {
            service(request, response);
        } catch (WarningException e) {
            // warning com.yidian.exception, send warning email
            String errorMsg = "Error happened while executing request: " + request.getRequestURI() + ", params: "
                    + request.getParameterMap();
            logger.error(errorMsg);
            logger.error(e.getMessage(), e);
            logger.error("sending warning email");

            mailman.sendMail(new WarningMail(errorMsg, e));

        } catch (BaseException e) { // don't log trace info for explicitly
            // thrown com.yidian.exception
            logger.error("Error happened while executing request: " + request.getRequestURI() + ", params: "
                    + request.getParameterMap());
            logger.error(e.getMessage());

        } catch (Exception e) { // we could actually hide the output message
            logger.error("Error happened while executing request: " + request.getRequestURI() + ", params: "
                    + request.getParameterMap());
            logger.error(e.getMessage(), e);
        }
    }
}
