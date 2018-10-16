package com.github.fisherhe12.apache.commons.collections;

import com.github.fisherhe12.common.domain.Player;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Apache Commons 集合工具类演示
 *
 * @author fisher
 * @date 2018-01-25
 * @see org.apache.commons.collections4.IterableUtils;
 * @see org.apache.commons.collections4.CollectionUtils
 */
public class CollectionUtilsTest {

	List<Player> playerList;

	List<Player> otherPlayerList;

	@BeforeEach
	public void init() {
		playerList = new ArrayList<>();
		playerList.add(new Player("kobe", 24));
		playerList.add(new Player("O'Neal", 34));
		playerList.add(new Player("O'Neal", 34));
		playerList.add(new Player("YaoMing", 11));
		playerList.add(new Player("Tracy", 1));
		playerList.add(new Player("Scala", 4));
		playerList.add(new Player("Jordan", 23));
		playerList.add(new Player("Fisher", 2));
		playerList.add(new Player("Jackson", 13));
		playerList.add(new Player("Antetokounmpo", 34));

		otherPlayerList = new ArrayList<>();
		otherPlayerList.add(new Player("Jackson", 13));
		otherPlayerList.add(new Player("Fisher", 2));
		otherPlayerList.add(new Player("O'Neal", 34));
		otherPlayerList.add(new Player("Paul", 3));
	}

	/**
	 * 查询单个集合元素
	 */
	@Test
	public void find() {
		Player player = IterableUtils.find(playerList, object -> object.getName().startsWith("YaoMing"));
		System.out.println(player);
	}

	/**
	 * 集合转换,可转换类型。
	 * 抽取Player中Name属性 重新组成新的List
	 */
	@Test
	public void collect() {
		Collection<String> playerNameList = CollectionUtils.collect(playerList, p -> p.getName());
		System.out.println(playerNameList);
	}

	/**
	 * 对原有集合进行过滤,不会拷贝副本
	 * 与guava filter用法相似
	 */
	@Test
	public void filter() {
		CollectionUtils.filter(playerList, player -> player.getNumber() > 10);
		System.out.println(playerList);
	}

	/**
	 * 查询集合元素,返回列表
	 */
	@Test
	public void select() {
		Collection<Player> players = CollectionUtils.select(playerList, player -> player.getName().startsWith("J"));
		System.out.println(players);
	}

	/**
	 * 判断集合元素是否匹配
	 */
	@Test
	public void matches() {
		System.out.println(IterableUtils
				.matchesAny(playerList, player -> player.getName().length() > 7 && player.getNumber() > 30));
		System.out.println(IterableUtils
				.matchesAll(playerList, player -> player.getName().length() > 7 && player.getNumber() > 30));
		System.out.println(IterableUtils.countMatches(playerList, player -> player.getNumber() > 30));
	}

	/**
	 * 元素在集合出现的次数,可筛选重复项
	 */
	@Test
	public void frequency() {
		System.out.println(IterableUtils.frequency(playerList, new Player("O'Neal", 34)));
	}

	/**
	 * 交集,允许重复的元素
	 */
	@Test
	public void retailAll() {
		Collection<Player> currentCollection = CollectionUtils.retainAll(playerList, otherPlayerList);
		System.out.println(playerList);
		System.out.println(currentCollection);
	}

	/**
	 * 差集,会移除所有重复的数据
	 * 从本集合中移除另外一个集合中相同的元素
	 */
	@Test
	public void removeAll() {
		Collection<Player> currentCollection = CollectionUtils.removeAll(playerList, otherPlayerList);
		System.out.println(playerList);
		System.out.println(currentCollection);
	}

	/**
	 * 差集,不会删除相同的元素
	 */
	@Test
	public void subtract() {
		Collection<Player> currentCollection = CollectionUtils.subtract(playerList, otherPlayerList);
		System.out.println(playerList);
		System.out.println(currentCollection);
	}

	/**
	 * 并集,不会引入相同的元素
	 */
	@Test
	public void union() {
		Collection<Player> currentCollection = CollectionUtils.union(playerList, otherPlayerList);
		System.out.println(playerList);
		System.out.println(currentCollection);
	}

	/**
	 * 不可变的集合
	 * 类似guava的immutable List
	 */
	@Test
	public void unmodifiableCollection() {
		Iterable<Player> unmodifiableList = IterableUtils.unmodifiableIterable(playerList);
		System.out.println(unmodifiableList);
	}


}
