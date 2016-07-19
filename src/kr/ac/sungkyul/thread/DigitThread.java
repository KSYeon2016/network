package kr.ac.sungkyul.thread;

public class DigitThread extends Thread {

	@Override
	public void run() {
		for(int i = 0; i < 9; i++){
			System.out.print(i);
			try {
				Thread.sleep(1000);	// 1초 쉬어줌
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
