package fr.ybo.util;

import fr.ybo.modele.ChannelForMemCache;
import fr.ybo.modele.ProgrammeForMemCache;
import fr.ybo.modele.TvForMemCache;
import fr.ybo.services.CacheService;
import fr.ybo.xmltv.Tv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class GetTv {

    private final static Object jeton = new Object();

    private final static Logger logger = LoggerFactory.getLogger(GetTv.class);

    public static List<ChannelForMemCache> getCurrentChannels() throws JAXBException, IOException {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());


        List<ChannelForMemCache> channels = CacheService.getInstance().getChannels(currentDate);

        if (channels == null) {
            channels = GetTv.getTvFromNetworkAndReturnChannels(currentDate);
        }

        return channels;
    }

    public static List<ProgrammeForMemCache> getCurrentProgrammes(String channelId) throws JAXBException, IOException {
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        List<ProgrammeForMemCache> programmes = CacheService.getInstance().getProgrammes(currentDate, channelId);

        if (programmes == null) {
            programmes = GetTv.getTvFromNetworkAndReturnProgramme(currentDate, channelId);
        }
        return programmes;
    }

    private static List<ChannelForMemCache> getTvFromNetworkAndReturnChannels(String currentDate) throws JAXBException, IOException {
        TvForMemCache tv;
        synchronized (jeton) {
            JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
            Unmarshaller um = jc.createUnmarshaller();

            tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileForChannels()));

            CacheService.getInstance().addChannels(currentDate, tv.getChannel());

            for (ChannelForMemCache channel : tv.getChannel()) {
                logger.info("Récupération des programmes de la chaine " + channel.getDisplayName() + "(id=" + channel.getId() + ")");
                TvForMemCache tvWithProgrammes = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileProgrammeForOneChannel(channel.getId())));
                CacheService.getInstance().addProgrammes(currentDate, channel.getId(), tvWithProgrammes.getProgramme());
            }
        }
        return tv.getChannel();
    }

    public static void updateFromCron(String currentDate) throws JAXBException, IOException {
        synchronized (jeton) {
            long startTime = System.currentTimeMillis();

            JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
            Unmarshaller um = jc.createUnmarshaller();

            List<ChannelForMemCache> channels = CacheService.getInstance().getChannels(currentDate);
            if (channels == null) {
                TvForMemCache tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileForChannels()));
                channels = tv.getChannel();
                CacheService.getInstance().addChannels(currentDate, channels);
            }


            for (ChannelForMemCache channel : channels) {
                List<ProgrammeForMemCache> programmes = CacheService.getInstance().getProgrammes(currentDate, channel.getId());
                if (programmes == null) {
                    logger.info("Récupération des programmes de la chaine " + channel.getDisplayName());
                    TvForMemCache tvWithProgrammes = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileProgrammeForOneChannel(channel.getId())));
                    programmes = tvWithProgrammes.getProgramme();
                    CacheService.getInstance().addProgrammes(currentDate, channel.getId(), programmes);
                }
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime > TimeUnit.SECONDS.toMillis(40)) {
                    break;
                }
            }

        }
    }

    private static List<ProgrammeForMemCache> getTvFromNetworkAndReturnProgramme(String currentDate, String channelId) throws JAXBException, IOException {
        TvForMemCache returnTv = null;
        synchronized (jeton) {
            JAXBContext jc = JAXBContext.newInstance("fr.ybo.xmltv");
            Unmarshaller um = jc.createUnmarshaller();

            TvForMemCache tv = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileForChannels()));

            CacheService.getInstance().addChannels(currentDate, tv.getChannel());


            for (ChannelForMemCache channel : tv.getChannel()) {
                logger.info("Récupération des programmes de la chaine " + channel.getDisplayName());
                TvForMemCache tvWithProgrammes = TvForMemCache.fromTv((Tv) um.unmarshal(GetZip.getFileProgrammeForOneChannel(channel.getId())));
                if (channel.getId().equals(channelId)) {
                    returnTv = tvWithProgrammes;
                }
                CacheService.getInstance().addProgrammes(currentDate, channel.getId(), tvWithProgrammes.getProgramme());
            }
        }
        if (returnTv == null) {
            return null;
        }
        return returnTv.getProgramme();
    }
}
