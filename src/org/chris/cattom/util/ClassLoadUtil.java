package org.chris.cattom.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import org.chris.cattom.server.CatServer;
import org.chris.cattom.servlet.Servlet;

public class ClassLoadUtil {
	/**
	 * 初始化Servlet并调用init()方法
	 * @param servletName
	 */
	public static Servlet loadAndInitServlet(String servletName) {
		URLClassLoader loader = getClassLoader();
		Servlet newServlet = null;
		try {
			Class<?> clazz = loader.loadClass(servletName);
			newServlet = (Servlet) clazz.newInstance();
			CatServer.getServer().getCache().put(servletName, newServlet);
			newServlet.init();
		} catch (InstantiationException e) {
			System.out.println(Util.exceptionMessage(e));
		} catch (IllegalAccessException e) {
			System.out.println(Util.exceptionMessage(e));
		} catch (ClassNotFoundException e) {
			System.out.println(Util.exceptionMessage(e));
		}
		return newServlet;
	}
	/**
	 * 获得类加载器
	 * 
	 * @return
	 */
	private static URLClassLoader getClassLoader() {
		URLClassLoader loader = null;
		URLStreamHandler streamHandler = null;
		URL[] urls = new URL[1];
		String repository = CatServer.getServer().getRepository();
		try {
			urls[0] = new URL(null, repository, streamHandler);
		} catch (MalformedURLException e) {
			System.out.println(Util.exceptionMessage(e));
		}
		loader = new URLClassLoader(urls);
		return loader;
	}

}
