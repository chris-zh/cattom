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
 * ������������
 * @author chris.zhang
 *
 */
public class CatServer {
	private static CatServer server = new CatServer();//��ʼ������Server
	private static Logger log = Logger.getLogger(CatServer.class);
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	private Cache cache ;
	private String webRepository;//servlet�������ַ
	/**
	 * ���Serverʵ��
	 * @return
	 */
	protected static CatServer getServer(){
		return server;
	}
	
	protected Cache getCache() {
		return cache;
	}

	/**
	 * ��ȡServlet��Դ���ַ
	 * @return
	 */
	protected String getWebRepository() {
		return webRepository;
	}
	

	/**
	 * ��ʼ��CatsServer
	 */
	private void init(){
		//step1 ��ʼ����Դ���ַ
		this.webRepository = initRepository();
		//step2 ��ʼ������ --��Ӧ���û��棬catserverӦ�ð��������������������е�����
		this.cache = Cache.getCache();
		//step3 ��ʼ��properties catserver���農̬�ļ���������Ϣ
		//step4 ��ʼ��classLoader 
	}
	
	/**
	 * ��ʼ����Դ��
	 */
	private String initRepository(){
		File webappFolder = new File(Contants.WEB_APP_ROOT);
		boolean createWebappFolder = false;
		String repository = null;
		try {
			if(!webappFolder.exists()){
				createWebappFolder = webappFolder.mkdir();
				if(createWebappFolder){
					log.info("����Web��Դ��ɹ���");
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
	 * Server��ʼ������
	 */
	private CatServer(){
//		this.cache = Cache.getCache();
//		loadRepository();//������Դ��
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
			log.info(Util.exceptionMessage(e));
		}
	}
}
