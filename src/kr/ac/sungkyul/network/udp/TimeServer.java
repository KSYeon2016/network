package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private final static int PORT = 5000;
	private final static int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		String data = null;
		byte[] sendData = null;
		
		try {
			// 소켓 생성
			socket = new DatagramSocket(PORT);
			
			while(true){
				// 수신 대기
				DatagramPacket receivePacket = new DatagramPacket(
						new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				// 데이터 수신
				String message = new String(
						receivePacket.getData(), 
						0, 
						receivePacket.getLength(), 
						StandardCharsets.UTF_8);
				System.out.println("수신:" + message);
				
				// 요청 수신
				if("".equals(message)){
					// 타임 메시지
					SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
					data = "서버 시간 : " + format.format( new Date() );
					sendData = data.getBytes(StandardCharsets.UTF_8);
				} else {
					// echo
					data = "<<" + message;
					sendData = data.getBytes(StandardCharsets.UTF_8);
				}
				
				// 데이터 송신
				DatagramPacket sendPacket = new DatagramPacket(
						sendData,
						sendData.length,
						new InetSocketAddress(
								receivePacket.getAddress(), receivePacket.getPort()));
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
