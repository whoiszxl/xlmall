package com.zxlvoid.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author whoiszxl
 *
 */
// @Component
public class TimeFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(TimeFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("TimeFilter.init()");
		Pattern p = Pattern.compile("/favicon.ico");
		patterns.add(p);
	}

	/**
	 * 封装，不需要过滤的list列表
	 */
	protected static List<Pattern> patterns = new ArrayList<Pattern>();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		// 校验uri，排除不需要过滤的
		String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		if (isInclude(url)) {
			logger.info("校验通过");
			chain.doFilter(request, response);
			return;
		}
		logger.info("request请求:" + httpServletRequest.getRequestURI() + " start................");
		long start = new Date().getTime();
		chain.doFilter(request, response);
		logger.info("request请求耗时" + (new Date().getTime() - start) + "................");
		logger.info("request请求结束 end................");

	}

	@Override
	public void destroy() {
		System.out.println("TimeFilter.destroy()");
	}

	/**
	 * 是否需要过滤
	 * 
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
}
