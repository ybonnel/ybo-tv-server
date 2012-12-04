package fr.ybo.modele;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ybo.xmltv.Channel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChannelForMemCache implements Serializable, Comparable<ChannelForMemCache> {

    private String id;
    private String displayName;


    @JsonProperty("icon")
    public String getOneIcon() {
        return mapChaineLogo.get(id);
    }

    private final static Map<String, String> mapChaineLogo = new HashMap<String, String>(){{
        put("1", "tf1.png");
        put("2", "france2.png");
        put("3", "france3.png");
        put("4", "canal.png");
        put("5", "france5.png");
        put("6", "m6.png");
        put("7", "arte.png");
        put("8", "d8.png");
        put("9", "w9.png");
        put("10", "tmc.png");
        put("11", "nt1.png");
        put("12", "nrj12.png");
        put("13", "lachaineparlementaire.png");
        put("14", "france4.png");
        put("15", "bfmtv.png");
        put("16", "itele.png");
        put("17", "d17.png");
        put("18", "gulli.png");
        put("20", "13erue.png");
        put("21", "2be.png");
        put("23", "ab1.png");
        put("24", "ab3.png");
        put("25", "ab4.png");
        put("26", "action.png");

        put("27", "abmoteurs.png");
        put("28", "alsatictv.png");
        put("29", "animaux.png");
        put("31", "betv.png");
        put("32", "becine.png");
        put("33", "beseries.png");
        put("34", "besport1.png");
        put("35", "besport2.png");
        put("38", "boomerang.png");
        put("42", "canalj.png");
        put("43", "canalcinema.png");
        put("44", "canaldecale.png");
        put("45", "canalfamily.png");
        put("46", "canalhightech.png");
        put("47", "canalsport.png");
        put("48", "canalhorizon.png");
        put("50", "cartoonnetwork.png");
        put("51", "chasseetpeche.png");
        put("52", "cinefirst.png");
        put("53", "cinefx.png");
        put("54", "cinepolar.png");
        put("56", "cinecinemaclassic.png");
        put("58", "cinecinemaclub.png");
        put("59", "cinecinemaemotion.png");
        put("60", "cinecinemafamiz.png");
        put("61", "cinecinemafrisson.png");
        put("62", "cinecinemapremier.png");
        put("65", "cinecinemastar.png");
        put("67", "clubrtl.png");
        put("68", "comedie.png");
        put("69", "cuisinetv.png");
        put("70", "demaintv.png");
        put("71", "discoverychannel.png");
        put("73", "disneychannel.png");
        put("75", "disneycinemagic.png");
        put("76", "disneycinemagichd.png");
        put("78", "dorceltv.png");
        put("79", "ducotedechezvoustv.png");
        put("80", "eentertainment.png");
        put("82", "encyclopedia.png");
        put("83", "equidia.png");
        put("84", "escales.png");
        put("85", "espn.png");
        put("86", "espnclassic.png");
        put("87", "euronews.png");
        put("89", "eurosport.png");
        put("92", "june.png");
        put("119", "franceo.png");
        put("121", "gameone.png");
        put("122", "histoire.png");
        put("125", "jetix.png");
        put("126", "jimmy.png");
        put("130", "ladeux.png");
        put("131", "laune.png");
        put("133", "lci.png");
        put("135", "libertytv.png");
        put("137", "m6boutique.png");
        put("138", "m6musicblack.png");
        put("139", "m6musichits.png");
        put("141", "machainesport.png");
        put("142", "mangas.png");
        put("144", "mcm.png");
        put("146", "mcmpop.png");
        put("147", "mcmtop.png");
        put("148", "mezzo.png");
        put("149", "motorstv.png");
        put("150", "mtv.png");
        put("162", "mtvbase.png");
        put("164", "mtvidol.png");
        put("165", "mtvpulse.png");
        put("168", "nationalgeographic.png");
        put("169", "nationalgeographichd.png");
        put("171", "nickelodeon.png");
        put("173", "nrjhits.png");
        put("174", "nrjparis.png");
        put("175", "odyssee.png");
        put("176", "oltv.png");
        put("177", "omtv.png");
        put("180", "orangecinegeants.png");
        put("181", "orangecinechoc.png");
        put("182", "orangecinehappy.png");
        put("183", "orangecinemax.png");
        put("186", "parispremiere.png");
        put("187", "pinktv.png");
        put("188", "planete.png");
        put("190", "planetejustice.png");
        put("191", "planetenolimit.png");
        put("192", "planetethalassa.png");
        put("194", "playhousedisney.png");
        put("195", "plugrtl.png");
        put("199", "rtl9.png");
        put("201", "syfy.png");
        put("202", "seasons.png");
        put("203", "serieclub.png");
        put("204", "sportplus.png");
        put("206", "tcm.png");
        put("209", "teleguadeloupe.png");
        put("210", "teleguyane.png");
        put("213", "telemartinique.png");
        put("214", "telenantes.png");
        put("216", "telenouvellecaledonie.png");
        put("217", "telepolynesie.png");
        put("218", "telereunion.png");
        put("220", "teletoon.png");
        put("222", "telif.png");
        put("227", "teva.png");
        put("228", "tf6.png");
        put("229", "tiji.png");
        put("233", "tpsstar.png");
        put("235", "tsr1.png");
        put("236", "tsr2.png");
        put("237", "tv5monde.png");
        put("243", "tvbreizh.png");
        put("246", "ushuaiatv.png");
        put("247", "vivolta.png");
        put("248", "voyage.png");
        put("253", "cnn.png");
        put("275", "2mmonde.png");
        put("276", "piwi.png");
        put("278", "3sat.png");
        put("279", "bloombergtv.png");
        put("281", "yachtsail.png");
        put("288", "france24.png");
        put("290", "babyfirst.png");
        put("293", "cap24.png");
        put("294", "idf1.png");
        put("295", "etb1.png");
        put("296", "etb2.png");
        put("298", "m6musicclub.png");
        put("299", "toutelhistoire.png");
        put("301", "ard.png");
        put("303", "zdf.png");
        put("304", "dsf.png");
        put("401", "bbcone.png");
        put("402", "bbctwo.png");
        put("403", "bbcprime.png");
        put("511", "rtbfsat.png");
        put("514", "rtltvi.png");
        put("601", "tve1.png");
        put("602", "tve2.png");
        put("603", "tveinternational.png");
        put("701", "raiuno.png");
        put("702", "raidue.png");
        put("703", "raitre.png");
        put("811", "latrois.png");
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
        channelForMemCache.setId(transformId(channel.getId()));
        return channelForMemCache;
    }

    public static String transformId(String oldId) {
        return oldId.split("\\.")[0].substring(1);
    }

    private transient ProgrammeForMemCache currentProgramme;

    public ProgrammeForMemCache getCurrentProgramme() {
        return currentProgramme;
    }

    public void setCurrentProgramme(ProgrammeForMemCache currentProgramme) {
        this.currentProgramme = currentProgramme;
    }


    @Override
    public int compareTo(ChannelForMemCache o) {
        int id1 = Integer.parseInt(id);
        int id2 = Integer.parseInt(o.id);
        return (id1 == id2) ? 0 : (id1 < id2) ? -1 : 1;
    }
}
