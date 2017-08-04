import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.junit.Test;

public class JavaSemaphore {

	private static final int THREAD_COUNT = 30;

	private static ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);

	private Semaphore semaphore = new Semaphore(10);

	@Test
	public void test() {
		for (int i = 0; i < THREAD_COUNT; i++) {
			service.execute(new Runnable() {

				@Override
				public void run() {
					try {
						semaphore.acquire();
						System.out.println(Thread.currentThread().getName() + " save data");
						semaphore.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}
		service.shutdown();
	}
}
