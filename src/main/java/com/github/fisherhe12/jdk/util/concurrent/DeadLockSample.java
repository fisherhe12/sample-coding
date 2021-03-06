package com.github.fisherhe12.jdk.util.concurrent;

/**
 * 一个经典的死锁的例子
 *
 * @author Administrator
 */
public class DeadLockSample extends Thread {
	private String first;
	private String second;

	private DeadLockSample(String name, String first, String second) {
		super(name);
		this.first = first;
		this.second = second;
	}

	public static void main(String[] args) throws InterruptedException {
		String lockA = "lockA";
		String lockB = "lockB";
		DeadLockSample t1 = new DeadLockSample("Thread1", lockA, lockB);
		DeadLockSample t2 = new DeadLockSample("Thread2", lockB, lockA);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	@Override
	public void run() {
		synchronized (first) {
			System.out.println(this.getName() + " obtained: " + first);
			try {
				Thread.sleep(1000L);
				synchronized (second) {
					System.out.println(this.getName() + " obtained: " + second);
				}
			} catch (InterruptedException e) {
				//do nothing
			}
		}
	}
}
