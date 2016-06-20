package com.bingo;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.bingo.servlet.HomePageServlet;
import com.typesafe.config.Config;
import com.yidian.wemedia.ConfigUtil;
import com.yidian.wemedia.convertor.LogUtil;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhangbing on 16/6/20.
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Parameter(description="help", names={"--help","-help"})
    private boolean help = false;

    @Parameter(description="Service Host", names="-host")
    private String host  = "0.0.0.0";

    @Parameter(description="Port Number", names="-port")
    private final int port;

    private final Config props;

    public Main(Config props) {
        this.props = checkNotNull(props, "props");
        port = props.getInt("port");
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Signal.handle(new Signal("INT"), new QuitSignalHandler());

        Main service = null;

        try {
            LogUtil.configureLog4jProp("log4j.properties");
            Config config = ConfigUtil.getInstance();
            LOG.info(config.getString("SERVER_PORT"));

            service = new Main(config);
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            System.exit(-1);
        }

        JCommander commander = new JCommander(service);
        try {
            commander.parse(args);
        } catch (ParameterException e) {
            LOG.error(e.getMessage());
            commander.usage();
            System.exit(1);
        }
        if ( service.help ){
            commander.usage();
        }else{
            try {

                service.run();

            } catch (Exception e){
                LOG.error("", e);
                throw e;
            }
        }
    }

    public void run() throws IOException {

        final Server server = new Server();
        server.setGracefulShutdown(1000);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.info("stoping the service...");
                    server.stop();
                    try {
                        server.join();
                    } catch (Exception e) {
                        LOG.error("", e);
                    }

                    LOG.info("shutdown service");
                } catch (Exception e) {
                    LOG.error("", e);
                }
            }
        }));

        Connector conn = new SelectChannelConnector();
        conn.setHost(host);
        conn.setPort(port);

        server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", -1);
        server.setConnectors(new Connector[]{conn});

        ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
        root.setContextPath("/");
        root.addServlet(new ServletHolder(new HomePageServlet()), "/homepage");

        HandlerList lists = new HandlerList();
        lists.setHandlers(new Handler[] { root });

        server.setHandler(lists);

        try {
            server.start();

        } catch (Exception e) {
            LOG.error("", e);
        }
    }

    private static final class QuitSignalHandler implements SignalHandler {
        @Override
        public void handle(Signal signal) {
            LOG.info("receive INT signal, quit program...");
            System.exit(0);
        }
    }
}
