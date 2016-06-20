package com.yidian.wemedia;

import com.typesafe.config.Config;
import com.yidian.wemedia.mail.Mailman;
import com.yidian.wemedia.servlet.ResponseServlet;
import com.yidian.wemedia.servlet.ScanEventServlet;
import com.yidian.wemedia.servlet.ServerConfigCheckerServlet;
import com.yidian.wemedia.util.LogUtil;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

/**
 * Created by zhangbing on 16/5/26.
 */
public class Main {
    private final Mailman mailman;
    private final String alarmEmail = "";

    public Main() {
        mailman = new Mailman(alarmEmail);
    }

    public static void main(String[] args) throws Exception {
        Main service = new Main();

        LogUtil.configureLog4jProp("conf/log4j.properties");
        Config config = ConfigUtil.getInstance();

        try {
            service.run();
        } catch (Exception e) {
            throw e;
        }
    }

    public void run() throws IOException {

        final Server server = new Server();
        server.setGracefulShutdown(1000);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.stop();
                    try {
                        server.join();
                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                }
            }
        }));

        Connector conn = new SelectChannelConnector();
        conn.setHost("0.0.0.0");
        conn.setPort(7777);

        server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
        server.setConnectors(new Connector[]{conn});

        ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
        root.setContextPath("/");
        root.addServlet(new ServletHolder(new ResponseServlet(mailman)), "/callback");
        root.addServlet(new ServletHolder(new ScanEventServlet(mailman)), "/scan-event");
        root.addServlet(new ServletHolder(new ServerConfigCheckerServlet(mailman)), "/server-checker");

        HandlerList lists = new HandlerList();
        lists.setHandlers(new Handler[] { root });

        server.setHandler(lists);

        try {
            server.start();
        } catch (Exception e) {
        }
    }
}
