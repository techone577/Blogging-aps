package com.blogging.aps.support.loadBalance;

import java.util.*;

/**
 * @author techoneduan
 * @date 2018/12/27
 * 一致性hash
 */
public class ConsistentHashing {

    /**
     * FNV_prime = 2^24 + 2^8 + 0x93 = 16777619
     * 用于散列的质数
     */
    private static final int FNV_prime= 16777619;

    public static SortedMap<Integer, String> LOOP = new TreeMap<>();

    private static void initLoop (List<String> ipList) {
        for (String ip : ipList) {
            LOOP.put(FNV_32_HASH(ip), ip);
        }
    }

    public static String loadBalance (List<String> ipList, String localIp, String param) {
        initLoop(ipList);
        return getServer(localIp + param);
    }

    /**
     * FNV_hash_1a_32bit
     * @param str
     * @return
     */
    public static Integer FNV_32_HASH (String str) {
        /**
         * 初始hash值
         */
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * FNV_prime;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    private static String getServer (String node) {
        int hash = FNV_32_HASH(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap =
                LOOP.tailMap(hash);
        /**
         * 第一个Key就是顺时针最近的那个结点
         */
        Integer i = subMap.firstKey();
        return subMap.get(i);
    }

    public static void main (String[] args) {
        List<String> ipList = new ArrayList<String>() {
            {
                add("10.32.16.111");
                add("10.32.16.110");
                add("10.32.16.112");
                add("10.32.16.100");
            }
        };
        ConsistentHashing.loadBalance(ipList, "168.126.0.1", "test");
    }
}
