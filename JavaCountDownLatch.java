import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class JavaCountDownLatch {

	private CountDownLatch countDownLatch = new CountDownLatch(2);

	@Test
	public void test() throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(1);
				countDownLatch.countDown();
				System.out.println(2);
				countDownLatch.countDown();
			}
		}).start();

		countDownLatch.await();
		System.out.println(3);
	}
}
