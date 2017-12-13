package com.cn.tf.ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;
public class FtpDemo {
	/** 
	* Description: 向FTP服务器上传文件 
	* @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建 
	* @param url FTP服务器hostname 
	* @param port FTP服务器端口 
	* @param username FTP登录账号 
	* @param password FTP登录密码 
	* @param path FTP服务器保存目录 
	* @param filename 上传到FTP服务器上的文件名 
	* @param input 输入流 
	* @return 成功返回true，否则返回false 
	*/
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) { 
	boolean success = false; 
	FTPClient ftp = new FTPClient(); 
	try { 
	int reply; 
	ftp.connect(url, port);//连接FTP服务器 
	//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	boolean ccccc =ftp.login(username, password);//登录 
    ftp.setControlEncoding("GBK"); 
    ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  
    ftp.enterLocalPassiveMode();
	reply = ftp.getReplyCode(); 
	if (!FTPReply.isPositiveCompletion(reply)) { 
	ftp.disconnect(); 
	return success; 
	} 
	boolean xxx =ftp.changeWorkingDirectory(path); 
	System.out.println("-----------------------------------");
	System.out.println("切换工作目录: "+xxx);
	boolean ccc =ftp.storeFile(filename, input); 
	System.out.println("-----------------------------------");
	System.out.println("是否上传成功: "+ccc);
	input.close(); 
	ftp.logout(); 
	success = true; 
	} catch (IOException e) { 
	e.printStackTrace(); 
	} finally { 
	if (ftp.isConnected()) { 
	try { 
	ftp.disconnect(); 
	} catch (IOException ioe) { 
	} 
	} 
	} 
	return success; 
	}
	   
	  /**
	   * 下载文件
	   * @param hostname FTP服务器地址
	   * @param port  FTP服务器端口号
	   * @param username  FTP登录帐号
	   * @param password  FTP登录密码
	   * @param pathname  FTP服务器文件目录
	   * @param filename  文件名称
	   * @param localpath 下载后的文件路径
	   * @return
	   */
	  public static boolean downloadFile(String hostname, int port, String username, String password, String pathname, String filename, String localpath){
	    boolean flag = false;
	    FTPClient ftpClient = new FTPClient();
	    try {
	      //连接FTP服务器
	      ftpClient.connect(hostname, port);
	      //登录FTP服务器
	      ftpClient.login(username, password);
	      //验证FTP服务器是否登录成功
	      int replyCode = ftpClient.getReplyCode();
	      if(!FTPReply.isPositiveCompletion(replyCode)){
	        return flag;
	      }
	      //切换FTP目录
	      ftpClient.changeWorkingDirectory(pathname);
	      FTPFile[] ftpFiles = ftpClient.listFiles();
	      for(FTPFile file : ftpFiles){
	        if(filename.equalsIgnoreCase(file.getName())){
	          File localFile = new File(localpath + "/" + file.getName());
	          OutputStream os = new FileOutputStream(localFile);
	          ftpClient.retrieveFile(file.getName(), os);
	          os.close();
	        }
	      }
	      ftpClient.logout();
	      flag = true;
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally{
	      if(ftpClient.isConnected()){
	        try {
	          ftpClient.logout();
	        } catch (IOException e) {
	          
	        }
	      }
	    }
	    return flag;
	  }
	  /**
	   * 测试上传
	   * 2017年5月4日下午3:22:05 f
	   */
	@Test 
	public void testUpLoadFromDisk(){ 
	try { 
	File file =new File("D:\\SoftWareBag\\vmware11_centOs\\CentOS-6.4-i386-bin-DVD1.iso");
	FileInputStream in=new FileInputStream(file); 
	boolean flag = uploadFile("192.168.126.241", 21, "admin", "admin", "files", file.getName(), in); 
	System.out.println(flag); 
	} catch (FileNotFoundException e) { 
	e.printStackTrace(); 
	} 
	}

}
