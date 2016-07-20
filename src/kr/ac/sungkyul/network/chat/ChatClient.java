package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	private final static String SERVER_IP = "220.67.115.225";
	private final static int SERVER_PORT = 5000;

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;
		
		try{
			// 1. 키보드 연결
			scanner = new Scanner(System.in);
			
			// 2. socket 생성
			socket = new Socket();
			
			// 3. 연결
			InetSocketAddress serverSocketAddress =
					new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);
			
			// 4. reader/writer 생성
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8), true);
			
			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();
			pw.println("join:" + nickname);
			pw.flush();
			
			// 6. ChatClientThread 시작
			new ChatClientThread(br).start();
			
			// 7. 키보드 입력 처리
			while(true){
				System.out.print(">>");
				String input = scanner.nextLine();
				
				if("QUIT".equals(input.toUpperCase()) == true){
					// 8. quit 프로토콜 처리
					pw.println("quit");
					pw.flush();
					break;
				}else{
					// 9. 메시지 처리
					pw.println("message:" + input);
					pw.flush();
				}
			}
		}catch(IOException e){
			ChatServer.log("error:" + e);
		}finally{
			try {
				// 10. 자원정리
				socket.close();
				scanner.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
