package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	List<PrintWriter> listWriters;

	public ChatServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}
	
	@Override
	public void run() {
		// 1. Remote Host Information
		InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetSocketAddress.getHostName();
		int remoteHostPort = inetSocketAddress.getPort();
		ChatServer.log( "연결됨 from " + remoteHostAddress + ":" + remoteHostPort );
		
		try {
			// 2. Stream
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8), true);
			
			// 3. 요청 처리
			while(true){
				String request = br.readLine();
				
				if(request == null){
					ChatServer.log("클라이언트로부터 연결 끊김");
					doQuit(pw);
					break;
				}
				
				// 4. 프로토콜 분석
				String[] tokens = request.split(":");
				
				if("join".equals(tokens[0])){
					doJoin(tokens[1], pw);
				} else if("message".equals(tokens[0])){
					doMessage(tokens[1]);
				} else if("quit".equals(tokens[0])){
					doQuit(pw);
					break;
				} else{
					ChatServer.log("에러 : 알 수 없는 요청 (" + tokens[0] + ")");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doJoin(String nickname, PrintWriter pw){
		this.nickname = nickname;
		
		String data = nickname + "님이 참여하였습니다.";
		broadcast(data);
		
		// writer pool에 저장
		listWriters.add(pw);
		
		// ack
		pw.println("join:ok");
		pw.flush();
	}
	
	private void broadcast(String data) {
//		for(Writer writer : listWriters){
//			PrintWriter printWriter = (PrintWriter)writer;
//			printWriter.println(data);
//			printWriter.flush();
//		}
		int count = listWriters.size();
		for( int i = 0; i < count; i++ ) {
			PrintWriter pw = listWriters.get( i );
			pw.println( data );
			pw.flush();
		}
	}

	private void doMessage(String message) {
		String msgBc = nickname + ":" + message;
		broadcast(msgBc);
	}
	
	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}
	
	private void removeWriter(Writer writer){
		listWriters.remove(writer);
	}

}
