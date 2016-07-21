package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoClient {
	private final static String SERVER_IP = "220.67.115.225";
	private final static int SERVER_PORT = 1000;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			// 1. 소켓 생성
			socket = new DatagramSocket();
			
			// 2. 데이터 송신
			String message = "Hello World";
			byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
			DatagramPacket sendPacket = new DatagramPacket(
					sendData,
					sendData.length,	// 버퍼 길이
					new InetSocketAddress(SERVER_IP, SERVER_PORT));	// 받는 주소
			
			socket.send(sendPacket);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
	}

}
