package fr.ybo.services;

import fr.ybo.modele.Programme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ProgrammeService extends DataService<Programme> {

    @SuppressWarnings("unchecked")
    @Override
    public List<Programme> getAll() throws ServiceExeption {
        throw new RuntimeException("Programme.getAll is not accessible");
    }

    @Override
    public Programme getById(String id) throws ServiceExeption {
        throw new RuntimeException("Programme.getId is not accessible");
    }

    private List<Programme> getByChannel(String channel) {
        List<Programme> programmes = new ArrayList<Programme>();
        for (Programme programme : JongoService.INSTANCE.getCollection("programmes").find("{channel:#}", channel).as(Programme.class)) {
            programmes.add(programme);
        }
        return programmes;
    }

    @Override
    public List<Programme> getBy(String parameterName, String parameterValue) throws ServiceExeption {
        if ("id".equals(parameterName)) {
            return Collections.singletonList(getById(parameterValue));
        }
        if ("channel".equals(parameterName)) {
            return getByChannel(parameterValue);
        }
        return null;
    }

    private Collection<Programme> getByChannelAndDate(String channel, String date) {
        List<Programme> programmes = new ArrayList<Programme>();
        for (Programme programme : JongoService.INSTANCE.getCollection("programmes").find(
                "{channel:#,start:#}",
                channel,
                Pattern.compile('^' + date)).as(Programme.class)) {
            programmes.add(programme);
        }
        return programmes;
    }

    private List<Programme> getByChannelAndBetweenDate(String channel, String dateDebut, String dateFin) {

        List<Programme> programmes = new ArrayList<Programme>();
        for (Programme programme : JongoService.INSTANCE.getCollection("programmes").find(
                "{channel:#,start:{$lte:#},stop:{$gte:#}}",
                channel, dateFin, dateDebut).as(Programme.class)) {
            programmes.add(programme);
        }
        return programmes;

    }


    @Override
    public List<Programme> get(String... parameters) throws ServiceExeption {
        if (parameters.length == 4) {
            if ("channel".equals(parameters[0])
                    && "date".equals(parameters[2])) {
                return new ArrayList<Programme>(getByChannelAndDate(parameters[1], parameters[3]));

            }
        } else if (parameters.length == 6) {
            if ("channel".equals(parameters[0])
                    && "datedebut".equals(parameters[2])
                    && "datefin".equals(parameters[4])) {
                return new ArrayList<Programme>(getByChannelAndBetweenDate(parameters[1], parameters[3], parameters[5]));
            }
        }
        return null;
    }


}
