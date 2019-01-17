package com.blogging.aps.support.loadBalance;

import java.util.*;

/**
 * @author techoneduan
 * @date 2018/12/28
 * <p>
 * 带虚拟节点的一致性hash
 */
public class ConsistentHashingVN {

    /**
     * 待添加入Hash环的服务器列表
     */
    private static List<String> servers;

    /**
     * 真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
     */
    private static List<String> realNodes;

    /**
     * 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
     */
    private static SortedMap<Integer, String> virtualNodes;

    /**
     * 虚拟节点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟节点
     */
    private static final int VIRTUAL_NODES = 5;


    private static void initLoopWithVN (List<String> servers) {
        /**
         * 保存真实节点
         */
        realNodes = new ArrayList<>();
        virtualNodes = new TreeMap<>();
        servers.stream().forEach(item -> {
            realNodes.add(item);
        });
        // 再添加虚拟节点，遍历LinkedList使用foreach循环效率会比较高
        for (String str : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = str + "&&VN" + String.valueOf(i);
                int hash = ConsistentHashing.FNV_32_HASH(virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    public static String loadBalance (List<String> ipList, String ip, String params) {
        initLoopWithVN(ipList);
        return getServer(ip + params);
    }

    /**
     * 得到应当路由到的结点
     */
    private static String getServer (String node) {
        // 得到带路由的结点的Hash值
        int hash = ConsistentHashing.FNV_32_HASH(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap =
                virtualNodes.tailMap(hash);
        // 第一个Key就是顺时针过去离node最近的那个结点
        Integer i = subMap.firstKey();
        // 返回对应的虚拟节点名称，这里字符串稍微截取一下
        String virtualNode = subMap.get(i);
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }

    public static void main (String[] args) {
        String[] nodes = {"10.32.16.111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    ConsistentHashing.FNV_32_HASH(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");
    }
}
