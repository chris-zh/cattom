package org.chris.cattom.http;

import org.chris.cattom.server.Contants;
/**
 * HTTP返回报文头
 * @author chris.zhang
 *
 */
public class HTTPResponseHead {
	private String headContent;//http response报文
	private String protocol = Contants.HTTP_PROTOCOL_VERSION;
	private int statusCode;
	private String contentType;
	private long contentLength;
	/**
	 * 构造方法
	 * @param statusCode 状态码
	 * @param contentType 内容类型
	 * @param contentLength 内容长度
	 */
	public HTTPResponseHead(int statusCode, String contentType, long contentLength){
		this.statusCode = statusCode;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.structure();
	}
	/**
	 * 组成报文头
	 */
	private void structure(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.protocol);
		sb.append(Contants.SPACE);
		sb.append(statusCode);
		sb.append(Contants.SPACE);
		sb.append(HTTPStatusCode.mapDescription(statusCode));
		sb.append(Contants.HTTP_NEWLINE);
		sb.append("Content-Type:"+contentType);
		sb.append(Contants.HTTP_NEWLINE);
		sb.append("Content-Length:"+contentLength);
		sb.append(Contants.HTTP_NEWLINE);
		sb.append(Contants.HTTP_NEWLINE);
		headContent = sb.toString();
	}
	/**
	 * 获取HTTP返回报文内容
	 * @return
	 */
	public String getContext(){
		return this.headContent;
	}
	/**
	 * 获取HTTP返回报文字节数组
	 * @return
	 */
	public byte[] getContextBytes(){
		return headContent == null?null:headContent.getBytes();
	}
	public static void main(String[] args) {
		HTTPResponseHead responseHead = new HTTPResponseHead(200, "text/html", 1024);
		System.out.println(responseHead.getContext());
	}
}
