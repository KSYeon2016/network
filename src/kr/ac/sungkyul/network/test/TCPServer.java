package kr.ac.sungkyul.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private final static int SERVER_PORT = 1000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩(서버의 ip주소와 포트) ipconfig
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress =
					new InetSocketAddress(serverAddress, SERVER_PORT);

			serverSocket.bind(inetSocketAddress);
			System.out.println("[server] bind - " + serverAddress + ":" + SERVER_PORT);

			// 3. accept : 클라이언트로부터 연결(요청) 대기
			Socket socket = serverSocket.accept(); // blocking (다음 라인으로 내려가지 않고 대기)

			// 4. 연결 성공
			InetSocketAddress remoteAddress =
					(InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println(
					"[server] 연결 성공 from " + remoteHostAddress + ":" + remoteHostPort);

			try{
				// 5. IOStream
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				// 6. 데이터 읽기
				byte[] buffer = new byte[256];
				int readBytes = is.read(buffer);	// blocking
				
				// 클라이언트가 연결을 끊은 경우(정상 종료)
				if(readBytes <= -1){
					System.out.println("[server] closed by client");
					return;
				}
				
				// 데이터가 온 경우 : buffer를 0에서 readBytes까지
				String data = new String(buffer, 0, readBytes, "utf-8");
				System.out.println("[server] received : " + data);
				
				// 7. 데이터 쓰기
//				os.write(buffer);
				os.write(data.getBytes("utf-8"));
			} catch(SocketException e){
				System.out.println("[server] 비정상적으로 클라이언트가 연결을 끊었습니다.");
			} catch(IOException e){
				e.printStackTrace();
			} finally {
				// 8. 데이터 통신 소켓 닫기
				if(socket != null && socket.isClosed() == false){
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
