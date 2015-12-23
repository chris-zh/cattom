package org.chris.cattom.server;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.chris.cattom.util.Util;
public class ServletProcessor {
	/**
	 * ¥¶¿ÌServlet
	 * 
	 * @param request
	 * @param response
	 */
	public void process(HttpCatRequest request, HttpCatResponse response) {
		Map<Object, Object> cache = HttpServer.getServer().getCache();
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		Servlet servlet = null;
		Class<?> myClass = null;
		try {
			servlet = (Servlet) cache.get(servletName);
			if (servlet == null) {
				myClass = Class.forName(servletName);
				servlet = (Servlet) myClass.newInstance();
				cache.put(myClass.getName(), servlet);
			}
			servlet.service(request, response);
		} catch (ServletException e) {
			System.out.println(Util.exceptionMessage(e));
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		} catch (ClassNotFoundException e) {
			System.out.println(Util.exceptionMessage(e));
		}catch(InstantiationException e){
			System.out.println(Util.exceptionMessage(e));
		}catch(IllegalAccessException e){
			System.out.println(Util.exceptionMessage(e));
		}
	}

}
