package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer2 {
	private final static int SERVER_PORT = 2000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, SERVER_PORT);

			serverSocket.bind(inetSocketAddress);
			System.out.println("[echo] bind - " + serverAddress + ":" + SERVER_PORT);

			// 3. accept : 연결 요청 대기
			Socket socket = serverSocket.accept();

			// 4. 연결 성공
			InetSocketAddress remoteAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println("[echo] 연결 성공 from " + remoteHostAddress + ":" + remoteHostPort);

			try {
				// Stream
				BufferedReader br = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), "utf-8"));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
	
				while (true) {
					// 데이터 읽기
					String data = br.readLine();

					// 클라이언트가 연결을 끊은 경우
					if (data == null) {
						System.out.println("[echo] closed by client");
						break;
					}

					// 데이터가 온 경우
					System.out.println("[client] : " + data);

					// 데이터 쓰기
					pw.println(data);
				}
			} catch (SocketException e) {
				System.out.println("[echo] 비정상적으로 클라이언트가 연결을 끊었습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 8. 데이터 통신 소켓 닫기
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 9. 프로그램 전체가 끝났을 때 서버소켓을 닫아줌
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
