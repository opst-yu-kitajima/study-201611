package jp.co.opst.study;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class GzipTest implements Constant {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(HTTP_PORT);
		Socket socket = serverSocket.accept();
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		OutputStream output = socket.getOutputStream();

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

		System.out.println("##### Start gzip load #####");
		byte[] html = FileUtils.readFileToByteArray(new File("./html/post.html"));
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut = new GZIPOutputStream(bout);
		IOUtils.write(html, gzipOut);
		gzipOut.finish();
		byte[] gzipHtml = bout.toByteArray();
		System.out.println("Gzip byte size: " + gzipHtml.length);
		System.out.println("##### End zip load #####");

		System.out.println("##### Start ontput header #####");
		IOUtils.write(IOUtils.toByteArray(System.in), output);
		System.out.println("##### End output header #####");

		IOUtils.write("\r\n".getBytes(), output);

		System.out.println("##### Start ontput body #####");
		System.out.print(new String(gzipHtml, "UTF-8"));
		IOUtils.write(gzipHtml, output);
		System.out.println("##### End output body #####");

		output.close();
		input.close();
		socket.close();
		serverSocket.close();
	}
}
