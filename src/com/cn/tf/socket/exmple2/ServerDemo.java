package com.cn.tf.socket.exmple2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {
	public static void main(String[] args) throws IOException {
		// ����������Socket���󣬲�ָ���˿�
		ServerSocket ss = new ServerSocket(9527);

		// �����ͻ��˵�����
		Socket s = ss.accept(); // ����

		// ��ȡ������
		InputStream is = s.getInputStream();
		byte[] bys = new byte[1024];
		int len = is.read(bys);//����
		String client = new String(bys, 0, len);
		System.out.println("server:" + client);

		// ��ȡ�����
		OutputStream os = s.getOutputStream();
		os.write("nenenen".getBytes());

		// �ͷ���Դ
		s.close();
		ss.close();
	}
}
