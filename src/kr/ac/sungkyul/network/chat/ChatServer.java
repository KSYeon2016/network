package kr.ac.sungkyul.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class ChatServer {
	private final static int SERVER_PORT = 5000;

	public static void main(String[] args) {
		List<PrintWriter> listWriter = new Vector<PrintWriter>();
		
		try {
			// 1. 서버 소켓 생성
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket();
			
			// 2. 바인딩
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, SERVER_PORT));
			log("연결 기다림 " + hostAddress + ":" + SERVER_PORT);
			
			while(true){
				// 3. 요청 대기
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriter).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String logMessage) {
		System.out.println(logMessage);
	}

}
