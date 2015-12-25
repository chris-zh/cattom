package org.chris.cattom.server;

public class ServerAccessor {
	/**
	 * 获取CatServer
	 * @return
	 */
	public static CatServer getServer(){
		return CatServer.getServer();
	}
	/**
	 *
	 * @return
	 */
	public static String getWebRepository() {
		return CatServer.getServer().getWebRepository();
	}
	/**
	 * 获取服务器缓存
	 * @return
	 */
	public static Cache getServerCache(){
		return CatServer.getServer().getCache();
	}
	
}
