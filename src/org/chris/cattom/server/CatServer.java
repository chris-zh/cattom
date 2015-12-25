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
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.chris.cattom.servlet.CatRequest;
import org.chris.cattom.servlet.CatResponse;
import org.chris.cattom.util.Util;
public class CatServer {
	private static Logger log = Logger.getLogger(CatServer.class);
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	private Map<Object, Object> cache ;
	private String webRepository;//servlet容器库地址
	private URLClassLoader classLoader;
	/**
	 * 获取Servlet资源库地址
	 * @return
	 */
	protected String getServletRepository() {
		return webRepository;
	}
	
	/**
	 * 获得类加载器
	 * 
	 * @return
	 */
	private void getClassLoader() {
		URLClassLoader loader = null;
		URLStreamHandler streamHandler = null;
		URL[] urls = new URL[1];
		String repository = ServerAccessor.getServletRepository();
		try {
			urls[0] = new URL(null, repository, streamHandler);
		} catch (MalformedURLException e) {
			System.out.println(Util.exceptionMessage(e));
		}
		this.classLoader = new URLClassLoader(urls);
	}

	/**
	 * 初始化CatsServer
	 */
	private void init(){
		//step1 初始化资源库地址
		loadRepository();
		
		
		//step2 初始化缓存 --不应该用缓存，catserver应该包含服务器启动所需所有的数据
		//step3 初始化properties catserver所需静态文件的配置信息
		//step4 初始化classLoader 
	}
	
	/**
	 * 初始化资源库地址
	 */
	private void loadRepository(){
		File webappFolder = new File(Contants.WEB_APP_ROOT);
		boolean createWebappFolder = false;
		try {
			if(!webappFolder.exists()){
				createWebappFolder = webappFolder.mkdir();
				if(createWebappFolder){
					log.info("创建Web资源库成功！");
				}
			}
			String repository = (new URL("file", null, webappFolder.getCanonicalPath()+ File.separator)).toString();
			this.webRepository = repository;
		} catch (MalformedURLException e) {
			log.info(Util.exceptionMessage(e));
		} catch (IOException e) {
			log.info(Util.exceptionMessage(e));
		}
	}
	/**
	 * Servler初始化方法
	 */
	public CatServer(){
		this.cache = new HashMap<Object, Object>();
		loadRepository();//加载资源库
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
				socket = serverSocket.accept();//得到socket
				input = socket.getInputStream();
				output = socket.getOutputStream();
				CatRequest request = new CatRequest(input);//生成request
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
			log.info(Util.exceptionMessage(e));
		}
	}
}
