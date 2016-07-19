package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 다중처리 Echo Server
 */
public class EchoServer3 {
	private final static int SERVER_PORT = 2000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress =
					new InetSocketAddress(serverAddress, SERVER_PORT);

			serverSocket.bind(inetSocketAddress);
			System.out.println("[echo] bind - " + serverAddress + ":" + SERVER_PORT);

			while (true) {
				// accept : 연결 요청 대기
				Socket socket = serverSocket.accept();

				EchoServer3ReceiveThread thread = new EchoServer3ReceiveThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 프로그램 전체가 끝났을 때 서버소켓을 닫아줌
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
