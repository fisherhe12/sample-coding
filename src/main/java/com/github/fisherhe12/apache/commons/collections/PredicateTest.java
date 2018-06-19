package com.github.fisherhe12.apache.commons.collections;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.functors.NotNullPredicate;
import org.apache.commons.collections4.functors.NotPredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Commons collection predicate test case
 *
 * @author fisher
 * @date 2018-01-25
 */
public class PredicateTest {

    /**
     * 唯一性过滤
     */
    @Test
    public void uniquePredicate() {
        ArrayList<String> elements = new ArrayList<>();
        elements.add("fisher");
        elements.add("jack");
        elements.add("jordan");
        elements.add("jack");
        CollectionUtils.filter(elements, UniquePredicate.uniquePredicate());
        System.out.println(elements);
    }

    /**
     * 排空过滤
     */
    @Test
    public void notNullPredicate() {
        ArrayList<String> elements = new ArrayList<>();
        elements.add("fisher");
        elements.add("");
        elements.add("jordan");
        elements.add(null);

        CollectionUtils.filter(elements, NotNullPredicate.notNullPredicate());
        System.out.println(elements);
    }

    /**
     * 不包含过滤
     */
    @Test
    public void notPredicate() {

        ArrayList<String> elements = new ArrayList<>();
        elements.add("fisher");
        elements.add("");
        elements.add("jordan");
        elements.add(null);

        CollectionUtils.filter(elements, NotPredicate.notPredicate(n -> n == null || n.startsWith("f")));
        System.out.println(elements);
    }
}
