package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread {
	private BufferedReader br;
	
	public ChatClientThread(BufferedReader br) {
		this.br = br;
	}
	
	@Override
	public void run() {
		while(true){
			// reader를 통해 읽은 데이터 콘솔에 출력하기(message 처리)
			try {
				String message = br.readLine();
				
				if(message == null){
					break;
				}
				
				System.out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
