package fr.ybo.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import fr.ybo.services.CacheService;
import fr.ybo.services.DataService;
import fr.ybo.services.ServiceExeption;
import fr.ybo.services.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        Iterable<String> paths = Splitter.on('/').trimResults().omitEmptyStrings().split(req.getPathInfo());

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
            String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

            String jsonResponse = CacheService.getInstance().getJsonResponse(currentDate, req.getPathInfo());

            if (jsonResponse == null) {
                Object result = ServiceFactory.callService(service, req.getMethod(), parameters);

                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                jsonResponse = mapper.writeValueAsString(result);
                CacheService.getInstance().addJsonResponse(currentDate, req.getPathInfo(), jsonResponse);
            }
            resp.getWriter().print(jsonResponse);
        } catch (ServiceExeption serviceExeption) {
            resp.setStatus(500);
            logger.error("ServiceException ", serviceExeption);
            serviceExeption.printStackTrace(resp.getWriter());
        }
    }

}
