package com.github.fisherhe12.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Master 选举
 * @author fisher
 * @date 2018-07-03
 */
public class MasterSelectorTest {

	private static final String IP_ADDRESS = "172.18.8.20:2181";
	private static final String MASTER_SELECTOR_PATH = "/master-selector";
	private CuratorFramework curatorFramework;

	@Before
	public void init() {
		curatorFramework = CuratorFrameworkFactory.builder().connectString(IP_ADDRESS).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("root").build();
		curatorFramework.start();
	}

	@Test
	public void elect() throws InterruptedException {
		LeaderSelector selector=new LeaderSelector(curatorFramework, MASTER_SELECTOR_PATH, new LeaderSelectorListenerAdapter() {
			@Override
			public void takeLeadership(CuratorFramework client) throws  Exception{
				List<String> list = client.getChildren().forPath(MASTER_SELECTOR_PATH);
				for (String s : list) {
					System.out.println("子节点变化:"+s);
				}
				System.out.println("获取master权限....");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("处理完操作,释放master权限...");

			}
		});
		selector.autoRequeue();
		selector.start();
		TimeUnit.SECONDS.sleep(Long.MAX_VALUE);

	}
}
