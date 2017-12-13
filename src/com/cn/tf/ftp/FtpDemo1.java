package com.cn.tf.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

public class FtpDemo1 {
	 private  FTPClient ftp;      
	    /**  
	     *   
	     * @param path 上传到ftp服务器哪个路径下     
	     * @param addr 地址  
	     * @param port 端口号  
	     * @param username 用户名  
	     * @param password 密码  
	     * @return  
	     * @throws Exception  
	     */    
	    private  boolean connect(String path,String addr,int port,String username,String password) throws Exception {      
	        boolean result = false;      
	        ftp = new FTPClient();      
	        int reply;      
	        ftp.connect(addr,port);      
	        ftp.login(username,password);   
	        ftp.setControlEncoding("GBK"); 
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
	        reply = ftp.getReplyCode();      
	        if (!FTPReply.isPositiveCompletion(reply)) {      
	            ftp.disconnect();      
	            return result;      
	        }      
	       boolean boo = ftp.changeWorkingDirectory(path);      
	       System.out.println("-----------------------------------");
       	   System.out.println("切换工作目录是否成功: "+boo);
	        result = true;      
	        return result;      
	    }      
	    /**  
	     *   
	     * @param file 上传的文件或文件夹  
	     * @throws Exception  
	     */    
	    private void upload(File file) throws Exception{      
	        if(file.isDirectory()){           
	            ftp.makeDirectory(file.getName());                
	            boolean bbb =ftp.changeWorkingDirectory(file.getName());   
	        	
	            String[] files = file.list();             
	            for (int i = 0; i < files.length; i++) {      
	                File file1 = new File(file.getPath()+"\\"+files[i] );      
	                if(file1.isDirectory()){      
	                    upload(file1);      
	                    ftp.changeToParentDirectory();      
	                }else{                    
	                    File file2 = new File(file.getPath()+"\\"+files[i]);      
	                    FileInputStream input = new FileInputStream(file2);      
	                    ftp.storeFile(file2.getName(), input);      
	                    input.close();                            
	                }                 
	            }      
	        }else{      
	            File file2 = new File(file.getPath());      
	            FileInputStream input = new FileInputStream(file2);  
	            //使用原文件名
	           //  boolean ccc=ftp.storeFile(file2.getName(), input);  
	            //不使用源文件名
	           boolean ccc= ftp.storeFile("aaa.txt", input);  
	            System.out.println("-----------------------------------");
	        	System.out.println("是否上传成功: "+ccc);
	            input.close();        
	        }      
	    } 
	    
	    /**
	     * 下载文件
	     * @param hostname FTP服务器地址
	     * @param port  FTP服务器端口号
	     * @param username  FTP登录帐号
	     * @param password  FTP登录密码
	     * @param pathname  FTP服务器文件目录
	     * @param filename  ftp文件名称
	     * @param localpath 下载后的文件路径
	     * @return
	     */
	    public  boolean downloadFile(String hostname, int port, String username, String password, String pathname, String filename, String localpath){
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
	     * 删除文件
	     * @param hostname FTP服务器地址
	     * @param port  FTP服务器端口号
	     * @param username  FTP登录帐号
	     * @param password  FTP登录密码
	     * @param pathname  FTP服务器保存目录
	     * @param filename  ftp中要删除的文件名称
	     * @return
	     */
	    public  boolean deleteFile(String hostname, int port, String username, String password, String pathname, String filename){
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
	        ftpClient.dele(filename);
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
	     * 2017年5月4日下午3:23:30 f
	     */
	    @Test
	    public void testupload() throws Exception{
	    	  FtpDemo1 f = new FtpDemo1(); 
	    	  //第一个参数为上传到ftp的目录
		      boolean bbbb = f.connect("images", "localhost", 21, "tianfeng", "123456");  
		      //new File定义 需要上传的文件夹或者文件路径
		      File file = new File("D:\\aaaa\\aaa.txt");    
		      f.upload(file);  
		      System.out.println("zhi xing wan bi");
	    }
	    /**
	     * 测试下载
	     * 2017年5月4日下午3:24:03 f
	     */
	    @Test
	    public void testdownload(){
	    FtpDemo1 f = new FtpDemo1();  
	    boolean dddd=	 f.downloadFile("localhost", 21, "tianfeng", "123456", "images", "aaa.txt", "D:\\BBBB");
	    System.out.println("-----------------------------------");
    	System.out.println("是否下载成功: "+dddd);
	    }
	    /**
	     * 测试删除
	     * 2017年5月4日下午3:41:51 f
	     */
	    @Test
	    public void testDelete(){
	    	 FtpDemo1 f = new FtpDemo1();  
	    	boolean eeee= f.deleteFile("localhost", 21, "tianfeng", "123456", "images", "ccc.txt");
	    	 System.out.println("-----------------------------------");
	     	System.out.println("是否删除成功: "+eeee);
	    	}
}


