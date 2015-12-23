package org.chris.cattom.server;

import java.io.IOException;

import org.chris.cattom.util.Util;

public class StaticResourceProcessor {
	/**
	 * ����̬��Դ
	 * @param request
	 * @param response
	 */
	public void process(HttpCatRequest request, HttpCatResponse response) {
		try {
			response.sendStaticResource();
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		}
	}

}
