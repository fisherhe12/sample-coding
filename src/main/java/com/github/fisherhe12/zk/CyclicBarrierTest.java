package com.github.fisherhe12.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 *  基于ZK实现的分布式环境下的cyclicBarrier实例
 * @author fisher
 * @date 2018-07-09
 */
public class CyclicBarrierTest {
	private static final String IP_ADDRESS = "116.196.99.138:2181";
	private static final String CYCLIC_BARRIER_PATH = "/cyclic-barrier";

	@Test
	public void cyclic() throws Exception {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(IP_ADDRESS)
							.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("root")
							.build();
					curatorFramework.start();
					DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(curatorFramework,
							CYCLIC_BARRIER_PATH, 5);
					TimeUnit.SECONDS.sleep(Math.round(Math.random() * 3));
					System.out.println(Thread.currentThread().getName() + " barrier ready...");
					barrier.enter();
					System.out.println("start...");
					TimeUnit.SECONDS.sleep(Math.round(Math.random() * 3));
					barrier.leave();
					System.out.println("quit...");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}).start();
		}
	}

}
