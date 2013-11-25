package fr.ybo.services;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {

    private static Map<String, DataService<?>> services = new HashMap<String, DataService<?>>(){{
        put("channel", new ChannelService());
        put("programme", new ProgrammeService());
    }};


    public static DataService<?> getService(String service) {
        return services.get(service);
    }

    public static Object callService(DataService service, String method, String...parameters) throws ServiceExeption {
        if ("GET".equals(method)) {
            // GET : on a soit le getAll, soit le getBy
            switch (parameters.length) {
                case 0:
                    return service.getAll();
                case 1:
                    return service.getById(parameters[0]);
                case 2:
                    return service.getBy(parameters[0], parameters[1]);
                default:
                    return service.get(parameters);
            }
        }

        return null;
    }


}
