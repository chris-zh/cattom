package org.chris.cattom.server;

import java.io.IOException;
import java.util.Map;

import org.chris.cattom.servlet.CatRequest;
import org.chris.cattom.servlet.CatResponse;
import org.chris.cattom.servlet.Servlet;
import org.chris.cattom.servlet.ServletException;
import org.chris.cattom.util.ClassLoadUtil;
import org.chris.cattom.util.Util;
public class ServletProcessor {
	/**
	 * ¥¶¿ÌServlet
	 * 
	 * @param request
	 * @param response
	 */
	public void process(CatRequest request, CatResponse response) {
		Map<Object, Object> cache = CatServer.getServer().getCache();
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		Servlet servlet = null;
		try {
			servlet = (Servlet) cache.get(servletName);
			if (servlet == null) {
				servlet = ClassLoadUtil.loadAndInitServlet(servletName);
			}
			servlet.service(request, response);
		} catch (ServletException e) {
			System.out.println(Util.exceptionMessage(e));
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		}
	}
}
