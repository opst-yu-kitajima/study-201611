package jp.co.opst.study;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class GetTest {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(40080);
		Socket socket = serverSocket.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		System.out.println("##### Start input header #####");
		StringBuilder header = new StringBuilder();
		while (true) {
			String line = in.readLine();
			if (StringUtils.isEmpty(line)) {
				break;
			}
			header.append(line).append("\r\n");
		}
		System.out.println(header.toString());
		System.out.println("##### End input header #####");

		System.out.println("##### Start ontput header and body #####");
		String outStr = IOUtils.toString(System.in);
		out.print(outStr);
		out.flush();
		System.out.println("##### End output header and body #####");

		socket.close();
		serverSocket.close();
	}
}
