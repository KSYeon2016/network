package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		// 호스트 이름, 도메인 이름, IpAddress
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			String hostName = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			// hostAddress를 바이트 단위로
			byte[] addresses = inetAddress.getAddress();
			
			System.out.println("HostName : " + hostName);
			System.out.println("HostAddress : " + hostAddress);
			
			for(int i = 0; i < addresses.length; i++){
				System.out.print(addresses[i] & 0x000000ff);
				if(i < addresses.length - 1){
					System.out.print(".");
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
