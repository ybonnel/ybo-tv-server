package fr.ybo;

import fr.ybo.services.CouchBaseService;
import org.mortbay.jetty.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.text.SimpleDateFormat;
import java.util.Date;


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
            CouchBaseService.INSTANCE.stopCouchbaseClient();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(60);
        }
    }
}
