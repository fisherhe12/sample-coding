package com.github.fisherhe12.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Curator Watcher 监听事件
 *
 * 1.pathChildrenCache 监听一个路径下的子节点的创建、删除、节点更新
 *
 * 2.NodeCache 监听一个节点的创建、更新、删除
 *
 * 3.TreeCache 前两个事件的合集 监听路径下的创建、更新、删除
 *
 * @author fisher
 */
public class CuratorEventTest {

	private static final String IP_ADDRESS = "172.18.8.20:2181";
	private CuratorFramework curatorFramework;

	@Before
	public void init() {
		curatorFramework = CuratorFrameworkFactory.builder().connectString(IP_ADDRESS).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("root").build();
		curatorFramework.start();
	}

	@Test
	public void nodeCacheOps() throws Exception {
		NodeCache nodeCache = new NodeCache(curatorFramework, "/book/", false);
		nodeCache.start();
		nodeCache.getListenable().addListener(() -> System.out
				.println("Node has been changed,result is :" + new String(nodeCache.getCurrentData().getData())));

		curatorFramework.create().creatingParentsIfNeeded().forPath("/book", "book".getBytes());
		curatorFramework.setData().forPath("/book", "python".getBytes());
		curatorFramework.delete().forPath("/book");

		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	public void pathCacheOps() throws Exception {
		PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/book", false);
		//TODO 验证一下StartMode类型
		pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
		pathChildrenCache.getListenable().addListener((client, event) -> {
			switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("Node added->" + event.getData().getPath());
					break;
				case CHILD_REMOVED:
					System.out.println("Node removed->" + event.getData().getPath());
					break;
				case CHILD_UPDATED:
					System.out.println("Node changed->" + event.getData().getPath());
					break;
				default:
					break;
			}
		});
		curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
				.forPath("/book/java", "zookeeper".getBytes());

		curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
				.forPath("/book/javascript", "react".getBytes());
		TimeUnit.SECONDS.sleep(1);

		curatorFramework.setData().forPath("/book/java", "spring".getBytes());

		curatorFramework.setData().forPath("/book/javascript", "jquery".getBytes());
		TimeUnit.SECONDS.sleep(1);

		curatorFramework.delete().deletingChildrenIfNeeded().forPath("/book");

		CloseableUtils.closeQuietly(pathChildrenCache);

	}

	@Test
	public void treeCache() throws Exception {
		TreeCache cache = TreeCache.newBuilder(curatorFramework, "/").setCacheData(false).build();
		cache.getListenable().addListener((client, event) -> {
			if ( event.getData() != null )
			{
				System.out.println("type=" + event.getType() + " path=" + event.getData().getPath());
			}
			else
			{
				System.out.println("type=" + event.getType());
			}
		});
		cache.start();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		in.readLine();

	}


	@After
	public void close() {
		CloseableUtils.closeQuietly(curatorFramework);
	}

}
