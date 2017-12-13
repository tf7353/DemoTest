package com.cn.tf.socket.exmple2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientDemo {
	public static void main(String[] args) throws IOException {
		// �����ͻ���Socket����
		Socket s = new Socket("192.168.126.240", 9527);

		// ��ȡ�����?
		OutputStream os = s.getOutputStream();
		os.write("hahahah".getBytes());

		// ��ȡ������
		InputStream is = s.getInputStream();
		byte[] bys = new byte[1024];
		int len = is.read(bys);//����
		String server = new String(bys, 0, len);
		System.out.println("client:" + server);

		// �ͷ���Դ
		s.close();
	}
}
