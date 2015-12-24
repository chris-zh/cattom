package org.chris.cattom.http;

import org.chris.cattom.server.Contants;
/**
 * HTTP���ر���ͷ
 * @author chris.zhang
 *
 */
public class HTTPResponseHead {
	private String headContent;//http response����
	private String protocol = Contants.HTTP_PROTOCOL_VERSION;
	private int statusCode;
	private String contentType;
	private long contentLength;
	/**
	 * ���췽��
	 * @param statusCode ״̬��
	 * @param contentType ��������
	 * @param contentLength ���ݳ���
	 */
	public HTTPResponseHead(int statusCode, String contentType, long contentLength){
		this.statusCode = statusCode;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.structure();
	}
	/**
	 * ��ɱ���ͷ
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
	 * ��ȡHTTP���ر�������
	 * @return
	 */
	public String getContext(){
		return this.headContent;
	}
	/**
	 * ��ȡHTTP���ر����ֽ�����
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
