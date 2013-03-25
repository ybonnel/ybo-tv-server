package fr.ybo.services;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.ComplexKey;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewRow;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ybo.modele.Channel;
import fr.ybo.modele.Programme;

import java.io.IOException;
import java.util.*;

public class ChannelService extends DataService<Channel> {

    @SuppressWarnings("unchecked")
    @Override
    public List<Channel> getAll() throws ServiceExeption {
        try {
            List<Channel> channels = CouchBaseService.INSTANCE.getMapper().readValue((String) CouchBaseService.INSTANCE.getClient().get("channels"),
                    new TypeReference<List<Channel>>() {});
            Collections.sort(channels);
            return channels;
        } catch (IOException ioException) {
            throw new ServiceExeption(ioException);

        }
    }

    @Override
    public Channel getById(String id) throws ServiceExeption {
        Channel channel = null;
        for (Channel oneChannel : getAll()) {
            if (id.equals(oneChannel.getId())) {
                channel = oneChannel;
                break;
            }
        }
        return channel;
    }

    @Override
    public List<Channel> getBy(String parameterName, String parameterValue) throws ServiceExeption {
        if ("id".equals(parameterName)) {
            return Collections.singletonList(getById(parameterValue));
        } else if ("date".equals(parameterName)) {
            List<Channel> returnChannels = new ArrayList<Channel>();
            Map<String, Channel> channelsById = new HashMap<String, Channel>();
            for (Channel channel : getAll()) {
                channelsById.put(channel.getId(), channel);
            }

            try {
                CouchbaseClient client = CouchBaseService.INSTANCE.getClient();
                ObjectMapper mapper = CouchBaseService.INSTANCE.getMapper();

                View view = client.getView("programme", "by_date");
                Query query = new Query();

                query.setRange(ComplexKey.of("00000000000000", parameterValue),
                        ComplexKey.of(parameterValue, "99999999999999"));
                query.setIncludeDocs(true);

                for (ViewRow row : client.query(view, query)) {
                    Programme programme = mapper.readValue((String) row.getDocument(), Programme.class);
                    channelsById.get(programme.getChannel()).setCurrentProgramme(programme);
                }
            } catch (IOException ioException) {
                throw new ServiceExeption(ioException);
            }

            for (Channel channel : channelsById.values()) {
                if (channel.getCurrentProgramme() != null) {
                    returnChannels.add(channel);
                }
            }
            Collections.sort(returnChannels);
            return returnChannels;
        }
        return null;
    }

    @Override
    public List<Channel> get(String... parameters) throws ServiceExeption {
        return null;
    }


}
