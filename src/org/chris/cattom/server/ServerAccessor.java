package org.chris.cattom.server;

public class ServerAccessor {
	/**
	 * ��ȡCatServer
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
	 * ��ȡ����������
	 * @return
	 */
	public static Cache getServerCache(){
		return CatServer.getServer().getCache();
	}
	
}
