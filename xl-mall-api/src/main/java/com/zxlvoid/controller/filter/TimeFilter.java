package com.zxlvoid.controller.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author whoiszxl
 *
 */
@Component
public class TimeFilter implements Filter{

	private Logger logger = LoggerFactory.getLogger(TimeFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("TimeFilter.init()");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("request请求开始 start................");
		long start = new Date().getTime();
		chain.doFilter(request, response);
		logger.info("request请求耗时" + (new Date().getTime() - start) + "................");
		logger.info("request请求结束 end................");
		
	}

	@Override
	public void destroy() {
		System.out.println("TimeFilter.destroy()");
	}

}
