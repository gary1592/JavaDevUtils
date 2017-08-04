import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class JavaExecutor {

	public static class ThreadOne implements Runnable {

		@Override
		public void run() {
			System.out.println("I'm " + Thread.currentThread().getName());
		}

	}

	public static class ThreadTwo implements Callable<String> {
		private int id;

		public ThreadTwo(int id) {
			this.id = id;
		}

		@Override
		public String call() throws Exception {
			String name = Thread.currentThread().getName();
			System.out.println(name + " is called");
			return name + " result is " + id;
		}

	}

	public static class MyThread implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " 正在执行。。。");
		}
	}

	@Test
	public void testRunTask() {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			// 执行Runnable任务，无返回值
			executorService.execute(new ThreadOne());
			System.out.println(i + 11);
		}
		executorService.shutdown();
	}

	@Test
	public void testCallTask() throws InterruptedException, ExecutionException {
		List<Future<String>> list = new ArrayList<>();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 20; i++) {
			// 执行Callable任务，并将结果保存在future中
			Future<String> future = executorService.submit(new ThreadTwo(i + 1));
			list.add(future);
		}
		for (Future<String> future : list) {
			// Future返回如果没有完成，则一直循环等待，直到Future返回完成
			while (!future.isDone()) {
			}
			// 输出各个线程（任务）执行的结果
			System.out.println(future.get());
		}
		// 启动一次顺序关闭，执行以前提交的任务，不接受新任务
		executorService.shutdown();
	}

	@Test
	public void testSelfThreadPool() {
		// 创建等待队列
		BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(20);
		// 创建线程池，池中保存的线程数为3，允许的最大线程数为5
		ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 50, TimeUnit.MILLISECONDS, bqueue);
		// 创建七个任务
		Runnable t1 = new MyThread();
		Runnable t2 = new MyThread();
		Runnable t3 = new MyThread();
		Runnable t4 = new MyThread();
		Runnable t5 = new MyThread();
		Runnable t6 = new MyThread();
		Runnable t7 = new MyThread();
		// 每个任务会在一个线程上执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		pool.execute(t6);
		pool.execute(t7);
		// 关闭线程池
		pool.shutdown();
	}
}
