package org.chris.cattom.server;

import java.io.File;

public class Contants {
	public static final String WEB_APP = "webapp";
	public static final String WEB_ROOT = System.getProperty("user.dir")+File.separator+"webroot";
	public static final String WEB_APP_ROOT = System.getProperty("user.dir")+File.separator+"webapp";
	public static final String HTTP_NEWLINE = "\r\n";
	public static final String SPACE = " ";
	public static final String HTTP_PROTOCOL_VERSION = "HTTP/1.1";
}
