package com.cn.tf;


import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test {

	@org.junit.Test
	public void test1(){
		String banktradedate = "2017-12-19 17:40:00.0";
		//logger.info("banktradedate--"+banktradedate);
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date banktrade = null;

		try {
//			Gooo
			banktrade = sim.parse(banktradedate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		sim = new SimpleDateFormat("yyyyMMddHHmmss");
		String createTime = sim.format(banktrade);
		System.out.println(createTime);
	}
	@org.junit.Test
	public void test2(){

	String s ="AbleSky.NET/5.98_41/Android/8.1.0/MI+3W/ableskyapp";
	String version=s.split("/")[1].split("_")[0];
	String result="";
	if(Double.parseDouble(version)>Double.parseDouble("5.97")){
		result="1";
	}else if(Double.parseDouble(version)==Double.parseDouble("5.97")){
		result="2";
	}else{
		result="3";
	}

		String sss ="S 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Mobile/15A372/ableskyapp/nengliketang AppVersion/8.2 bh/44";
			String[] m =sss.split("/");
			String t = m[m.length-1];
		System.out.println(t);
	}
	@org.junit.Test
	public void test3(){
		for(int i=0 ;i<300;i++){
			List<String> list = new ArrayList<>();
			list.add("经过核查商户交易正常，排除风险。");
			list.add("商户交易正常，排除风险");
			list.add("经查询商户交易正常，可以排除风险。");
			int [] arr = {1,2,3};
			//产生0-(arr.length-1)的整数值,也是数组的索引
			int index=(int)(Math.random()*arr.length);
			int rand = arr[index];
			System.out.println(list.get(rand-1));
		}

	}
	@org.junit.Test
	public void test4(){
		//JSONObject jsonObject = new JSONObject();
		Map<String,Object> head = new HashMap<>();
		Map<String,Object> body = new HashMap<>();
		Map<String,Object> domain = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		head.put("head1","head1");
		head.put("head2","head2");
		head.put("head3","head3");
		body.put("body1","body1");
		body.put("body2","body2");
		body.put("body3","body3");
		domain.put("head",head);
		domain.put("body",body);

//		JSONObject jsonObject = new JSONObject(domain);

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(new Date());
		System.out.println(time);


	}
	@org.junit.Test
	public void test5(){
		String s="1.0.0|1|1|reqReser1|reqReser2|1|1||\n" +
				"respCode|respMsg|payerAcctBal|acctDate|txnType|txnNo|settleMethod|merId|merName|payeeBankNo|payeeAcctNo|payeeAcctName|currencyCode|txnAmt|remark|reqReserved\n" +
				"00|成功|100000|20180206|02|ORDER001111111121|00|012310048995503|测试商户|105121061148|621661280000447287|某某餐饮公司|156|1|测试|myReqReserved";
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes(Charset.forName("utf8"))), Charset.forName("UTF-8")));
		String line;
		StringBuffer strbuf=new StringBuffer();
		try {
			while ( (line = br.readLine()) != null ) {
                if(!line.trim().equals("")){
                    //line=line;//每行可以做加工
                    strbuf.append(line+"\n");
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(strbuf.toString());

	}
	@org.junit.Test
	public void test6(){
		Map<String, Integer> positionMap= new HashMap<>();
		String flilerow = "|payerAcctBal|respCode|txnType|respMsg|settleMethod|merId|merName|payeeBankNo|payeeAcctNo|payeeAcctName|currencyCode|txnAmt|remark|reqReserved";
		String[] rows = flilerow.split("\\|");
		List list = Arrays.asList(rows);
		if(list.contains("respCode")){
			positionMap.put("respCode",list.indexOf("respCode"));
		}
		if(list.contains("respMsg")){
			positionMap.put("respMsg",list.indexOf("respMsg"));
		}
		if(list.contains("txnNo")){
			positionMap.put("txnNo",list.indexOf("txnNo"));
		}
		if(list.contains("payeeAcctNo")){
			positionMap.put("payeeAcctNo",list.indexOf("payeeAcctNo"));
		}
		if(list.contains("payeeAcctName")){
			positionMap.put("payeeAcctName",list.indexOf("payeeAcctName"));
		}
		System.out.println(positionMap);
	}
	@org.junit.Test
	public void test7(){
		try {
			Map<String, Object> valueMap = new HashMap<>();
			String result = "{\"aaa\":\"111\",\"bbb\":null,\"ccc\":\"333\",\"ddd\":\"444\",\"eee\":\"555\"}";
		/*	if (StringUtils.isNotBlank(result)) {
				JSONObject jsonobject = JSONObject.fromObject(result);
				valueMap = (Map<String, Object>) JSONObject
						.toBean(jsonobject, Map.class);
				// map = parseQString(result);
				System.out.println(valueMap);
			}*/
			JSONObject jsonObject = JSONObject.fromObject(result);
			Iterator keyIter = jsonObject.keys();
			String key;
			String value;
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				if(jsonObject.get(key) instanceof JSONNull){
					value = null;
				}else{
					value = (String)jsonObject.get(key);
				}
				valueMap.put(key, value);
			}

//			data.put("aa","11");
//			data.put("bb",null);
			System.out.println(valueMap.get("ffff"));
			TreeMap<String, Object> tree = new TreeMap<String, Object>();
			Iterator<Map.Entry<String, Object>> it = valueMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> en = it.next();
				if ("signature".equals(en.getKey().trim())) {
					continue;
				}
				tree.put(en.getKey(), en.getValue());
			}
			it = tree.entrySet().iterator();
			StringBuffer sf = new StringBuffer();
			while (it.hasNext()) {
				Map.Entry<String, Object> en = it.next();
				sf.append(en.getKey() + "=" + en.getValue()
						+ "&");
			}
			System.out.println(sf.substring(0, sf.length() - 1));
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
	@org.junit.Test
	public void test8(){
		try {
			Map<String, String> map = null;
			String result = "{\"aaa\":\"111\",\"bbb\":null}";
			if (StringUtils.isNotBlank(result)) {
				JSONObject jsonobject = JSONObject.fromObject(result);
				map = (Map<String, String>) JSONObject
						.toBean(jsonobject, Map.class);
				// map = parseQString(result);
				System.out.println(map);
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
	@org.junit.Test
	public void test9(){
		final BASE64Encoder encoder = new BASE64Encoder();
		final BASE64Decoder decoder = new BASE64Decoder();
		String s="WyB7CiAgInR5cGUiIDogIkREMDEiLAogICJzcG5zcklkIiA6ICIwMDAxMDAwMCIsCiAgIm9mZnN0QW10IiA6ICIxODMiLAogICJpZCIgOiAiMTIwMTIwMTcwNDE3MDAwMiIsCiAgImRlc2MiIDogIuS6jOe7tOeggea1i+ivlemaj+acuueri+WHjyIsCiAgImFkZG5JbmZvIiA6ICIiCn0gXQ==";
		try {
			System.out.println(new String(decoder.decodeBuffer(s), "UTF-8"));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@org.junit.Test
	public void test10(){
		BigDecimal b = new BigDecimal("7.66");
		System.out.println(b.longValue()+"|"+b.doubleValue());
		Object o = new Object();



	}
	public static String getCustomerInfo(Map<String,String> customerInfoMap) {

		if(customerInfoMap.isEmpty())
			return "{}";
		StringBuffer sf = new StringBuffer("{");
		for(Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = customerInfoMap.get(key);
			sf.append(key).append("=").append(value);
			if(it.hasNext())
				sf.append("&");
		}
		String customerInfo = sf.append("}").toString();
		return customerInfo;
	}
	private List<String> getBetweenDates(String startTime, String endTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date begin = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			List<String> result = new ArrayList<String>();
			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(begin);
			while(begin.getTime()<=end.getTime()){
				result.add( sdf.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
				begin = tempStart.getTime();
			}
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}
}


