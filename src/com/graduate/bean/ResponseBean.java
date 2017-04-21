package com.graduate.bean;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * HTTP请求结果解析
 * @author czy
 *
 */
public class ResponseBean {
	private Header[] headers;
	private String cookie;
	private String bodyAsString;
	private ByteArrayOutputStream byteArrayOutputStream;
	
	public Header[] getHeaders() {
		return headers;
	}
	public void setHeaders(Header[] headers) {
		this.headers = headers;
		if(headers!=null){
			StringBuffer cookie = new StringBuffer("");
			if(headers!=null){
				for (Header header2 : headers) {
					if(header2.getName().equals("Set-Cookie")){
						cookie.append(header2.getValue());
						cookie.append(";");
					}
				}
			}
			if(cookie.length()>0) cookie=cookie.deleteCharAt(cookie.length()-1);
			this.cookie=cookie.toString();
		}
	}
	public String getCookie() {
		return cookie;
	}
	public String getBodyAsString() {
		if(bodyAsString == null&&byteArrayOutputStream != null){
			try {
				bodyAsString = byteArrayOutputStream.toString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return bodyAsString;
	}
	public void setBodyAsString(String bodyAsString) {
		this.bodyAsString = bodyAsString;
	}
	public ByteArrayOutputStream getByteArrayOutputStream() {
		return byteArrayOutputStream;
	}
	public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
		this.byteArrayOutputStream = byteArrayOutputStream;
	}
}
