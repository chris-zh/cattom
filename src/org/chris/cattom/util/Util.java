package org.chris.cattom.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import org.chris.cattom.server.CatServer;

public class Util {
	/**
	 * 异常堆栈
	 * @param e
	 * @return
	 */
	public static String exceptionMessage(Throwable e) {
		String result = null;
		StringWriter out = new StringWriter();
		try {
			e.printStackTrace(new PrintWriter(out));
			result = out.toString();
		} catch(Exception e1) {
		} finally {
			try {
				out.close();
			} catch(Exception e2) {
			}
		}
		return result;
	}
	/**
	 * 关闭流
	 * @param is 输入流
	 * @param os 输出流
	 */
	public static void closeStream(InputStream is, OutputStream os){
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				System.out.println(Util.exceptionMessage(e));
			}
		}
		if(is!=null){
			try {
				is.close();
			} catch (IOException e) {
				System.out.println(Util.exceptionMessage(e));
			}
		}
	}
	/**
	 * 从输入流获取数据，写入输出流
	 * @param is 输入流
	 * @param os 输出流
	 * @param bufferSize
	 */
	public static void writeOutputFromInput(InputStream is, OutputStream os, int bufferSize){
		byte[] bytes = new byte[bufferSize];
		int ch;
		try {
			ch = is.read(bytes, 0, bufferSize);
			while (ch != -1) {
				os.write(bytes, 0, ch);
				ch = is.read(bytes, 0, bufferSize);
			}
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		}
	}
	/**
	 * 将字节数组写入输出流
	 * @param os
	 * @param bytes
	 */
	public static void writeOutput(OutputStream os, byte[] bytes){
		try {
			os.write(bytes);
		} catch (IOException e) {
			System.out.println(Util.exceptionMessage(e));
		};
	}
}
