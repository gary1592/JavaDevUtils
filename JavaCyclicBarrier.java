import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;

public class JavaCyclicBarrier {

	public static class BankWaterService implements Runnable {

		// 创建4个屏障，处理完之后执行当前类的run方法
		private CyclicBarrier cb = new CyclicBarrier(4, this);

		// 假设有4个sheet，创建4个线程
		private Executor executor = Executors.newFixedThreadPool(4);

		// 保存每个sheet的结果
		private ConcurrentHashMap<String, Integer> sheetMap = new ConcurrentHashMap<>();

		private void count() {
			for (int i = 0; i < 4; i++) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						sheetMap.put(Thread.currentThread().getName(), 1);
						try {
							cb.await();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		}

		@Override
		public void run() {
			int result = 0;
			for (Entry<String, Integer> sheet : sheetMap.entrySet()) {
				result += sheet.getValue();
			}
			System.out.println(result);
		}

		@Test
		public void test() {
			BankWaterService service = new BankWaterService();
			service.count();
		}

	}
}
