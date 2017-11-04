package com.zxlvoid.controller.backend;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zxlvoid.common.Const;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/manage/user")
public class UserManageController {

	
	@Autowired
	private IUserService iUserService;
	
	@PostMapping("login")
	@ApiOperation(value="后台管理员登录")
	public ServerResponse<User> login(String username,String password,HttpSession session){
		ServerResponse<User> response = iUserService.login(username, password);
		if(response.isSuccess()) {
			User user = response.getData();
			if(user.getRole() == Const.Role.ROLE_ADMIN) {
				//登录的是管理员
				session.setAttribute(Const.CURRENT_USER, user);
				return response;
			}else {
				return ServerResponse.createByErrorMessage("不是管理员无法登录");
			}
		}
		return response;
	}
	
}
