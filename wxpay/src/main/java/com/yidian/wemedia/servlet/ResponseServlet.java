package com.yidian.wemedia.servlet;

import com.bingo.servlet.BaseServlet;
import com.bingo.util.JsonResponseBuilder;
import com.yidian.wemedia.mail.Mailman;
import org.eclipse.jetty.util.IO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangbing on 16/5/26.
 */
public class ResponseServlet extends BaseServlet {

    public ResponseServlet(Mailman mailman) {
        super(mailman);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JsonResponseBuilder builder = JsonResponseBuilder.builder(resp);

        IO.copy(req.getInputStream(), System.out);
    }
}
