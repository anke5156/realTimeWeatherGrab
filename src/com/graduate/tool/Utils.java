/**
 * Copyright (c) 2016-2026 Mininglamp Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.graduate.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

/**
 * @author czy
 * @since Ver1.0
 */
public class Utils {
	public final static int DEFAULT_BUFFER_SIZE = 1024*4;
	
	public static String read(InputStream in){
		InputStreamReader reader;
		try{
			reader = new InputStreamReader(in,"UTF-8");
		}catch(UnsupportedEncodingException e){
			throw new IllegalStateException(e.getMessage(),e);
		}
		return read(reader);
	}
	public static String read(Reader reader){
		try{
			StringWriter writer = new StringWriter();
			
			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			int n=0;
			while(-1!=(n=reader.read(buffer))){
				writer.write(buffer,0,n);
			}
			return writer.toString();
		}catch(IOException e){
			throw new IllegalStateException("read error",e);
		}
	}
	public static String readFromResource(String resource) throws IOException{
		InputStream in = null;
		try{
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if(in == null){
				in = Utils.class.getResourceAsStream(resource);
			}
			if(in == null){
				return null;
			}
			String text = Utils.read(in);
			return text;
		}finally{
			CloseUtil.closeIO(in);
		}
	}
	public static boolean isExistResource(String resource) throws IOException{
		InputStream in = null;
		boolean existFlag = true;
		try{
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if(in == null){
				in = Utils.class.getResourceAsStream(resource);
			}
			if(in == null){
				existFlag = false;
			}else{
				existFlag = true;
			}
			return existFlag;
		}finally{
			CloseUtil.closeIO(in);
		}
	}
	public static byte[] readByteArrayFromResource(String resource) throws IOException{
		InputStream in = null;
		try{
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if(in == null){
				return null;
			}
			return readByteArray(in);
		}finally{
			CloseUtil.closeIO(in);
		}
	}
	public static byte[] readByteArray(InputStream input) throws IOException{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input,output);
		return output.toByteArray();
	}
	
	public static long copy(InputStream input,OutputStream output) throws IOException{
		final int EOF = -1;
		
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		
		long count = 0;
		int n = 0;
		while(EOF != (n = input.read(buffer))){
			output.write(buffer,0,n);
			count +=n;
		}
		return count;
	}
	public static long copy(String resource,OutputStream output) throws IOException{
		long count;
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(resource);
			final int EOF = -1;
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			count = 0;
			int n = 0;
			while (EOF != (n = in.read(buffer))) {
				output.write(buffer, 0, n);
				count += n;
			}
			return count;
		} catch (Exception e) {
		} finally{
			CloseUtil.closeIO(in);
		}
		return 0;
	}
	
	public static String read(Reader reader,int length){
		try{
			char[] buffer = new char[length];
			
			int offset = 0;
			int rest = length;
			int len;
			while((len = reader.read(buffer,offset,rest))!=-1){
				rest -=len;
				offset +=len;
				
				if(rest==0){
					break;
				}
			}
			return new String(buffer,0,length-rest);
		}catch(IOException ex){
			throw new IllegalStateException("read error",ex);
		}
	}
	public static String toString(java.util.Date date){
		if(date == null){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	public static String getStackTrace(Throwable ex){
		StringWriter buf = new StringWriter();
		ex.printStackTrace(new PrintWriter(buf));
		
		return buf.toString();
	}
	public static String toString(StackTraceElement[] stackTrace){
		StringBuilder buf = new StringBuilder();
		for(StackTraceElement item : stackTrace){
			buf.append(item.toString());
			buf.append("\n");
		}
		return buf.toString();
	}
	public static Class<?> loadClass(String className){
		Class<?> clazz = null;
		if(className == null){
			return null;
		}
		try{
			return Class.forName(className);
		}catch(ClassNotFoundException e){
		}
		ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
		if(ctxClassLoader != null){
			try{
				clazz = ctxClassLoader.loadClass(className);
			}catch(ClassNotFoundException e){
			}
		}
		return clazz;
	}

	public static String getRealPath(String path){
		return Thread.currentThread().getContextClassLoader().getResource(path).getPath();
	}
}