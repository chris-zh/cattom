package org.chris.cattom.main;

import org.chris.cattom.server.CatServer;

public class MainServer {
	public static void main(String[] args) {
		CatServer server = CatServer.getServer();
		server.await();
	}
}
