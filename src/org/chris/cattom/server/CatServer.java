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
/**
 * 容器主服务器
 * @author chris.zhang
 *
 */
public class CatServer {
	private static CatServer server = new CatServer();//初始化单例Server
	private static Logger log = Logger.getLogger(CatServer.class);
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	private Cache cache ;
	private String webRepository;//servlet容器库地址
	/**
	 * 获得Server实例
	 * @return
	 */
	protected static CatServer getServer(){
		return server;
	}
	
	protected Cache getCache() {
		return cache;
	}

	/**
	 * 获取Servlet资源库地址
	 * @return
	 */
	protected String getWebRepository() {
		return webRepository;
	}
	

	/**
	 * 初始化CatsServer
	 */
	private void init(){
		//step1 初始化资源库地址
		this.webRepository = initRepository();
		//step2 初始化缓存 --不应该用缓存，catserver应该包含服务器启动所需所有的数据
		this.cache = Cache.getCache();
		//step3 初始化properties catserver所需静态文件的配置信息
		//step4 初始化classLoader 
	}
	
	/**
	 * 初始化资源库
	 */
	private String initRepository(){
		File webappFolder = new File(Contants.WEB_APP_ROOT);
		boolean createWebappFolder = false;
		String repository = null;
		try {
			if(!webappFolder.exists()){
				createWebappFolder = webappFolder.mkdir();
				if(createWebappFolder){
					log.info("创建Web资源库成功！");
				}
			}
			repository = (new URL("file", null, webappFolder.getCanonicalPath()+ File.separator)).toString();
		} catch (MalformedURLException e) {
			log.info(Util.exceptionMessage(e));
		} catch (IOException e) {
			log.info(Util.exceptionMessage(e));
		}
		return repository;
	}
	
	/**
	 * Server初始化方法
	 */
	private CatServer(){
//		this.cache = Cache.getCache();
//		loadRepository();//加载资源库
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
