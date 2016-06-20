package com.bingo.servlet;

import com.bingo.exception.ErrorCode;
import com.bingo.exception.WeMediaException;
import com.bingo.util.HttpUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bingo.util.IPUtil;
import com.bingo.util.MyBatisUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangbing on 16/6/20.
 */
public abstract class BaseServletWithSession extends HttpServlet{

    private static final Logger LOG = LoggerFactory.getLogger(BaseServletWithSession.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.info("Request: " + req.getRequestURI() + " IP:" + IPUtil.getIP(req) + " Input:" + req.getParameterMap());

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            this.doGet(req, resp, sqlSession);
        } catch (WeMediaException e) {
            HttpUtil.sentFiled(resp, e.getError(), e.getMessage());
        } catch (Exception e) {
            HttpUtil.sentFiled(resp, ErrorCode.GeneralError, e.getMessage());
        } finally {
            sqlSession.close();
            LOG.info("Success Return");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.info("Request: " + req.getRequestURI() + " IP:" + IPUtil.getIP(req) + " Input:" + req.getParameterMap());

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            this.doPost(req, resp, sqlSession);
        } catch (WeMediaException e) {
            HttpUtil.sentFiled(resp, e.getError(), e.getMessage());
        } catch (Exception e) {
            HttpUtil.sentFiled(resp, ErrorCode.GeneralError, e.getMessage());
        } finally {
            sqlSession.close();
            LOG.info("Success Return");
        }
    }

    protected abstract void doGet(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  SqlSession sqlSession) throws ServletException, IOException;

    protected abstract void doPost(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   SqlSession sqlSession) throws ServletException, IOException;

}
