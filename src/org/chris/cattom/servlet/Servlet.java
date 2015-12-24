package org.chris.cattom.servlet;

import java.io.IOException;

public interface Servlet {
	public void service(CatRequest request, CatResponse response) throws ServletException,IOException;
	public void init();
	public void destroy();
}
