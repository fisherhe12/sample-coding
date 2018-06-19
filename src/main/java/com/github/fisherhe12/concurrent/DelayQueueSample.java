package com.github.fisherhe12.concurrent;

import com.google.common.primitives.Ints;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 手动写一个延迟队列
 *
 * @author fisher
 * @date 2018-05-24
 */
public class DelayQueueSample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayQueue<Order> queue = new DelayQueue<>();

        OrderProducer producer = new OrderProducer(queue, 2, 10000);

        OrderConsumer consumer = new OrderConsumer(queue, 2);

        executorService.submit(producer);
        executorService.submit(consumer);

        executorService.awaitTermination(50, TimeUnit.SECONDS);
        executorService.shutdown();

    }
}


class OrderProducer implements Runnable {
    private DelayQueue<Order> queue;
    private Integer numOfOrdersToProduce;
    private Integer delayMilliseconds;

    public OrderProducer(DelayQueue<Order> queue, Integer numOfOrdersToProduce, Integer delayMilliseconds) {
        this.queue = queue;
        this.numOfOrdersToProduce = numOfOrdersToProduce;
        this.delayMilliseconds = delayMilliseconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < numOfOrdersToProduce; i++) {
            Order order = new Order(delayMilliseconds);
            System.out.println("生产延时订单:" + order);
            try {
                queue.put(order);
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


class OrderConsumer implements Runnable {
    private AtomicInteger numOfConsumedOrders = new AtomicInteger();
    private DelayQueue<Order> queue;
    private Integer numOfOrdersToTake;

    public OrderConsumer(DelayQueue<Order> queue, Integer numOfOrdersToTake) {
        this.queue = queue;
        this.numOfOrdersToTake = numOfOrdersToTake;
    }

    public AtomicInteger getNumOfConsumedOrders() {
        return numOfConsumedOrders;
    }

    @Override
    public void run() {
        for (int i = 0; i < numOfOrdersToTake; i++) {
            try {
                Order order = queue.take();
                numOfConsumedOrders.incrementAndGet();
                System.out.println("消费延时的订单:" + order.getOrderId() + "消费时间:" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


class Order implements Delayed {
    /**
     * 订单ID
     */
    private long orderId;

    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 过期时间
     */
    private long expireTime;
    /**
     * 超时时长
     */
    private long timeOut;

    public Order(long timeOut) {
        this.timeOut = timeOut;
        this.createTime = System.currentTimeMillis();
        this.expireTime = System.currentTimeMillis() + timeOut;
        this.orderId = RandomUtils.nextLong();
    }

    public long getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", timeOut=" + timeOut +
                '}';
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = this.expireTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Ints.saturatedCast(this.expireTime - ((Order) o).expireTime);
    }


}










