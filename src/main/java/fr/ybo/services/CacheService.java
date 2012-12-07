package fr.ybo.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Weigher;
import fr.ybo.modele.ChannelForMemCache;
import fr.ybo.modele.ProgrammeForMemCache;

import java.util.List;
import java.util.concurrent.TimeUnit;

public enum CacheService {

    INSTANCE;

    private boolean isCacheActive = true;

    private CacheService() {
        String propertyCacheActive = System.getProperty("fr.ybo.ybotv.cache");
        if (propertyCacheActive != null) {
            isCacheActive = Boolean.parseBoolean(propertyCacheActive);
        }
    }

    public static CacheService getInstance() {
        return INSTANCE;
    }

    private Cache<String, List<ChannelForMemCache>> cacheForChannels = null;

    private Cache<String, List<ChannelForMemCache>> getCacheForChannels() {
        if (cacheForChannels == null) {
            synchronized (INSTANCE) {
                if (cacheForChannels == null) {
                    cacheForChannels = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build();
                }
            }
        }
        return cacheForChannels;
    }

    public List<ChannelForMemCache> getChannels(String currentDate) {
        if (!isCacheActive) {
            return null;
        }
        String cacheId = currentDate + "_channels";
        return getCacheForChannels().getIfPresent(cacheId);
    }

    public void addChannels(String currentDate, List<ChannelForMemCache> channels) {
        if (isCacheActive) {
            String cacheId = currentDate + "_channels";
            getCacheForChannels().put(cacheId, channels);
        }
    }


    private Cache<String, List<ProgrammeForMemCache>> cacheForProgrammes = null;

    private Cache<String, List<ProgrammeForMemCache>> getCacheForProgrammes() {
        if (cacheForProgrammes == null) {
            synchronized (INSTANCE) {
                if (cacheForProgrammes == null) {
                    cacheForProgrammes = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build();
                }
            }
        }
        return cacheForProgrammes;
    }

    public List<ProgrammeForMemCache> getProgrammes(String currentDate, String channelId) {
        if (!isCacheActive) {
            return null;
        }
        String cacheId = currentDate + "_programmes_" + channelId;
        return getCacheForProgrammes().getIfPresent(cacheId);
    }

    public void addProgrammes(String currentDate, String channelId, List<ProgrammeForMemCache> programmes) {
        if (isCacheActive) {
            String cacheId = currentDate + "_programmes_" + channelId;
            getCacheForProgrammes().put(cacheId, programmes);
        }
    }


    private Cache<String, String> cacheForJsonResponses = null;

    private Cache<String, String> getCacheForJsonResponses() {
        if (cacheForJsonResponses == null) {
            synchronized (INSTANCE) {
                if (cacheForJsonResponses == null) {
                    cacheForJsonResponses = CacheBuilder.newBuilder().maximumWeight(10000000).weigher(new Weigher<String, String>() {
                        @Override
                        public int weigh(String key, String value) {
                            return value.length();
                        }
                    }).build();
                }
            }
        }
        return cacheForJsonResponses;
    }

    public String getJsonResponse(String currentDate, String path) {
        if (!isCacheActive) {
            return null;
        }
        String cacheId = "jsonResponse/" + currentDate + path;
        return getCacheForJsonResponses().getIfPresent(cacheId);
    }

    public void addJsonResponse(String currentDate, String path, String jsonResponse) {
        if (isCacheActive) {
            String cacheId = "jsonResponse/" + currentDate + path;
            getCacheForJsonResponses().put(cacheId, jsonResponse);
        }
    }
}
