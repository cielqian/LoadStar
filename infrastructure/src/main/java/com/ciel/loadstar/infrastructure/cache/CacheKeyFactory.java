package com.ciel.loadstar.infrastructure.cache;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/7/9 17:16
 */

public class CacheKeyFactory {
    public static final String LINK_VIEW_COUNT = "LINK_VIEW_COUNT";

    static Map<String, String> CACHE_KEY_MAP;

    static{
        CACHE_KEY_MAP = new HashMap<>();
        CACHE_KEY_MAP.put(LINK_VIEW_COUNT, "service:linkview:userid:date");
    }

    public static String build(String cacheType, Map<String, String> values){
        String cacheKeyTemplate = CACHE_KEY_MAP.get(cacheType);
        String cacheKeyFormat = new String(cacheKeyTemplate);

        String[] keys = cacheKeyFormat.split(":");

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (values.containsKey(key)){
                String valueForReplace = values.get(key);
                cacheKeyFormat = StringUtils.replace(cacheKeyFormat, key, valueForReplace);
            }
        }
        return cacheKeyFormat;
    }
}
