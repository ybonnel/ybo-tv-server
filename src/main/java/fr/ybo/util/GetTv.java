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

    @SuppressWarnings("unchecked")
    public static TvForMemCache getCurrentTv() throws JAXBException, IOException {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        MemcacheService service = MemcacheServiceFactory.getMemcacheService();

        TvForMemCache tv = null;
        synchronized (jeton) {

        List<ChannelForMemCache> channels = (List<ChannelForMemCache>) service.get(currentDate + "_channels");

            if (channels == null) {
                tv = getTvFromNetwork(currentDate, service);
            } else {
                tv = new TvForMemCache();
                tv.getChannel().addAll(channels);
                for (ChannelForMemCache channel : tv.getChannel()) {
                    List<ProgrammeForMemCache> programmeForMemCaches = (List<ProgrammeForMemCache>) service.get(currentDate + "_programmes_" + channel.getId());
                    if (programmeForMemCaches == null) {
                        return getTvFromNetwork(currentDate, service);
                    }
                    tv.getProgramme().addAll(programmeForMemCaches);
                }
            }
        }
        return tv;
    }

    private final static Set<String> channelFilterred = new HashSet<String>(){{
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

    private static TvForMemCache getTvFromNetwork(String currentDate, MemcacheService service) throws JAXBException, IOException {
        TvForMemCache tv;JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
        Unmarshaller um = jc.createUnmarshaller();

        tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFile()));

        Iterator<ChannelForMemCache> itChannel = tv.getChannel().iterator();
        while (itChannel.hasNext()) {
            if (channelFilterred.contains(itChannel.next().getId())) {
                itChannel.remove();
            }
        }

        service.put(currentDate + "_channels", tv.getChannel(), Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));
        Map<String, List<ProgrammeForMemCache>> mapProgrammes = new HashMap<String, List<ProgrammeForMemCache>>();

        for (ChannelForMemCache channel : tv.getChannel()) {
            mapProgrammes.put(channel.getId(), new ArrayList<ProgrammeForMemCache>());
        }

        for (ProgrammeForMemCache programme : tv.getProgramme()) {
            if (mapProgrammes.containsKey(programme.getChannel())) {
                mapProgrammes.get(programme.getChannel()).add(programme);
            }
        }

        for (Map.Entry<String, List<ProgrammeForMemCache>> entry : mapProgrammes.entrySet()) {
            logger.info("mise en cache de : " + currentDate + "_programmes_" + entry.getKey());
            service.put(currentDate + "_programmes_" + entry.getKey(), entry.getValue(), Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));
        }
        return tv;
    }
}
