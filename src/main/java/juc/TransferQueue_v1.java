package juc;

import java.util.concurrent.LinkedTransferQueue;

public class TransferQueue_v1 {
	public static void main(String[] args) throws InterruptedException {
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				while(true){
					try {
						Thread.sleep(1000);
						System.out.println(strs.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			},"c"+i).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(()->{
				for (int j = 0; j < 10; j++) {
					try {
						strs.transfer(Thread.currentThread().getName()+" object "+j+"被传递");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+" object "+j+"被消费");
				}
			},"p"+i).start();


		}

		Thread.sleep(60000);

		System.out.println("end");
		
		//strs.put("aaa");


		/*new Thread(() -> {
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();*/


	}
}
