package com.bingo.servlet;

import com.bingo.util.HttpUtil;
import com.bingo.util.ParamGetter;
import org.apache.ibatis.session.SqlSession;
import org.eclipse.jetty.util.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangbing on 15/9/9.
 */
public class HomePageServlet extends BaseServletWithSession{

    private static final Logger LOG = LoggerFactory.getLogger(HomePageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp,
                         SqlSession sqlSession) throws ServletException, IOException {

        LOG.info(req.getRequestURI() + "?" + req.getQueryString());

        ParamGetter paramGetter = new ParamGetter(req);

        long source = paramGetter.getLong("source");
        int opt = paramGetter.getInt("opt");

        HttpUtil.sendSuccess(resp, "OK");
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp,
                          SqlSession sqlSession) throws ServletException, IOException {

    }
}
