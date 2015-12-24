package org.chris.cattom.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.chris.cattom.servlet.CatRequest;
import org.chris.cattom.servlet.CatResponse;
import org.chris.cattom.util.Util;
public class CatServer {
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	private Map<Object, Object> cache ;
	private String repository;//servlet�������ַ
	public String getRepository() {
		return repository;
	}
	private void loadRepository(){
		File classPath = new File(Contants.WEB_APP_ROOT);
		try {
			String repository = (new URL("file", null, classPath.getCanonicalPath()+ File.separator)).toString();
			this.repository = repository;
		} catch (MalformedURLException e) {
			System.out.println(Util.exceptionMessage(e));
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		}
	}
	/**
	 * Servler��ʼ������
	 */
	public CatServer(){
		this.cache = new HashMap<Object, Object>();
		loadRepository();//������Դ��
	}
	
	private static CatServer server = new CatServer();
	public static CatServer getServer(){
		return server;
	}
	public Map<Object, Object> getCache(){
		return this.cache;
	}
	
	public void await(){
		ServerSocket serverSocket = null;
		InputStream input = null;
		OutputStream output = null;
		int port = 8989;
		String address = "127.0.0.1";
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName(address));
			while(!shutdown){
				Socket socket = null;
				socket = serverSocket.accept();//�õ�socket
				input = socket.getInputStream();
				output = socket.getOutputStream();
				CatRequest request = new CatRequest(input);//����request
				CatResponse response = new CatResponse(output);
				response.setRequest(request);
				if(request.getUri() == null){
					socket.close();
					continue;
				}
				if(request.getUri().startsWith("/servlet/")){
					ServletProcessor processor = new ServletProcessor();
					processor.process(request, response);
				}else{
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				}
				socket.close();
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			}
		} catch (Exception e) {
			System.out.println(Util.exceptionMessage(e));
		}
	}
}
