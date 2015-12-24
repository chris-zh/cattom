package org.chris.cattom.server;

import java.io.IOException;

import org.chris.cattom.servlet.CatRequest;
import org.chris.cattom.servlet.CatResponse;
import org.chris.cattom.util.Util;

public class StaticResourceProcessor {
	/**
	 * 处理静态资源
	 * @param request
	 * @param response
	 */
	public void process(CatRequest request, CatResponse response) {
		try {
			response.sendStaticResource();
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		}
	}

}
