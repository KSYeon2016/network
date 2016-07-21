package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TimeClient {
	private final static int PORT = 5000;
	private final static String IP = "220.67.115.225";
	private final static int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scanner = null;
		
		try {
			// 키보드 연결
			scanner = new Scanner(System.in);
			
			// 소켓 생성
			socket = new DatagramSocket();
			
			while(true){
				// 값 입력 받기
				System.out.print(">>");
				String message = scanner.nextLine();
				
				if("quit".equals(message)){
					break;
				}
				
				// 데이터 송신
				byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
				DatagramPacket sendPacket = new DatagramPacket(
						sendData, 
						sendData.length, 
						new InetSocketAddress(IP, PORT));
				socket.send(sendPacket);
				
				// 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(
						new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				String data = new String(
						receivePacket.getData(),
						0,
						receivePacket.getLength(),
						StandardCharsets.UTF_8);
				System.out.println(data);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			if(scanner != null){
				scanner.close();
			}
			if(socket != null && socket.isClosed() == false){
				socket.close();
			}
		}
	}

}
