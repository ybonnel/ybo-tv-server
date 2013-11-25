package fr.ybo.services;

import fr.ybo.modele.Channel;
import fr.ybo.modele.Programme;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelService extends DataService<Channel> {

    @SuppressWarnings("unchecked")
    @Override
    public List<Channel> getAll() throws ServiceExeption {
        List<Channel> channels = new ArrayList<Channel>();

        for (Channel channel : JongoService.INSTANCE.getCollection("channels").find().as(Channel.class)) {
            channels.add(channel);
        }
        Collections.sort(channels);
        return channels;
    }

    @Override
    public Channel getById(String key) throws ServiceExeption {
        return JongoService.INSTANCE.getCollection("channels").findOne(new ObjectId(key)).as(Channel.class);
    }

    @Override
    public List<Channel> getBy(String parameterName, String parameterValue) throws ServiceExeption {
        if ("id".equals(parameterName)) {
            return Collections.singletonList(getById(parameterValue));
        }
        if ("date".equals(parameterName)) {
            List<Channel> returnChannels = new ArrayList<Channel>();

            for (Programme programme : JongoService.INSTANCE.getCollection("programmes").find("{start:{$lte:#},stop:{$gte:#}}", parameterValue, parameterValue).as(Programme.class)) {
                Channel channel = JongoService.INSTANCE.getCollection("channels").findOne("{id:#}", programme.getChannel()).as(Channel.class);
                channel.setCurrentProgramme(programme);
                returnChannels.add(channel);
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
