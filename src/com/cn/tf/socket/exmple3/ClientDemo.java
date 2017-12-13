package com.cn.tf.socket.exmple3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientDemo {
	public static void main(String[] args) throws IOException {
		// �����ͻ���Socket����
		Socket s = new Socket("192.168.3.100", 12345);

		// ��װ����¼��
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// ��ȡ�����
		// OutputStream os = s.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				s.getOutputStream()));

		String line = null;
		while ((line = br.readLine()) != null) {
			if ("over".equals(line)) {
				break;
			}

			// ������д��ȥ
			bw.write(line);
			bw.newLine();
			bw.flush();

			// ��ȡ�������ķ���
			BufferedReader brServer = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String server = brServer.readLine();
			System.out.println("server:" + server);
		}

		// �ͷ���Դ
		s.close();
	}
}
