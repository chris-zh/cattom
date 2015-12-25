package org.chris.cattom.main;

import org.chris.cattom.server.CatServer;
import org.chris.cattom.server.ServerAccessor;

public class MainServer {
	public static void main(String[] args) {
		CatServer server = ServerAccessor.getServer();
		server.await();
	}
}
