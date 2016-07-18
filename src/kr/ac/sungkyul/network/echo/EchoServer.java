package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
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

			// 3. accept
			Socket socket = serverSocket.accept();

			// 4. 연결 성공
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println("[echo] 연결 성공 from " + remoteHostAddress + ":" + remoteHostPort);

			try {
				// 5. IOStream
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				String data = null;
				while (true) {
					// 6. 데이터 읽기
					byte[] buffer = new byte[256];
					int readBytes = is.read(buffer);

					// 클라이언트가 연결을 끊은 경우
					if (readBytes <= -1) {
						System.out.println("[echo] closed by client");
						return;
					}

					// 데이터가 온 경우
					data = new String(buffer, 0, readBytes, "utf-8");
					System.out.println("[client] : " + data);

					// 7. 데이터 쓰기
					os.write(data.getBytes("utf-8"));
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
