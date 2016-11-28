package jp.co.opst.study;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InitTest implements Constant {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(HTTP_PORT);
		Socket socket = serverSocket.accept();

		// クライアント（ブラウザ）からのリクエスト
		InputStream input = socket.getInputStream();

		// サーバからのレスポンス
		OutputStream output = socket.getOutputStream();

		System.out.println("##### Start input header #####");
		// IOUtils.readLines(input); ってやってしまうと、入力待ちに陥ってしまうので駄目です。
		// 空行がくるまでがヘッダなので、1行ずつ情報を確認してみる
		System.out.println("##### End input header #####");

		System.out.println("##### Start ontput header #####");
		System.out.println("##### End output header #####");

		System.out.println("##### Start ontput body #####");
		System.out.println("##### End output body #####");

		output.close();
		input.close();
		socket.close();
		serverSocket.close();
	}
}
