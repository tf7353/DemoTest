package com.cn.tf.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket client;  
    PrintWriter pw;  
    public  Client() throws UnknownHostException, IOException {
        client=new Socket("localhost",7777);  
        pw=new PrintWriter(client.getOutputStream());  
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
        pw.write(br.readLine());  
        pw.close();  
        br.close();  
    }  
    public static void main(String[] args) {  
        try {
			new Client();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("start...");
    }  
}


