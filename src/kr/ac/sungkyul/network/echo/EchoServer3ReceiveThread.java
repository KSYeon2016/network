package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer3ReceiveThread extends Thread {
	private Socket socket;

	public EchoServer3ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// 연결 성공
		InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
		int remoteHostPort = remoteAddress.getPort();
		consoleLog("연결 from " + remoteHostAddress + ":" + remoteHostPort);

		try {
			// Stream
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			while (true) {
				// 데이터 읽기
				String data = br.readLine();

				// 클라이언트가 연결을 끊은 경우
				if (data == null) {
					consoleLog("closed by client");
					break;
				}

				// 데이터가 온 경우
				consoleLog("received : " + data);
				
				// 데이터 쓰기
				pw.println(data);
			}
		} catch (SocketException e) {
			consoleLog("비정상적으로 클라이언트가 연결을 끊었습니다." + e);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 데이터 통신 소켓 닫기
			if (socket != null && socket.isClosed() == false) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void consoleLog(String message) {
		System.out.println("[echo server thread#" + getId() + "] " + message);
	}

}
