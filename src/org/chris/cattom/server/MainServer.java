package org.chris.cattom.server;

public class MainServer {
	public static void main(String[] args) {
		HttpServer server = HttpServer.getServer();
		server.await();
	}
}
