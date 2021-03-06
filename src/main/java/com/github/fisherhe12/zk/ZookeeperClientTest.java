package com.github.fisherhe12.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Zookeeper 客户端一些常用操作
 *
 * @author fisher
 */
class ZookeeperClientTest {

	private static final String IP_ADDRESS = "172.18.8.20:2183";
	private static ZooKeeper zooKeeper;
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static Stat stat = new Stat();

	@BeforeEach
	void init() {
		try {
			zooKeeper = new ZooKeeper(IP_ADDRESS, 50000, new InitWatcher());
			System.out.println(zooKeeper.getState());
			try {
				connectedSemaphore.await();
			} catch (InterruptedException e) {
				System.out.println("Zookeeper session established...");
			}
		} catch (IOException e) {
			System.out.println("Connect to zk error...");
			e.printStackTrace();
		}
	}

	@Test
	void createSync() throws KeeperException, InterruptedException {
		// 创建瞬时的节点
		String path = zooKeeper.create("/user", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Success create ephemeral zNode:" + path);

		// 创建瞬时有序的节点
		String path1 = zooKeeper
				.create("/user-", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("Success create ephemeralSequential zNode:" + path1);
	}

	@Test
	void createAsync() throws InterruptedException {
		zooKeeper.create("/user-", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
				(rc, path, ctx, name) -> System.out
						.println("Create path result,rc:" + rc + ",path:" + path + ",ctx:" + ctx + ",name:" + name),
				"This is context.");
		zooKeeper.create("/user-", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
				(rc, path, ctx, name) -> System.out
						.println("Create path result,rc:" + rc + ",path:" + path + ",ctx:" + ctx + ",name:" + name),
				"This is context.");
		zooKeeper.create("/user-", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
				(rc, path, ctx, name) -> System.out
						.println("Create path result,rc:" + rc + ",path:" + path + ",ctx:" + ctx + ",name:" + name),
				"This is context.");
		TimeUnit.SECONDS.sleep(3);
	}

	@Test
	void crud() throws KeeperException, InterruptedException {
		zooKeeper.exists("/user", true);
		zooKeeper.create("/user", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zooKeeper.setData("/user", "fisherhe".getBytes(), -1);
		zooKeeper.create("/user/age", "18".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zooKeeper.delete("/user/age", -1);
		zooKeeper.delete("/user", -1);
		TimeUnit.SECONDS.sleep(5);
	}

	@Test
	void get() throws KeeperException, InterruptedException {
		zooKeeper.getChildren("/book", true, (rc, path, ctx, children, stat) -> {
			System.out.println("Get children zNode result");
			System.out.println("rc:" + rc);
			System.out.println("path:" + path);
			System.out.println("children:" + children);
			System.out.println("stat:" + stat);
		}, null);
		byte[] data = zooKeeper.getData("/book", true, stat);
		System.out.println(new String(data));
		zooKeeper.setData("/book", "many many books".getBytes(), -1);

		TimeUnit.SECONDS.sleep(5);
	}

	@Test
	void auth() throws IOException, KeeperException, InterruptedException {
		ZooKeeper zooKeeper1 = new ZooKeeper(IP_ADDRESS, 5000, null);

		zooKeeper1.addAuthInfo("digest", "root:root".getBytes());
		zooKeeper1.create("/root", "root".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		zooKeeper1.create("/root/leaf", "leaf".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

		try {
			ZooKeeper zooKeeper2 = new ZooKeeper(IP_ADDRESS, 50000, null);
			zooKeeper2.delete("/root/leaf", -1);
		} catch (Exception e) {
			System.out.println("删除节点失败:" + e.getMessage());
		}

		ZooKeeper zooKeeper3 = new ZooKeeper(IP_ADDRESS, 50000, null);
		zooKeeper3.addAuthInfo("digest", "root:root".getBytes());
		zooKeeper3.delete("/root/leaf", -1);
		System.out.println("成功删除节点:/root/leaf");

		ZooKeeper zooKeeper4 = new ZooKeeper(IP_ADDRESS, 50000, null);
		zooKeeper4.delete("/root", -1);
		System.out.println("成功删除节点:/root");
	}

	@AfterEach
	void close() {
		try {
			zooKeeper.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class InitWatcher implements Watcher {
		@Override
		public void process(WatchedEvent watchedEvent) {
			try {

				if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {

					if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
						connectedSemaphore.countDown();
					} else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
						System.out.println("Node(" + watchedEvent.getPath() + ")Created...");
						zooKeeper.exists(watchedEvent.getPath(), true);
					} else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
						System.out.println("Node(" + watchedEvent.getPath() + ")Deleted...");
						zooKeeper.exists(watchedEvent.getPath(), true);
					} else if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
						byte[] data = zooKeeper.getData(watchedEvent.getPath(), true, stat);
						System.out.println("Node data changed ....:" + new String(data));
						System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
					} else if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
						List<String> children = zooKeeper.getChildren(watchedEvent.getPath(), true);
						System.out.println("children changed ....:" + children);
					}
				}
			} catch (InterruptedException | KeeperException e) {
				e.printStackTrace();
			}
		}
	}
}
