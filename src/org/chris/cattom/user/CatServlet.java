package org.chris.cattom.user;

import java.io.IOException;
import java.io.OutputStream;

import org.chris.cattom.servlet.CatRequest;
import org.chris.cattom.servlet.CatResponse;
import org.chris.cattom.servlet.Servlet;
import org.chris.cattom.servlet.ServletException;

public class CatServlet implements Servlet {

	public void destroy() {
		System.out.println("destroy");

	}

	@Override
	public void service(CatRequest request, CatResponse response)throws ServletException, IOException {
		CatResponse res = (CatResponse) response;
		System.out.println("service");
		String message = "I'm a cat Servlet! My hashCode is " + this.hashCode();
		// PrintWriter pw = response.getWriter();
		// pw.write("我是一个Servlet！");
		OutputStream os = res.getOutput();
		System.out.println("42--" + os);
		os.write(message.getBytes());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
