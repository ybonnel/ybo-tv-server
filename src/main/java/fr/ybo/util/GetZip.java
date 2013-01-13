package fr.ybo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipInputStream;

public class GetZip {

    private static Logger logger = LoggerFactory.getLogger(GetZip.class);

    //public final static String BASE_URL = "http://127.0.0.1:9080/local-xml-v2/";
    public final static String BASE_URL = "http://serveur.ybonnel.fr/local-xml-v2/";

    public static Reader getFileForChannels() throws IOException {

        logger.info("getFile");
        URL url = new URL(BASE_URL + "channels.xml.zip");
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(0);
        connection.setReadTimeout(0);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
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
        ZipInputStream stream = new ZipInputStream(inputStream);
        stream.getNextEntry();
        return new InputStreamReader(stream, "utf-8");
    }
}
