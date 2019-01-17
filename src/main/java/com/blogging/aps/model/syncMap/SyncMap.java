package com.blogging.aps.model.syncMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author techoneduan
 * @date 2018/12/18
 *
 * 全局Map 满足netty异步转为同步操作
 */
public class SyncMap {

    private static Map<String, String> syncMap = new ConcurrentHashMap<>();

    public static boolean hasKey (String requestId) {
        return syncMap.containsKey(requestId);
    }

    public static void put (String requestId, String resp) {
        if (!syncMap.containsKey(requestId))
            syncMap.put(requestId, resp);
    }

    public static void delete (String requestId) {
        if (syncMap.containsKey(requestId))
            syncMap.remove(requestId);
    }

    public static String get (String requestId) {
        String value = null;
        if (syncMap.containsKey(requestId)) {
             value = syncMap.get(requestId);
             delete(requestId);
        }
        return value;
    }
}
