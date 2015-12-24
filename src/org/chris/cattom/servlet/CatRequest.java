package org.chris.cattom.servlet;

import java.io.IOException;
import java.io.InputStream;

import org.chris.cattom.util.Util;
/**
 * Request
 * @author chris
 *
 */
public class CatRequest{
	private String uri;
	private InputStream input;
	/**
	 * 构造方法
	 * @param input
	 */
	public CatRequest(InputStream input){
		this.input = input;
		parse();
	}
	public String getUri() {
		return uri;
	}
	
	public void parse(){
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char)buffer[j]);
		}
		uri = parseUri(request.toString());
	}
	/**
	 * 解析Uri
	 * @param requestString
	 * @return
	 */
	public String parseUri(String requestString){
		int index1, index2;
		index1 = requestString.indexOf(" ");
		if(index1!= -1){
			index2 = requestString.indexOf(" ", index1 + 1);
			if(index2 > index1){
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}
}
