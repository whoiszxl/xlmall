package com.zxlvoid.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxlvoid.common.Const;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IUserService;

/**
 * 
 * @author whoiszxl
 *
 */
//@Component
public class AdminFilter implements Filter{
	
	@Autowired
	private IUserService iUserService;
	
	private Logger logger = LoggerFactory.getLogger(AdminFilter.class);
	
	/**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();
	
	public IUserService getiUserService() {
		return iUserService;
	}
	
	public void setiUserService(IUserService iUserService) {
		this.iUserService = iUserService;
	}
	
	/**
	 * 需要在过滤器执行之前提前获取到spring容器中的service,不然会报空指针异常.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("AdminFilter.init()");
		ServletContext context = filterConfig.getServletContext();
		WebApplicationContext ctxs = WebApplicationContextUtils.getWebApplicationContext(context);
		iUserService = (IUserService) ctxs.getBean("iUserService");
		Pattern p = Pattern.compile("/manage/user/login");
		patterns.add(p);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		//校验uri，排除不需要过滤的 
		String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
		if(isInclude(url)) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = httpServletRequest.getSession();
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		ServerResponse<String> errorObj = null;
		if(user == null) {
			errorObj = ServerResponse.createByErrorMessage("管理员未登录,请登录");
		}else if(this.iUserService.checkAdminRole(user).isSuccess()) {
			//是管理员,过滤器放行
            chain.doFilter(request, response);
            return;
		}else {
			logger.warn("用户名为:"+user.getUsername()+" 的用户试图访问管理员权限.......");
			errorObj = ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		ObjectMapper objectMapper = new ObjectMapper();
		String errorStr = objectMapper.writeValueAsString(errorObj);
		response.getWriter().write(errorStr);
	}

	@Override
	public void destroy() {
		System.out.println("AdminFilter.destroy()");
	}
	
	/**
     * 是否需要过滤
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
