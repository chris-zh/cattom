package org.chris.cattom.server;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DogServlet implements Servlet{

	public void destroy() {
		System.out.println("destroy");
		
	}

	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void init(ServletConfig arg0) throws ServletException {
		System.out.println("init");
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpCatResponse res = (HttpCatResponse)response;
		System.out.println("service");
		String message = "I'm a dog Servlet! My hashCode is "+this.hashCode();
//		PrintWriter pw = response.getWriter();
//		pw.write("我是一个Servlet！");
		OutputStream os = res.getOutput();
		System.out.println("42--"+os);
		os.write(message.getBytes());
	}

}
