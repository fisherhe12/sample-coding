package com.github.fisherhe12.jdk.util.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * stream 中flatMap示例
 *
 * @author fisher
 */
class FlatMapTest {

    /**
     * stream<String[]>->Stream<String>
     */
    @Test
    void arrayToFlatMap() {
        String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};

        //Stream<String[]>
        Stream<String[]> temp = Arrays.stream(data);

        Stream<String> stringStream = temp.flatMap(Arrays::stream);

        List<String> resultList = stringStream.filter(s -> !StringUtils.equals("a", s))
            .collect(Collectors.toList());
        resultList.forEach(System.out::println);

        int[] intArray = {1, 2, 3, 4, 5, 6};
        //1. Stream<int[]>
        Stream<int[]> streamArray = Stream.of(intArray);

        //2. Stream<int[]> -> flatMap -> IntStream
        IntStream intStream = streamArray.flatMapToInt(Arrays::stream);

        intStream.forEach(System.out::println);
    }
}
