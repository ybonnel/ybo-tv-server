package fr.ybo.util;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import fr.ybo.modele.ChannelForMemCache;
import fr.ybo.modele.ProgrammeForMemCache;
import fr.ybo.modele.TvForMemCache;
import fr.ybo.xmltv.Tv;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class GetTv {

    private final static Object jeton = new Object();

    private final static Logger logger = Logger.getLogger(GetTv.class);

    public static List<ChannelForMemCache> getCurrentChannels() throws JAXBException, IOException {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        MemcacheService service = MemcacheServiceFactory.getMemcacheService();
        List<ChannelForMemCache> channels = (List<ChannelForMemCache>) service.get(currentDate + "_channels");

        if (channels == null) {
            channels = GetTv.getTvFromNetworkAndReturnChannels(currentDate, service);
        }

        return channels;
    }

    public static List<ProgrammeForMemCache> getCurrentProgrammes(String channelId) throws JAXBException, IOException {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        MemcacheService service = MemcacheServiceFactory.getMemcacheService();

        List<ProgrammeForMemCache> programmes = (List<ProgrammeForMemCache>) service.get(currentDate + "_programmes_" + channelId);

        if (programmes == null) {
            programmes = GetTv.getTvFromNetworkAndReturnProgramme(currentDate, service, channelId);
        }
        return programmes;
    }

    private final static Set<String> channelFilterred = new HashSet<String>() {{
        add("40");
        add("49");
        add("55");
        add("63");
        add("64");
        add("90");
        add("91");
        add("123");
        add("124");
        add("132");
        add("160");
        add("161");
        add("163");
        add("170");
        add("185");
        add("211");
        add("234");
        add("240");
        add("249");
        add("250");
        add("279");
        add("282");
        add("289");
        add("297");
        add("302");
        add("304");
        add("901");
    }};

    private static List<ChannelForMemCache> getTvFromNetworkAndReturnChannels(String currentDate, MemcacheService service) throws JAXBException, IOException {
        TvForMemCache tv;
        synchronized (jeton) {
            JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
            Unmarshaller um = jc.createUnmarshaller();

            tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileForChannels()));

            Iterator<ChannelForMemCache> itChannel = tv.getChannel().iterator();
            while (itChannel.hasNext()) {
                if (channelFilterred.contains(itChannel.next().getId())) {
                    itChannel.remove();
                }
            }

            service.put(currentDate + "_channels", tv.getChannel(), Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));

            for (ChannelForMemCache channel : tv.getChannel()) {
                logger.info("Récupération des programmes de la chaine " + channel.getDisplayName());
                TvForMemCache tvWithProgrammes = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileProgrammeForOneChannel(channel.getId())));
                service.put(currentDate + "_programmes_" + channel.getId(), tvWithProgrammes.getProgramme(), Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));
            }
        }
        return tv.getChannel();
    }

    private static List<ProgrammeForMemCache> getTvFromNetworkAndReturnProgramme(String currentDate, MemcacheService service, String channelId) throws JAXBException, IOException {
        TvForMemCache returnTv = null;
        synchronized (jeton) {
            JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
            Unmarshaller um = jc.createUnmarshaller();

            TvForMemCache tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileForChannels()));

            Iterator<ChannelForMemCache> itChannel = tv.getChannel().iterator();
            while (itChannel.hasNext()) {
                if (channelFilterred.contains(itChannel.next().getId())) {
                    itChannel.remove();
                }
            }

            service.put(currentDate + "_channels", tv.getChannel(), Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));


            for (ChannelForMemCache channel : tv.getChannel()) {
                logger.info("Récupération des programmes de la chaine " + channel.getDisplayName());
                TvForMemCache tvWithProgrammes = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileProgrammeForOneChannel(channel.getId())));
                if (channel.getId().equals(channelId)) {
                    returnTv = tvWithProgrammes;
                }
                service.put(currentDate + "_programmes_" + channel.getId(), tvWithProgrammes.getProgramme(), Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));
            }
        }
        return returnTv.getProgramme();
    }
}
