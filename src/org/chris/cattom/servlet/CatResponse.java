package org.chris.cattom.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.chris.cattom.http.HTTPContentType;
import org.chris.cattom.http.HTTPResponseHead;
import org.chris.cattom.http.HTTPStatusCode;
import org.chris.cattom.server.Contants;
import org.chris.cattom.util.Util;

public class CatResponse{
	private OutputStream output;
	private CatRequest request;
	private static final int BUFFER_SIZE = 1024;

	public CatResponse(OutputStream output) {
		this.output = output;
	}
	
	public OutputStream getOutput() {
		return output;
	}

	public void setRequest(CatRequest request){
		this.request = request;
	}
	/**
	 * ·¢ËÍ¾²Ì¬×ÊÔ´
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException {
		FileInputStream fis = null;
		HTTPResponseHead responseHead = null;
		try {
			String path = Contants.WEB_ROOT + request.getUri();
			File file = new File(path);
			if (file.exists()) {
				fis = new FileInputStream(file);
				responseHead = new HTTPResponseHead(HTTPStatusCode.OK, HTTPContentType.HTML, fis.available());
				Util.writeOutput(output, responseHead.getContextBytes());
				Util.writeOutputFromInput(fis, output, BUFFER_SIZE);
			} else {
				String fileNotFound = "<h1>File Not Found</h1>";
				responseHead = new HTTPResponseHead(HTTPStatusCode.NOT_FOUND, HTTPContentType.HTML, fileNotFound.length());
				Util.writeOutput(output, responseHead.getContextBytes());
			}
		} catch (Exception e) {
			System.out.println(Util.exceptionMessage(e));
		} finally {
			Util.closeStream(fis, null);
		}

	}
}
