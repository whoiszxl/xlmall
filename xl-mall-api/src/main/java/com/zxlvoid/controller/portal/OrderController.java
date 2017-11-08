package com.zxlvoid.controller.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zxlvoid.common.ServerResponse;

import io.swagger.annotations.Api;

/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "前台订单模块",description = "前台订单模块")
@RestController
@RequestMapping("/order/")
public class OrderController {

	
	public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		return null;
	}
	
}
