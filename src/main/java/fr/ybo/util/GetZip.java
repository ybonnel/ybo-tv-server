package fr.ybo.util;


import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import fr.ybo.xmltv.Channel;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.zip.ZipInputStream;

public class GetZip {

    private static Logger logger = Logger.getLogger(GetZip.class);

    public final static String BASE_URL = "http://transports-rennes.ic-s.org/version/ybo-tv/xml/";

    public static Reader getFileForChannels() throws IOException {

        logger.info("getFile");
        URL url = new URL(BASE_URL + "channels.xml.zip");
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(0);
        connection.setReadTimeout(0);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        //InputStream inputStream = GetZip.class.getResourceAsStream("/complet.zip");
        ZipInputStream stream = new ZipInputStream(inputStream);
        stream.getNextEntry();
        return new InputStreamReader(stream, "utf-8");
    }

    public static Reader getFileProgrammeForOneChannel(String channelId) throws IOException {
        URL url = new URL(BASE_URL + "programmes_" + channelId + ".xml.zip");
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(0);
        connection.setReadTimeout(0);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        //InputStream inputStream = GetZip.class.getResourceAsStream("/complet.zip");
        ZipInputStream stream = new ZipInputStream(inputStream);
        stream.getNextEntry();
        return new InputStreamReader(stream, "utf-8");
    }
}
