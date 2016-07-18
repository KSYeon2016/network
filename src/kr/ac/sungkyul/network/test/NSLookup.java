package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// 도메인 이름으로 ip주소를 받아옴
		while (true) {
			try {
				System.out.print(">>");
				String host = scanner.nextLine();
				
				if ("quit".equals(host)) {
					break;
				}

				// 이름 하나에 여러 ip, ip 하나에 여러 이름이 가능하므로 배열로 받는다
				InetAddress[] inetAddresses = InetAddress.getAllByName(host);

				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}

			} catch (UnknownHostException e) {
				System.out.println("unkown host");
				// e.printStackTrace();
			}
		}
		
		scanner.close();
	}

}
