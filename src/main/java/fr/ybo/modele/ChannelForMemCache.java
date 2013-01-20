package fr.ybo.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.primitives.Ints;
import fr.ybo.xmltv.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class ChannelForMemCache implements Serializable, Comparable<ChannelForMemCache> {

    private String id;
    private String displayName;


    private static final Logger logger = LoggerFactory.getLogger(ChannelForMemCache.class);

    @JsonProperty("icon")
    public String getOneIcon() {
        if (!mapChaineLogo.containsKey(id)) {
            logger.warn("La chaine {} n'a pas de logo", id);
        }
        return mapChaineLogo.get(id) + ".png";
    }

    @JsonProperty("numero")
    public int getNumero() {
        return getNumero(id);
    }

    private final static Map<String, String> mapChaineLogo = new HashMap<String, String>() {{
        put("13R1", "13erue");
        put("6TER", "6ter");
        put("MOT1", "abmoteurs");
        put("AB11", "ab1");
        put("AB31", "ab3");
        put("AB41", "ab4");
        put("ACT1", "action");
        put("ANI1", "animaux");
        put("ART1", "arte");
        put("BEI1", "beinsport1");
        put("BEI2", "beinsport2");
        put("BFM1", "bfmtv");
        put("BOO1", "boomerang");
        put("CIN20", "cinecinemaclassic");
        put("CIN21", "cinecinemaclub");
        put("CIN18", "cinecinemafamiz");
        put("CIN22", "cinecinemafrisson");
        put("CIN23", "cinecinemapremier");
        put("CIN28", "cinecinemastar");
        put("CAN1", "canalj");
        put("CAN2", "canal");
        put("CAN3", "canalcinema");
        put("CAN4", "canaldecale");
        put("CAN10", "canalfamily");
        put("CAN5", "canalsport");
        put("CAR1", "cartoonnetwork");
        put("CHE1", "cherie25");
        put("CIN1", "cinefx");
        put("CIN2", "cinepolar");
        put("CIN19", "cinefirst");
        put("CO11", "comedie");
        put("DIR1", "d8");
        put("EUR2", "d17");
        put("DIS1", "discoverychannel");
        put("DIS3", "disneychannel");
        put("DIS5", "disneycinemagic");
        put("EEN1", "eentertainment");
        put("EQU1", "equidia");
        put("ESC1", "escales");
        put("ESP1", "espnclassic");
        put("FRA2", "france2");
        put("FRA3", "france3");
        put("FRA4", "france4");
        put("FRA5", "france5");
        put("FRA1", "franceo");
        put("GAM1", "gameone");
        put("GUL1", "gulli");
        put("HD1", "hd1");
        put("ITL1", "itele");
        put("JIM1", "jimmy");
        put("LEQ1", "lequipe21");
        put("LAD1", "ladeux");
        put("LAU1", "laune");
        put("LAC1", "lachaineparlementaire");
        put("M61", "m6");
        put("M6M2", "m6musichits");
        put("MAC1", "machainesport");
        put("MAN1", "mangas");
        put("MCM1", "mcm");
        put("MEZ1", "mezzo");
        put("MOT2", "motorstv");
        put("MTV1", "mtv");
        put("NAT1", "nationalgeographic");
        put("NIC1", "nickelodeon");
        put("NOL1", "nolife");
        put("NRJ1", "nrj12");
        put("NT11", "nt1");
        put("NU23", "numero23");
        put("OMT1", "omtv");
        put("PAR1", "parispremiere");
        put("PIN1", "pinktv");
        put("PLA1", "planete");
        put("PLA5", "planetejustice");
        put("PLA2", "planetenolimit");
        put("PLA3", "planetethalassa");
        put("DIS15", "playhousedisney");
        put("RMC2", "rmcdecouverte");
        put("RTL2", "rtl9");
        put("SCI1", "syfy");
        put("SEA1", "seasons");
        put("SPO1", "sportplus");
        put("TCM1", "tcm");
        put("TLT1", "teletoon");
        put("TVA1", "teva");
        put("TF11", "tf1");
        put("TF61", "tf6");
        put("TIJ1", "tiji");
        put("TMC1", "tmc");
        put("TOU1", "toutelhistoire");
        put("TSR1", "tsr1");
        put("TSR2", "tsr2");
        put("TVB1", "tvbreizh");
        put("TV51", "tv5monde");
        put("USH1", "ushuaiatv");
        put("VIV1", "vivolta");
        put("W91", "w9");
    }};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public static ChannelForMemCache fromChannel(Channel channel) {
        ChannelForMemCache channelForMemCache = new ChannelForMemCache();
        channelForMemCache.setDisplayName(channel.getOneDisplayName());
        channelForMemCache.setId(channel.getId());
        return channelForMemCache;
    }

    private transient ProgrammeForMemCache currentProgramme;

    public ProgrammeForMemCache getCurrentProgramme() {
        return currentProgramme;
    }

    public void setCurrentProgramme(ProgrammeForMemCache currentProgramme) {
        this.currentProgramme = currentProgramme;
    }


    private final static Map<String, Integer> mapChaineNumero = new HashMap<String, Integer>() {{
        put("TF11", 1);
        put("FRA2", 2);
        put("FRA3", 3);
        put("CAN2", 4);
        put("FRA5", 5);
        put("M61", 6);
        put("ART1", 7);
        put("DIR1", 8);
        put("W91", 9);
        put("TMC1", 10);
        put("NT11", 11);
        put("NRJ1", 12);
        put("LAC1", 13);
        put("FRA4", 14);
        put("BFM1", 15);
        put("ITL1", 16);
        put("EUR2", 17);
        put("GUL1", 18);
        put("FRA1", 19);
        put("HD1", 20);
        put("LEQ1", 21);
        put("6TER", 22);
        put("NU23", 23);
        put("RMC2", 24);
        put("CHE1", 25);
        put("PAR1", 50);
        put("TVA1", 51);
        put("RTL2", 52);
    }};

    private static int getNumero(String idChaine) {
        if (mapChaineNumero.containsKey(idChaine)) {
            return mapChaineNumero.get(idChaine);
        } else {
            return 999;
        }
    }

    @Override
    public int compareTo(ChannelForMemCache o) {
        int result = Ints.compare(getNumero(id), getNumero(o.id));
        if (result == 0) {
            result = id.compareTo(o.id);
        }
        return result;
    }
}
