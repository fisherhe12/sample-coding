package com.github.fisherhe12.redis;

import com.github.fisherhe12.common.constant.LockConstant;
import redis.clients.jedis.Jedis;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 基于单机部署的Redis分布式锁
 * @author fisher
 */
public class RedisDistributedLock {

	protected Jedis jedis;

	protected String lockKey;

	protected String lockValue;

	protected volatile boolean isOpenExpirationRenewal = true;

	public RedisDistributedLock(Jedis jedis, String lockKey) {
		this(jedis, lockKey, UUID.randomUUID().toString() + Thread.currentThread().getId());
	}

	public RedisDistributedLock(Jedis jedis, String lockKey, String lockValue) {
		this.jedis = jedis;
		this.lockKey = lockKey;
		this.lockValue = lockValue;
	}

	public void sleepBySecond(int second) {
		try {
			TimeUnit.SECONDS.sleep(second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void scheduleExpirationRenewal() {
		Thread renewalThread = new Thread(new ExpirationRenewal());
	}


	public void tryLock(long time, TimeUnit unit) {
		while (true) {
			String result = jedis.set(lockKey, lockValue, LockConstant.NOT_EXIST, LockConstant.SECONDS, 30);
			if (LockConstant.OK.equals(result)) {
				System.out.println("线程id:" + Thread.currentThread().getId() + "加锁成功!时间:" + LocalTime.now());

				//开启定时刷新过期时间
				isOpenExpirationRenewal = true;
				scheduleExpirationRenewal();
				break;
			}
			System.out.println("线程id:" + Thread.currentThread().getId() + "获取锁失败，休眠10秒!时间:" + LocalTime.now());
			//休眠10秒
			sleepBySecond(10);
		}
	}

	public void unlock() {
		System.out.println("线程id:" + Thread.currentThread().getId() + "解锁!时间:" + LocalTime.now());

		String checkAndDelScript =
				"if redis.call('get', KEYS[1]) == ARGV[1] then " + "return redis.call('del', KEYS[1]) " + "else "
						+ "return 0 " + "end";
		jedis.eval(checkAndDelScript, 1, lockKey, lockValue);
		isOpenExpirationRenewal = false;
	}

	/**
	 * 刷新key的过期时间
	 */
	private class ExpirationRenewal implements Runnable {
		@Override
		public void run() {
			while (isOpenExpirationRenewal) {
				System.out.println("执行延迟失效时间中...");

				String checkAndExpireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then "
						+ "return redis.call('expire',KEYS[1],ARGV[2]) " + "else " + "return 0 end";
				jedis.eval(checkAndExpireScript, 1, lockKey, lockValue, "30");

				//休眠10秒
				sleepBySecond(10);
			}
		}
	}
}
