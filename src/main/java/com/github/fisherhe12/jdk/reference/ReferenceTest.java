package com.github.fisherhe12.jdk.reference;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * java强、弱、软、幻象引用的测试例子
 *
 * @author fisher
 * @date 2018-09-29
 */
class ReferenceTest {
	/**
	 * StrongReference 是 Java 的默认引用实现, 它会尽可能长时间的存活于 JVM 内， 当没有任何对象指向它时 GC 执行后将会被回收
	 */
	@Test
	void strongReference() {
		Object referent = new Object();

		// 通过赋值创建 StrongReference
		Object strongReference = referent;

		Assertions.assertSame(referent, strongReference);

		referent = null;
		System.gc();

		// StrongReference 在 GC 后不会被回收
		Assertions.assertNotNull(strongReference);

	}

	/**
	 * 弱引用，当所引用的对象在jvn内不再有强引用时,GC后将会被自动回收
	 */
	@Test
	void weakReference() {
		Object referent = new Object();
		WeakReference<Object> weakReference = new WeakReference<>(referent);

		Assertions.assertSame(referent, weakReference.get());

		referent = null;
		System.gc();

		//一旦没有指向 referent 的强引用, weak reference 在 GC 后会被自动回收
		Assertions.assertNull(weakReference.get());
	}

	/**
	 * WeakHashMap 使用 WeakReference 作为 key， 一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry
	 */
	@Test
	void weakHashMap() throws InterruptedException {
		Map<Object, Object> weakHashMap = new WeakHashMap<>();
		Object key = new Object();
		Object value = new Object();
		weakHashMap.put(key, value);

		Assertions.assertTrue(weakHashMap.containsValue(value));

		key = null;
		System.gc();

		/**
		 * 等待无效 entries 进入 ReferenceQueue 以便下一次调用 getTable 时被清理
		 */
		Thread.sleep(1000);

		/**
		 * 一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry
		 */
		Assertions.assertFalse(weakHashMap.containsValue(value));
	}

	/**
	 * SoftReference 于 WeakReference 的特性基本一致， 最大的区别在于 SoftReference 会尽可能长的保留引用直到 JVM 内存不足时才会被回收(虚拟机保证), 这一特性使得 SoftReference 非常适合缓存应用
	 */
	@Test
	void softReference() {
		Object referent = new Object();
		SoftReference<Object> softRerference = new SoftReference<>(referent);

		Assertions.assertNotNull(softRerference.get());

		referent = null;
		System.gc();

		// soft references 只有在 jvm OutOfMemory 之前才会被回收, 所以它非常适合缓存应用
		Assertions.assertNotNull(softRerference.get());
	}

	/**
	 * PhantomReference 有两个好处， 其一， 它可以让我们准确地知道对象何时被从内存中删除
	 * 其二， 它可以避免 finalization 带来的一些根本性问题, 上文提到 PhantomReference 的唯一作用就是跟踪 referent 何时被 enqueue 到 ReferenceQueue
	 */
	@Test
	void phantomReferenceAlwaysNull() {
		Object referent = new Object();
		PhantomReference<Object> phantomReference = new PhantomReference<>(referent, new ReferenceQueue<>());

		// phantom reference 的 get 方法永远返回 null
		Assertions.assertNull(phantomReference.get());
	}

}
