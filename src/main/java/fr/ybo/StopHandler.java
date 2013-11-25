package fr.ybo;

import fr.ybo.services.JongoService;
import org.mortbay.jetty.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;


public class StopHandler implements SignalHandler {

    private static final Logger logger = LoggerFactory.getLogger(StopHandler.class);

    private Server server;

    public StopHandler(Server server) {
        this.server = server;
    }

    @Override
    public void handle(Signal signal) {
        logger.info("stoping");
        try {
            server.stop();
            JongoService.INSTANCE.stopJongo();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(60);
        }
    }
}
