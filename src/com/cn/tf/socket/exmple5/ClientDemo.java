package com.cn.tf.socket.exmple5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientDemo {
	public static void main(String[] args) throws IOException {
		// �����ͻ��˶���
		Socket s = new Socket("192.168.3.100", 22222);

		// ��ȡ������
		BufferedReader br = new BufferedReader(new FileReader("Phone.java"));
		// ��ȡ�����
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));

		String line = null;
		while ((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}

		// ֪ͨ��������Ҫ�����ˣ����Ѿ�û��������
		s.shutdownOutput();

		// ��ȡ������
		BufferedReader brServer = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		String server = brServer.readLine();
		System.out.println("client:" + server);

		// �ͷ���Դ
		br.close();
		s.close();
	}
}
