package com.cn.tf;



//如需设置字体,请导入以下两个包
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;

/*public class POItest {
       
        *//**
         *
         *解决中文问题
         *//*
       
        public static String getUnicode(String toEncoded,String encoding){
          String retString="";
          if(toEncoded.equals("")||toEncoded.trim().equals(""))
          {
            return toEncoded;
          }
          try
          {
          byte[] b=toEncoded.getBytes(encoding);
          sun.io.ByteToCharConverter  convertor=sun.io.ByteToCharConverter.getConverter(encoding);
          char [] c=convertor.convertAll(b);
          for(int i=0;i< c.length;i++)
          {
            retString+=String.valueOf(c[i]);
          }
          }catch(java.io.UnsupportedEncodingException usee)
          {
           System.out.println("不支持"+encoding+"编码方式");
           usee.printStackTrace();
          }catch(sun.io.MalformedInputException mfie)
          {
           System.out.println("输入参数无效!!!");
           mfie.printStackTrace();
          }
          return retString;
        }
       
        *//**
         * 写入EXCEL
         *//*
       
        //Excel 文件要存放的位置，假定在D盘JTest目录下
        public static String UPath="D:\\JTest";
        //工作簿名称,可以取中文(就是另存为名称).
        public static String outputFile="D:/JTest/test.xls";
       
        public static void main(String argv[]){
               try{
                       //      如果UPath不存在,则创建路径
                       if(!new java.io.File(UPath).isDirectory()){
                               new java.io.File(UPath).mkdirs();
                               }
                      
                       File objFile = new File(outputFile);
                      
                       HSSFWorkbook workbook = null;
                      
                       HSSFSheet sheet = null;
                      
                       String table_name = "sheet1";
                      
                       //创建xml文件保存到D:\\JTest
                       if(!objFile.exists()){   //文件不存在
                               //创建新的Excel 工作簿
                               System.out.println("检测到文件不存在,正在创建文件...");
                               workbook = new HSSFWorkbook();
                               //在Excel工作簿中建一工作表，其名为缺省值
                               //如要新建一名为"效益指标"的工作表，其语句为：
                               //HSSFSheet sheet = workbook.createSheet("效益指标");
                               //上一句是来自网上,其实表名并不支持中文,转换后也不行!
                               //如有人已解决,请给出,谢谢!
                               sheet = workbook.createSheet(table_name);
                              
                               //新建一输出文件流
                               FileOutputStream fOut = new FileOutputStream(outputFile);
                              
                               //把相应的Excel 工作簿存盘
                               workbook.write(fOut);
                               fOut.flush();
                              
                               //操作结束，关闭文件
                               fOut.close();
                               }
                      
                       //如果文件存在,将直接插入数据.
                       //创建对Excel工作簿文件的引用
                       workbook = new HSSFWorkbook(new FileInputStream(outputFile));
                      
                       //创建对工作表的引用。
                       //本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
                       sheet = workbook.getSheet(table_name);
                      
                       //也可用getSheetAt(int index)按索引引用，
                       //在Excel文档中，第一张工作表的缺省索引是0，
                       //其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
                       //读取左上端单元
                       //行和列都是以'0'开头,比如A1为'00'
                       for(int i=0; i< 2; i++){
                               for(int j=0; j< 5; j++){
                                     
                                      //这儿,你可以测试读出一个已存在文件
                                      //HSSFRow row = sheet.getRow(i);
                                      //HSSFCell cell = row.getCell((short)j);
                                      //输出单元内容，cell.getStringCellValue()就是取所在单元的值
                                      //System.out.println("左上端单元是： " + cell.getStringCellValue());
                                     
                                      //在索引0的位置创建行（最顶端的行）
                                      //这儿,测试插入数据
                                      //插入之前必需先创建
                                      HSSFRow row = sheet.createRow((short)i);
                                      //在索引0的位置创建单元格（左上端)
                                       HSSFCell cell = row.createCell((short)j);
                                     
                                      //在创建之后可以设置单元格格式
                                      //具体用法将附于文档后面.
                                      //记得要导入包
                                     
                                      //定义单元格为字符串类型
                                      //这两名非常重要,缺少,则不能显示中文,
                                      //即使你调用编码转换方法,也不行.
                                      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                      //指定编码格式
                                     // cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                                     
                                      //在单元格中输入一些内容
                                      String text = ""+i+j+"";
                                      //调用编码转换方法,只针对中文
                                      text = getUnicode(text,"gb2312");
                                      cell.setCellValue(text);
                                     
                                      //新建一输出文件流
                                      FileOutputStream fOut = new FileOutputStream(outputFile);
                                     
                                      //把相应的Excel 工作簿存盘
                                      workbook.write(fOut);
                                      fOut.flush();
                                     
                                      // 操作结束，关闭文件
                                      fOut.close();
                                      }
                               }
                      
                       System.out.println("文件已生成!");
                       } catch(Exception e) {
                       System.out.println("已运行 xlCreate() : " + e );
                       }
               }
  
}*/
 
/*设置单元格格式
在这里，我们将只介绍一些和格式设置有关的语句，
我们假定workbook就是对一个工作簿的引用。在Java中，
第一步要做的就是创建和设置字体和单元格的格式，然后再应用这些格式：
例用开发工具追踪器,可以查看更多参数,自己慢慢去研究!
1、创建字体，设置其为红色、粗体：*/
	/*HSSFFont font = workbook.createFont();
	font.setColor(HSSFFont.COLOR_RED);
	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	//2、创建格式
	HSSFCellStyle cellStyle= workbook.createCellStyle();
	cellStyle.setFont(font);
	//3、应用格式
	HSSFCell cell = row.createCell((short) 0);
	cell.setCellStyle(cellStyle);
	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("标题");*/

