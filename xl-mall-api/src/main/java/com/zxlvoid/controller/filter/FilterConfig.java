package com.zxlvoid.controller.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author whoiszxl
 *
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean adminFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		
		AdminFilter adminFilter = new AdminFilter();
		filterRegistrationBean.setFilter(adminFilter);
		
		List<String> urls = new ArrayList<>();
		urls.add("/manage/category/*");
		//urls.add("/manage/product/*");
		filterRegistrationBean.setUrlPatterns(urls);
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.setName("adminFilter");
		return filterRegistrationBean;
	}
	
	//@Bean
	public FilterRegistrationBean timeFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		
		TimeFilter timeFilter = new TimeFilter();
		filterRegistrationBean.setFilter(timeFilter);
		
		List<String> urls = new ArrayList<>();
		urls.add("/manage/category/*");
		filterRegistrationBean.setUrlPatterns(urls);
		filterRegistrationBean.setOrder(2);
		filterRegistrationBean.setName("timeFilter");
		return filterRegistrationBean;
	}
	
}
