package com.cn.tf;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ClassEntity {
	private Integer number;
	private String adresss;
	private String phone;
	public Integer getNumber() {
		return number;
	}
	public String getAdresss() {
		return adresss;
	}
	public String getPhone() {
		return phone;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setAdresss(String adresss) {
		this.adresss = adresss;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}



}


