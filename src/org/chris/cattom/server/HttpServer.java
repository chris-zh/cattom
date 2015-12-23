package org.chris.cattom.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.chris.cattom.util.Util;
public class HttpServer {
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	private Map<Object, Object> cache ;
	public HttpServer(){
		this.cache = new HashMap<Object, Object>();
	}
	
	private static HttpServer server = new HttpServer();
	public static HttpServer getServer(){
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
				socket = serverSocket.accept();//得到socket
				System.out.println(socket.isClosed());
				input = socket.getInputStream();
				output = socket.getOutputStream();
				HttpCatRequest request = new HttpCatRequest(input);//生成request
				request.parse();//解析请求路径
				HttpCatResponse response = new HttpCatResponse(output);
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
