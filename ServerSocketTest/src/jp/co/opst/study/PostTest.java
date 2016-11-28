package jp.co.opst.study;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class PostTest implements Constant {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(HTTP_PORT);
		Socket socket = serverSocket.accept();
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		OutputStream output = socket.getOutputStream();

		System.out.println("##### Start input header #####");
		StringBuilder header = new StringBuilder(1024);
		int contentLength = 0;
		while (true) {
			String line = input.readLine();
			if (StringUtils.isEmpty(line)) {
				break;
			}
			if( StringUtils.startsWithIgnoreCase(line, "Content-Length")) {
				contentLength = Integer.parseInt(StringUtils.trim(StringUtils.substringAfter(line, ":")));
			}
			header.append(line).append(LINE_END);
		}
		System.out.println(header.toString());
		System.out.println("##### End input header #####");

		String body = StringUtils.EMPTY;
		if(contentLength > 0) {
			System.out.println("##### Start input body #####");
			char[] c = new char[contentLength];
            input.read(c);
            body = new String(c);
            System.out.println(body);
            System.out.println("##### End input body #####");
		}

		FileUtils.writeStringToFile(new File("./html/post/1234567890"), body);

		System.out.println("##### Start ontput header #####");
		IOUtils.write(IOUtils.toByteArray(System.in), output);
		System.out.println("##### End output header  #####");

		output.close();
		input.close();
		socket.close();
		serverSocket.close();
	}
}
