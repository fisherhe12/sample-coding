package com.github.fisherhe12.zk;

import com.google.common.collect.Lists;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.security.x509.IPAddressName;

import java.io.IOException;
import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;

/**
 * Zookeeper 原生客户端一些常用操作
 *
 * @author fisher
 */
public class ZkClientTest {

	private static final String IP_ADDRESS = "172.18.8.20:2181";
	private static ZooKeeper zooKeeper;
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static Stat stat = new Stat();

	@Before
	public void init() {
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
	public void createSync() throws KeeperException, InterruptedException {
		//创建瞬时的节点
		String path = zooKeeper.create("/user", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Success create ephemeral zNode:" + path);

		//创建瞬时有序的节点
		String path1 = zooKeeper
				.create("/user-", "fisher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("Success create ephemeralSequential zNode:" + path1);


		zooKeeper.create("/book/java", "2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zooKeeper.exists("/book/java", true);
		TimeUnit.SECONDS.sleep(1);
		zooKeeper.exists("/book/java", true);
		TimeUnit.SECONDS.sleep(1);

	}

	@Test
	public void createAsync() throws InterruptedException {
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
	public void delete() throws KeeperException, InterruptedException {
		zooKeeper.delete("/user", 0);
	}

	@Test
	public void get() throws KeeperException, InterruptedException {
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
	public void auth() {
		try {
			ZooKeeper zooKeeper = new ZooKeeper(IP_ADDRESS, 5000, new InitWatcher());
			ACL acl = new ACL(ZooDefs.Perms.CREATE, new Id("digest", "root:root"));
			ACL acl1 = new ACL(ZooDefs.Perms.CREATE, new Id("ip", "127.0.0.1"));

			ArrayList<ACL> acls = Lists.newArrayList(acl,acl1);

			zooKeeper.create("/user", "1".getBytes(), acls, CreateMode.EPHEMERAL);
			zooKeeper.addAuthInfo("digest", "root:root".getBytes());

			zooKeeper.create("/user", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

			byte[] data = zooKeeper.getData("/user", true, stat);
			System.out.println(new String(data));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}

	@After
	public void close() {
		try {
			zooKeeper.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class InitWatcher implements Watcher {

		@Override
		public void process(WatchedEvent watchedEvent) {
			System.out.println("Receive watched event:" + watchedEvent);
			if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
				if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
					connectedSemaphore.countDown();
				} else if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
					try {
						List<String> children = zooKeeper.getChildren(watchedEvent.getPath(), true);
						System.out.println("children changed ....:" + children);
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				} else if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
					try {
						byte[] data = zooKeeper.getData(watchedEvent.getPath(), true, stat);

						System.out.println("node data changed ....:" + new String(data));
						System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
				} else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
					System.out.println("Node created...");

				} else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
					System.out.println("Delete ....");
				}
			}
		}
	}

}



