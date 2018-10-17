package com.github.fisherhe12.apache.commons.collections;

import org.apache.commons.collections4.list.TreeList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Commons Collection TreeList TestCase
 *
 * @author fisher
 * @date 2018-01-25
 */
public class TreeListTest {

    /**
     * 基于AVL树实现的List,将各种操作的时间复杂度降低到O（logN)
     * <p>
     * *              get  add  insert  iterate  remove
     * TreeList       3    5       1       2       1
     * ArrayList      1    1      40       1      40
     * LinkedList  5800    1     350       2     325
     */
    @Test
    void treeList() {
        ArrayList<String> arrayList = new ArrayList<>();
        TreeList<String> treeList = new TreeList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            treeList.add("fisher");
            treeList.add("kpi");
            treeList.add("jack");
            treeList.add("bob");
            treeList.add("tom");
            treeList.add("alan");
            treeList.remove("kpi");
        }
        long end = System.currentTimeMillis();
        long elapsed = (end - start) / 1000;
        System.out.println(" tree stream elapsed time:" + elapsed + "s");

        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            arrayList.add("fisher");
            arrayList.add("kpi");
            arrayList.add("jack");
            arrayList.add("bob");
            arrayList.add("tom");
            arrayList.add("akka");
            arrayList.remove("akka");
        }
        end = System.currentTimeMillis();
        elapsed = (end - start) / 1000;
        System.out.println(" array stream elapsed time:" + elapsed + "s");
    }
}
