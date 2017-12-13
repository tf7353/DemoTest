package com.cn.tf.socket.exmple5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {
	public static void main(String[] args) throws IOException {
		// ����������Socket����
		ServerSocket ss = new ServerSocket(22222);

		// �����ͻ�������
		Socket s = ss.accept();

		// ��ȡ������
		BufferedReader br = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		// ��ȡ�����
		BufferedWriter bw = new BufferedWriter(new FileWriter("bw.txt"));

		String line = null;
		while ((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}

		// ��ȡ�����
		BufferedWriter bwServer = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));
		bwServer.write("�ļ��ϴ��ɹ�");
		bwServer.newLine();
		bwServer.flush();

		bw.close();
		// br.close();
		s.close();
		ss.close();

	}
}
