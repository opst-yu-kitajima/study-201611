package jp.co.opst.study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class GetTest implements Constant {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(HTTP_PORT);
		Socket socket = serverSocket.accept();
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter output = new BufferedWriter(new PrintWriter(socket.getOutputStream()));

		System.out.println("##### Start input header #####");
		StringBuilder header = new StringBuilder(1024);
		while (true) {
			String line = input.readLine();
			if (StringUtils.isEmpty(line)) {
				break;
			}
			header.append(line).append(LINE_END);
		}
		System.out.println(header.toString());
		System.out.println("##### End input header #####");

		System.out.println("##### Start ontput header and body #####");
		output.write(IOUtils.toString(System.in));
		output.flush();
		System.out.println("##### End output header and body #####");

		socket.close();
		serverSocket.close();
	}
}
