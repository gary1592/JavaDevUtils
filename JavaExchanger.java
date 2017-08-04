import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class JavaExchanger {

	private Exchanger<String> exchanger = new Exchanger<>();

	private ExecutorService service = Executors.newFixedThreadPool(2);

	@Test
	public void test() {
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String A = "银行流水A";
					exchanger.exchange(A);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String B = "银行流水B";
					String A = exchanger.exchange(B);
					System.out.println("A和B数据是否一致： " + A.equals(B) + ", A录入的是： " + A + ", B录入的是： " + B);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});

		service.shutdown();
	}
}
