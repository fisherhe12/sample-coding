package com.github.fisherhe12.jdk.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * jdk1.8新增的性能较好的读写锁
 * @author fisher
 * @date 2018-08-06
 */
public class StampedLockSample {

	public static void main(String[] args) {
		Point point = new Point();

		ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(), r -> {
			Thread thread = new Thread(r);
			thread.setName("thread");
			return thread;
		});

		executorService.submit(() -> point.move(10.00, 10.00));


		executorService.submit(() -> {
			double distanceFormOrigin = point.distanceFormOrigin();
			System.out.println(
					Thread.currentThread().getName()+"," + Thread.currentThread().getId() + ":" + distanceFormOrigin);
		});

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				point.move(20.00, 20.10);
			}
		});
		executorService.submit(() -> {
			double distanceFormOrigin = point.distanceFormOrigin();
			System.out.println(
					Thread.currentThread().getName() +","+ Thread.currentThread().getId() + ":" + distanceFormOrigin);
		});
	}


}

class Point {
	private final StampedLock s1 = new StampedLock();
	private double x, y;

	void move(double deltaX, double deltaY) {
		//这里的含义和distanceFormOrigin方法中 s1.readLock()是类似的
		long stamp = s1.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			//退出临界区,释放写锁
			s1.unlockWrite(stamp);
		}
	}

	double distanceFormOrigin() {
		//只读方法
		//试图尝试一次乐观读 返回一个类似于时间戳的邮戳整数stamp 这个stamp就可以作为这一个所获取的凭证
		long stamp = s1.tryOptimisticRead();
		//读取x和y的值,这时候我们并不确定x和y是否是一致的
		double currentX = x, currentY = y;
		//判断这个stamp是否在读过程发生期间被修改过,如果stamp没有被修改过,责任无这次读取时有效的,因此就可以直接return了,反之,如果stamp是不可用的,则意味着在读取的过程中,可能被其他线程改写了数据,因此,有可能出现脏读,如果如果出现这种情况,我们可以像CAS操作那样在一个死循环中一直使用乐观锁,知道成功为止
		if (!s1.validate(stamp)) {
			//也可以升级锁的级别,这里我们升级乐观锁的级别,将乐观锁变为悲观锁, 如果当前对象正在被修改,则读锁的申请可能导致线程挂起.
			stamp = s1.readLock();
			try {
				currentX = x;
				currentY = y;
			} finally {
				//退出临界区,释放读锁
				s1.unlockRead(stamp);
			}
		}
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}
}
