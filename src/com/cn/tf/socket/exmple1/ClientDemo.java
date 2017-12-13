package com.cn.tf.socket.exmple1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * TCP�ͻ��˲������裺
 * A:�����ͻ���Socket����
 * B:��������ͨ��
 * C:��������
 * D:�ͷ���Դ
 */
public class ClientDemo {
	public static void main(String[] args) throws IOException {
		// �����ͻ���Socket����
		// Socket(String host, int port)
		// ��������ͨ��
		Socket s = new Socket("192.168.3.100", 12306);

		// ��������
		// OutputStream getOutputStream()
		OutputStream os = s.getOutputStream();
		os.write("tcp,������".getBytes());

		// �ͷ���Դ
		// os.close();
		s.close();
	}
}
