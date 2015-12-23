package org.chris.cattom.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {
	/**
	 * Òì³£¶ÑÕ»
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
	 * ¹Ø±ÕÁ÷
	 * @param is
	 * @param os
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
}
