package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient2 {
	private final static String SERVER_IP = "220.67.115.225";
	private final static int SERVER_PORT = 2000;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;

		try {
			// 1. 소켓 생성
			socket = new Socket();

			// 2. 서버 연결
			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			// 3. Stream. 바이트단위로 받아오지 않음
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			// true : autoflush

			while (true) {
				// 메세지입력
				System.out.print(">> ");
				String message = scanner.nextLine();
				
				if("exit".equals(message)){
					System.out.println("Echo 프로그램을 종료합니다.");
					break;
				}
				
				// 메세지 보내기
				pw.println(message);

				// 메세지 다시 받기
				String messageEcho = br.readLine();

				// 서버가 연결을 끊은 경우
				if (messageEcho == null) {
					System.out.println("[echo] close by server");
					break;
				}

				// data 받기
				System.out.println("<< " + messageEcho);
			}
		} catch (SocketException e) {
			System.out.println("[echo] 비정상적으로 서버로부터 연결이 끊겼습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
