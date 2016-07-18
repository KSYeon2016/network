package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {
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

			// 3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			String data = null;
			while (true) {
				System.out.print(">> ");
				data = scanner.nextLine();
				
				if(data.equals("exit")){
					System.out.println("Echo 프로그램을 종료합니다.");
					break;
				}

				// 4. 데이터 쓰기
				os.write(data.getBytes("UTF-8"));

				// 5. 데이터 읽기
				byte[] buffer = new byte[256];
				int readBytes = is.read(buffer);

				// 서버가 연결을 끊은 경우
				if (readBytes <= -1) {
					System.out.println("[echo] close by server");
					return;
				}

				// data 받기
				data = new String(buffer, 0, readBytes, "utf-8");
				System.out.println("<< " + data);
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
