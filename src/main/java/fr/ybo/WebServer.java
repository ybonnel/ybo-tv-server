package fr.ybo;


import fr.ybo.services.ChannelService;
import fr.ybo.services.ServiceExeption;
import fr.ybo.services.ServiceFactory;
import fr.ybo.web.DataServer;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebServer extends AbstractHandler {

    private DataServer dataServer = new DataServer();

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);


    @Override
    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
        String path = httpServletRequest.getPathInfo();

        if (path.startsWith("/data/")) {
            dataServer.doGet(httpServletRequest, httpServletResponse);
        } else if (path.equals("/status")) {
            try {
                ServiceFactory.getService("channel").getAll();
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                PrintWriter writer = httpServletResponse.getWriter();
                writer.print("OK");
                writer.close();
            } catch (ServiceExeption serviceExeption) {
                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                PrintWriter writer = httpServletResponse.getWriter();
                writer.println("KO");
                serviceExeption.printStackTrace(writer);
                writer.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        logger.info("ybo-tv-server starting");
        int defaultPort = 9080;
        if (args.length == 1) {
            defaultPort = Integer.parseInt(args[0]);
        }

        Server server = new Server(defaultPort);

        Signal.handle(new Signal("TERM"), new StopHandler(server));

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource publicResources = Resource.newClassPathResource("/public");
        resourceHandler.setBaseResource(publicResources);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{new WebServer(), resourceHandler});
        server.setHandler(handlers);

        server.start();
        server.join();

        logger.info(":ybo-tv-server stopped");
    }
}
