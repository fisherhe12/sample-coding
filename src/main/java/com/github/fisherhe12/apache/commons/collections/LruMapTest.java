package com.github.fisherhe12.apache.commons.collections;

import org.apache.commons.collections4.map.LRUMap;
import org.junit.jupiter.api.Test;

/**
 * LruMap test case
 *
 * @author fisher
 * @date 2018-01-25
 * @see org.apache.commons.collections4.map.LRUMap
 */
class LruMapTest {

    /**
     * 基于链表实现的LRUMAP
     * 默认最大容量为100,可自定最大长度
     * 超出容量外的元素,根据LRU原则进行移除
     */
    @Test
    void lruMap() {
        LRUMap<String, Integer> lruMap = new LRUMap<>(4);
        lruMap.putIfAbsent("fisher", 12);
        lruMap.putIfAbsent("jackson", 13);
        lruMap.put("jordan", 23);
        lruMap.put("kobe", 24);
        System.out.println(lruMap.isFull());
        lruMap.put("harden", 13);
        System.out.println(lruMap);
        lruMap.get("jackson");
        lruMap.put("O'neal", 34);
        System.out.println(lruMap);

    }
}
