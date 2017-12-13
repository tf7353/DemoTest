package com.cn.tf.socket.exmple3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Exception in thread "main" java.net.BindException: Address already in use: JVM_Bind
 * Log4j
 * 
 * �ͻ��ˣ�����¼������
 * ��������������д���ı��ļ�
 */
public class ServerDemo {
	public static void main(String[] args) throws IOException {
		// ����������Socket����
		ServerSocket ss = new ServerSocket(12345);

		// �����ͻ��˶���
		Socket s = ss.accept();

		// ��ȡ������
		BufferedReader br = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		// ��ȡ�����
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));

		String line = null;
		while ((line = br.readLine()) != null) {
			bw.write(line.toUpperCase());
			bw.newLine();
			bw.flush();
		}

		// �ͷ���Դ
		s.close();
		ss.close();
	}
}
