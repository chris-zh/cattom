package org.chris.cattom.http;

public class HTTPStatusCode {
	public static final int OK = 200;
	public static final int NOT_FOUND = 404;
	public static String mapDescription(int statusCode){
		String description = null;
		switch (statusCode) {
		case 200:
			description = "OK";
			break;
		case 404:
			description = "NOT FOUND";
			break;
		case 503:
			description = "";
			break;
		default:
			break;
		}
		return description;
	}
}
