package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer {
	private final static int PORT = 1000;
	private final static int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			// 1. 소켓 생성
			socket = new DatagramSocket(PORT);
			
			while(true){
				// 2. 수신 대기
				DatagramPacket receiverPacket =
						new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receiverPacket);	// blocking
				
				// 3. 데이터 수신
				String message = new String(
						receiverPacket.getData(),
						0,
						receiverPacket.getLength(),
						StandardCharsets.UTF_8);	// 바이트 단위
				System.out.println("수신:" + message);
				
				// 4. 데이터 송신
				byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
				DatagramPacket sendPacket = new DatagramPacket(
						sendData,
						sendData.length,
						new InetSocketAddress(
								receiverPacket.getAddress(), receiverPacket.getPort()));
				socket.send(sendPacket);
			}
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
