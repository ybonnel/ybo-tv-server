package fr.ybo.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import fr.ybo.services.DataService;
import fr.ybo.services.ServiceExeption;
import fr.ybo.services.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

public class DataServer {

    private static Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String accept = req.getHeader("Accept");

        logger.debug("DataServer.goGet");

        if (accept == null) {
            resp.setStatus(404);
            PrintWriter writer = resp.getWriter();
            writer.print("Accept header is missing");
            writer.close();
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        Iterable<String> paths = Iterables.skip(Splitter.on('/').trimResults().omitEmptyStrings().split(req.getPathInfo()), 1);

        String nomService = Iterables.getFirst(paths, null);

        if (nomService == null) {
            resp.setStatus(404);
            return;
        }

        DataService service = ServiceFactory.getService(nomService);
        if (service == null) {
            resp.setStatus(404);
            return;
        }

        String[] parameters = Iterables.toArray(Iterables.skip(paths, 1), String.class);

        try {

            Object result = ServiceFactory.callService(service, req.getMethod(), parameters);

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String jsonResponse = mapper.writeValueAsString(result);
            if (req.getHeader("Accept-Encoding") != null && req.getHeader("Accept-Encoding").contains("gzip")) {
                resp.addHeader("content-encoding", "gzip");
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(resp.getOutputStream())))) {
                    out.write(jsonResponse);
                }
            } else {
                PrintWriter writer = resp.getWriter();
                writer.print(jsonResponse);
                writer.close();
            }
            resp.setStatus(200);
        } catch (ServiceExeption serviceExeption) {
            resp.setStatus(500);
            logger.error("ServiceException ", serviceExeption);
            serviceExeption.printStackTrace(resp.getWriter());
        }
    }

}
