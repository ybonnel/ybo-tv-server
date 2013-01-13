package fr.ybo.cron;

import com.google.common.base.Throwables;
import fr.ybo.util.GetTv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UpdateServer {

    private final static Logger logger = LoggerFactory.getLogger(UpdateServer.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("UpdateServer - begin");
        try {
            long startTime = System.currentTimeMillis();
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            GetTv.updateFromCron(currentDate);
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime < TimeUnit.SECONDS.toMillis(10)) {
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.add(Calendar.DAY_OF_YEAR, 1);
                currentDate = new SimpleDateFormat("yyyyMMdd").format(currentCalendar.getTime());
                GetTv.updateFromCron(currentDate);
            }
            response.setStatus(200);
            PrintWriter writer = response.getWriter();
            writer.print("OK");
            writer.close();
        } catch (JAXBException e) {
            Throwables.propagate(e);
        }
        logger.info("Update Servlet - end");
    }
}
