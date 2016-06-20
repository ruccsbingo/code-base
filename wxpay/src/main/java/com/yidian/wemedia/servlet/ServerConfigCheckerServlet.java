package com.yidian.wemedia.servlet;

import com.bingo.servlet.BaseServlet;
import com.yidian.wemedia.bean.ScanEventBean;
import com.yidian.wemedia.convertor.ScanEventConvertor;
import com.yidian.wemedia.mail.Mailman;
import com.yidian.wemedia.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhangbing on 16/5/31.
 */
public class ServerConfigCheckerServlet extends BaseServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ServerConfigCheckerServlet.class);

    public ServerConfigCheckerServlet(Mailman mailman) {
        super(mailman);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(request.getQueryString());
        ScanEventBean scanEventBean = ScanEventConvertor.convert(request.getInputStream());
        LOG.info(scanEventBean.toString());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOG.info(request.getQueryString());
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }
}
