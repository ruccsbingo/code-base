import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * Created by zhangbing on 16/5/30.
 */
public class HttpsServer {

    public static void main(String[] args) throws Exception {
        runHttps();
    }

    public static void runHttps() throws Exception {

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9999);

        HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(HttpsServer.class.getResource("keystore.jks").toExternalForm());
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");

        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        sslConnector.setPort(9998);

        server.setConnectors(new Connector[]{connector, sslConnector});

        ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
        root.setContextPath("/");
        root.addServlet(new ServletHolder(new ExampleServlet()), "/example");

        HandlerList lists = new HandlerList();
        lists.setHandlers(new Handler[]{root});

        server.setHandler(lists);

        server.start();
        server.join();
    }

//    public static void runHttps() throws Exception {
//        // 1. Creating the server on port 8080
//        int port = 9898;
//        Server server = new Server(port);
//
//        HttpConfiguration https_config = new HttpConfiguration();
//        https_config.setSecureScheme("https");
//        https_config.setSecurePort(port);
//        https_config.setOutputBufferSize(32768);
//        https_config.addCustomizer(new SecureRequestCustomizer());
//
//        SslContextFactory sslContextFactory = new SslContextFactory();
//        sslContextFactory.setKeyStorePath("keystore");
//        sslContextFactory.setKeyStorePassword("OBF:1lby1iz01mpn1lqa1lto1mtf1j1u1lfk");
//        sslContextFactory.setKeyManagerPassword("OBF:1lby1iz01mpn1lqa1lto1mtf1j1u1lfk");
//
//        ServerConnector httpsConnector = new ServerConnector(server,
//                new SslConnectionFactory(sslContextFactory,"http/1.1"),
//                new HttpConnectionFactory(https_config));
//        httpsConnector.setPort(port);
//        httpsConnector.setIdleTimeout(500000);
//        server.addConnector(httpsConnector);
//
//        // 2. Creating the WebAppContext for the created content
//        WebAppContext ctx = new WebAppContext();
////        ctx.setResourceBase("src/main/webapp");
//        ctx.setContextPath("/jetty-jsp-example");
//        ctx.setResourceBase("webapp");
//
//        //3. Including the JSTL jars for the webapp.
//        ctx.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",".*/[^/]*jstl.*\\.jar$");
//
//        //4. Enabling the Annotation based configuration
//        org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
//        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
//        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");
//
//        //5. Setting the handler and starting the Server
//        server.setHandler(ctx);
//        server.start();
//        server.join();
//    }
}
