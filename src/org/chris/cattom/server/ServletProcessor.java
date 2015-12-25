package org.chris.cattom.server;

import java.io.IOException;

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
//		Map<Object, Object> cache = CatServer.getServer().getCache();
		Cache cache = ServerAccessor.getServerCache();
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);
		Servlet servlet = null;
		try {
			Object servletObject = cache.get(servletName);
			if(servletObject!=null){
				servlet = (Servlet) servletObject;
			}
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
