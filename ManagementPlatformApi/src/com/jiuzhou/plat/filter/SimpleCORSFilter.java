package com.jiuzhou.plat.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;

import com.jiuzhou.plat.util.PropertiesUtils;
import com.jiuzhou.plat.wrapper.InputStreamRequestWrapper;

@Component
public class SimpleCORSFilter implements Filter {
	
	private static String isRelease = PropertiesUtils.getProp("isRelease");
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (!"1".equals(isRelease)) {
			String origin = request.getHeader("Origin");
	        response.setHeader("Access-Control-Allow-Origin", origin);
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");  
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        
		} else {
			String m=request.getMethod();  
			if(!"GET".equals(m)&&!"POST".equals(m)){  
	            System.out.println("GET or POST only  ");  
	            response.setHeader("Allow", "GET,POST");  
	            response.setStatus(405);  
	            return;  
	        } 
		}
        
        //引用request包装器
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest && !ServletFileUpload.isMultipartContent(request)) {
            requestWrapper = new InputStreamRequestWrapper(request);
        }
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
        

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
