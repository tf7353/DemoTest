package com.cn.tf.socket.exmple1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * ������������ݲ���?
 * A:����������Socket����
 * B:����ͻ�������?
 * C:��ȡ��ݲ����?
 * D:�ͷ���Դ
 */
public class ServerDemo {
	public static void main(String[] args) throws IOException {
		// ����������Socket����
		// ServerSocket(int port)
		ServerSocket ss = new ServerSocket(12306);

		// ����ͻ�������?
		// Socket accept()
		Socket s = ss.accept(); // �˷��������Ӵ���֮ǰһֱ����

		// ��ȡ��ݲ����?
		InputStream is = s.getInputStream();
		byte[] bys = new byte[1024];
		int len = is.read(bys); // ����
		String client = new String(bys, 0, len);
		System.out.println("server:" + client);

		// �ͷ���Դ
		// is.close();
		s.close();
		// ss.close();
	}
}
