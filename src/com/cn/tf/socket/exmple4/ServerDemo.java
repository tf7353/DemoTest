package com.cn.tf.socket.exmple4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 
 * �ͻ��ˣ�����¼������
 * ��������������д���ı��ļ�
 * 
 * ������ҵ��
 * �ͻ��ˣ��ı��ļ�
 * ���������ı��ļ�
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
		BufferedWriter bw = new BufferedWriter(new FileWriter("bw.txt"));

		String line = null;
		while ((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}

		// ���ø�һ������
		BufferedWriter bwServer = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));
		bwServer.write("�����Ѿ��ɹ�д���ļ�");
		bwServer.newLine();
		bwServer.flush();

		// �ͷ���Դ
		s.close();
		ss.close();
	}
}
