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

        // Ajouter au filter pour cause mémoire.
        add("31");
        add("32");
        add("33");
        add("34");
        add("35");
        add("78"); //"dorceltv.png");
        add("79"); //"ducotedechezvoustv.png");
        add("92"); //"june.png");
        add("125"); //"jetix.png");
        add("130"); //"ladeux.png");
        add("131"); //"laune.png");
        add("141"); //"machainesport.png");
        add("180"); //"orangecinegeants.png");
        add("181"); //"orangecinechoc.png");
        add("182"); //"orangecinehappy.png");
        add("183"); //"orangecinemax.png");
        add("194"); //"playhousedisney.png");
        add("195"); //"plugrtl.png");
        add("204"); //"sportplus.png");
        add("209"); //"teleguadeloupe.png");
        add("210"); //"teleguyane.png");
        add("213"); //"telemartinique.png");
        add("216"); //"telenouvellecaledonie.png");
        add("217"); //"telepolynesie.png");
        add("218"); //"telereunion.png");
        add("222"); //"telif.png");
        add("235"); //"tsr1.png");
        add("236"); //"tsr2.png");
        add("275"); //"2mmonde.png");
        add("276"); //"piwi.png");
        add("278"); //"3sat.png");
        add("279"); //"bloombergtv.png");
        add("281"); //"yachtsail.png");
        add("290"); //"babyfirst.png");
        add("293"); //"cap24.png");
        add("295"); //"etb1.png");
        add("296"); //"etb2.png");
        add("298"); //"m6musicclub.png");
        add("299"); //"toutelhistoire.png");
        add("301"); //"ard.png");
        add("303"); //"zdf.png");
        add("304"); //"dsf.png");
        add("401"); //"bbcone.png");
        add("402"); //"bbctwo.png");
        add("403"); //"bbcprime.png");
        add("511"); //"rtbfsat.png");
        add("514"); //"rtltvi.png");
        add("601"); //"tve1.png");
        add("602"); //"tve2.png");
        add("603"); //"tveinternational.png");
        add("701"); //"raiuno.png");
        add("702"); //"raidue.png");
        add("703"); //"raitre.png");
        add("811"); //"latrois.png");

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

    public static void updateFromCron(String currentDate) throws JAXBException, IOException {
        synchronized (jeton) {
            long startTime = System.currentTimeMillis();

            JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
            Unmarshaller um = jc.createUnmarshaller();
            MemcacheService service = MemcacheServiceFactory.getMemcacheService();

            List<ChannelForMemCache> channels = (List<ChannelForMemCache>) service.get(currentDate + "_channels");
            if (channels == null) {
                TvForMemCache tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileForChannels()));

                Iterator<ChannelForMemCache> itChannel = tv.getChannel().iterator();
                while (itChannel.hasNext()) {
                    if (channelFilterred.contains(itChannel.next().getId())) {
                        itChannel.remove();
                    }
                }
                channels = tv.getChannel();
                service.put(currentDate + "_channels", channels, Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));
            }


            for (ChannelForMemCache channel : channels) {
                logger.info("Récupération des programmes de la chaine " + channel.getDisplayName());
                List<ProgrammeForMemCache> programmes = (List<ProgrammeForMemCache>) service.get(currentDate + "_programmes_" + channel.getId());
                if (programmes == null) {
                    TvForMemCache tvWithProgrammes = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileProgrammeForOneChannel(channel.getId())));
                    programmes = tvWithProgrammes.getProgramme();
                    service.put(currentDate + "_programmes_" + channel.getId(), programmes, Expiration.byDeltaMillis((int) TimeUnit.DAYS.toMillis(2)));
                }
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime > TimeUnit.SECONDS.toMillis(40)) {
                    break;
                }
            }

        }
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
