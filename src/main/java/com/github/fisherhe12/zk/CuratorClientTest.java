package com.github.fisherhe12.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Curator客戶端进行Zookeeper操作
 *
 * @author fisher
 */
public class CuratorClientTest {
	private static final String IP_ADDRESS = "172.18.8.20:2181";
	private CuratorFramework  curatorFramework;

	@Before
	public void init() {
		curatorFramework = CuratorFrameworkFactory.builder().connectString(IP_ADDRESS).sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("root").build();
		curatorFramework.start();
	}

	@Test
	public void crud() throws Exception {
		String result = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
				.forPath("/book/java/zk", "curator".getBytes());
		System.out.println(result);

		List<String> list = curatorFramework.getChildren().forPath("/book");
		for (String s : list) {
			System.out.println(s);
		}
		Stat stat1 = curatorFramework.setData().forPath("/book/java/zk", "curator->update first".getBytes());
		curatorFramework.setData().withVersion(1).forPath("/book/java/zk", "curator->update second".getBytes());
		System.out.println(stat1);

		Stat stat = new Stat();
		byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/book/java/zk");
		System.out.println(new String(bytes));

		curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath("/book");

	}

	@Test
	public void crudAsync() {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		try {
			curatorFramework.create().
					creatingParentsIfNeeded().
					withMode(CreateMode.EPHEMERAL).
					inBackground((client, event) -> System.out.println(
							Thread.currentThread().getName() + "->resultCode:" + event.getResultCode() + "->" + event
									.getType()), executorService).
					forPath("/node", "123".getBytes());


		} catch (Exception e) {
			e.printStackTrace();
		}

		executorService.shutdown();
	}

	@Test
	public void transactionOps() {
		try {
			CuratorOp createOp = curatorFramework.transactionOp().create().forPath("/book", "java".getBytes());
			CuratorOp setDataOp = curatorFramework.transactionOp().setData().forPath("/book/java", "python".getBytes());
			CuratorOp deleteOp = curatorFramework.transactionOp().delete().forPath("/book");
			List<CuratorTransactionResult> curatorTransactionResults = curatorFramework.transaction()
					.forOperations(createOp,setDataOp,deleteOp);
			for (CuratorTransactionResult result : curatorTransactionResults) {
				System.out.println(result.getForPath() + " - " + result.getType());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@After
	public void close() {
		curatorFramework.close();
	}

}
