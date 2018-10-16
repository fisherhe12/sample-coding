package com.github.fisherhe12.zk;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.StandardLockInternalsDriver;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper 分布式锁实现
 * @author fisher
 */
class DistributedLockTest {

	private static final String IP_ADDRESS = "172.18.8.20:2181";
	private static final String LOCK_PATH = "/myLock";
	private CuratorFramework curatorFramework;

	@BeforeEach
	void init() {
		curatorFramework = CuratorFrameworkFactory.builder().connectString(IP_ADDRESS).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("root").build();
		curatorFramework.start();

	}

	/**
	 * 公平锁实现订单序列号生成
	 * 1. 当前客户端通过getChildren（/mylock）获取所有子节点列表并根据自增数字排序，
	 * 然后判断一下自己创建的节点的顺序是不是在列表当中最小的，如果是 那么获取到锁，如果不是，那么获取自己的前一个节点，
	 * 并设置监听这个节点的变化，当节点变化时重新执行此操作，直到自己是编号最小的一个为止
	 * 2. 释放锁，当前获得锁的客户端在操作完成后删除自己创建的节点，这样会激发zk的事件给其它客户端知道，这样其它客户端会重新执行步驟1
	 */
	@Test
	void fairGenerateNo() throws Exception {
		InterProcessMutex lock = new InterProcessMutex(curatorFramework, LOCK_PATH);
		generateOrderNo(lock);

	}

	/**
	 * 自定义非公平锁实现订单序列号生成
	 */
	@Test
	void unfairGenerateNo() throws Exception {
		InterProcessMutex lock = new InterProcessMutex(curatorFramework, LOCK_PATH, new NoFairLockDriver());
		generateOrderNo(lock);
	}

	private void generateOrderNo(InterProcessMutex lock) throws Exception {
		PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, LOCK_PATH, false);
		pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
		pathChildrenCache.getListenable().addListener((client, event) -> {
			System.out.println(event.getType() + ":" + event.getData().getPath());
		});
		CountDownLatch countDownLatch = new CountDownLatch(50);
		for (int i = 0; i < 50; i++) {
			new Thread(() -> {
				try {
					lock.acquire();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
					String orderNo = simpleDateFormat.format(new Date());
					System.out.println(
							Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + "-" + orderNo);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						countDownLatch.countDown();
						lock.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

		}
		countDownLatch.await();
	}


}

class NoFairLockDriver extends StandardLockInternalsDriver {

	private static int DEFAULT_LENGTH = 5;
	/**
	 * 随机数的长度
	 */
	private int numLength;

	NoFairLockDriver() {
		this(DEFAULT_LENGTH);
	}

	private NoFairLockDriver(int numLength) {
		this.numLength = numLength;
	}

	@Override
	public String createsTheLock(CuratorFramework client, String path, byte[] lockNodeBytes) throws Exception {
		String newPath = path + getRandomSuffix();
		String ourPath;
		if (lockNodeBytes != null) {
			//原来使用的是CreateMode.EPHEMERAL_SEQUENTIAL类型的节点
			//节点名称最终是这样的_c_c8e86826-d3dd-46cc-8432-d91aed763c2e-lock-0000000025
			//其中0000000025是zk服务器端资自动生成的自增序列 从0000000000开始
			//所以每个客户端创建节点的顺序都是按照0，1，2，3这样递增的顺序排列的，所以他们获取锁的顺序与他们进入的顺序是一致的，这也就是所谓的公平锁
			//现在我们将有序的编号换成随机的数字，这样在获取锁的时候变成非公平锁了
			ourPath = client.create().creatingParentContainersIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL)
					.forPath(newPath, lockNodeBytes);
			//ourPath = client.create().creatingParentContainersIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, lockNodeBytes);
		} else {
			ourPath = client.create().creatingParentContainersIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL)
					.forPath(newPath);
		}
		return ourPath;
	}

	/**
	 * 获得随机数字符串
	 */
	private String getRandomSuffix() {
		return RandomStringUtils.randomNumeric(numLength, numLength + 1);
	}


}
