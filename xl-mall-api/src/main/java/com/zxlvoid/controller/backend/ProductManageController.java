package com.zxlvoid.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zxlvoid.common.Const;
import com.zxlvoid.common.ResponseCode;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.Product;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IUserService;

/**
 * 
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

	@Autowired
	private IUserService iUserService;
	
	public ServerResponse productSave(HttpSession session, Product product) {
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user == null) {
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
		}
		
		if(iUserService.checkAdminRole(user).isSuccess()) {
			return ServerResponse.createByErrorMessage("无管理员权限");
		}else {
			return ServerResponse.createByErrorMessage("无管理员权限");
		}
	}

}
