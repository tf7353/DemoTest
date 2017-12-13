package com.cn.tf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
	public String insert(){
		List<Object>  aaa = new ArrayList<Object>();
		List<String>  bbb = new ArrayList<String>();
		bbb.add("sdfdf");
		aaa.add("张三");
		aaa.add("lissi");
		aaa.add(bbb);
		
		
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("aaa", "张三");
		map1.put("bbb", "lsie");
		map1.put("list", bbb);
		map1.put("aaa", "张三");
		map1.put("aaa", "张三");
		map1.put("aaa", "张三");
		
		map1.get("aaa");
		
		map1.put("renuyanlist", aaa);
		aaa.add(map1);
		
		
		
		return "保存成功";
	}
}


