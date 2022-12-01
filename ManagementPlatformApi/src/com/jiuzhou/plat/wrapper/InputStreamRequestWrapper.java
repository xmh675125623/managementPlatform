package com.jiuzhou.plat.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.jiuzhou.plat.util.WebDataAESUtils;

/**
 * request包装器用于解决request中InputStream只能读取一次的问题
 * @author xingmh
 *
 */
public class InputStreamRequestWrapper extends HttpServletRequestWrapper {
    private String body;
	public InputStreamRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
//                char[] charBuffer = new char[128];
//                int bytesRead = -1;
//                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
//                    stringBuilder.append(charBuffer, 0, bytesRead);
//                }
                
                while (true) {
                	String line = bufferedReader.readLine();
                    if (line == null) {
                    	break;
                    }
                    stringBuilder.append(line);
                }
                
                stringBuilder.append("");
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        body = stringBuilder.toString();
        try {
        	if (StringUtils.isNotBlank(body)) {
        		body = WebDataAESUtils.webDataDecrypt(body);
        	} 
		} catch (Exception e) {
			body = "";
			e.printStackTrace();
		}
//        System.out.println(new String(body.getBytes("ISO-8859-1"), "UTF-8"));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes("ISO-8859-1"));
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
			public boolean isFinished() {
                return false;
            }
            @Override
			public boolean isReady() {
                return false;
            }
            @Override
			public void setReadListener(ReadListener readListener) {}
            @Override
			public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;

    }
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
    public String getBody() {
        try {
			return new String(this.body.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return "";
    }

}
