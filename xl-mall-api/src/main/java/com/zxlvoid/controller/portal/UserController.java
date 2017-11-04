package com.zxlvoid.controller.portal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zxlvoid.common.Const;
import com.zxlvoid.common.ResponseCode;
import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.User;
import com.zxlvoid.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 
 * @author whoiszxl
 *
 */
@Api(value = "前台用户模块",description="前台用户模块")
@RestController
@RequestMapping("/user/")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IUserService iUserService;
	
	@PostMapping("test")
	public ServerResponse<String> hello() {
		ServerResponse<String> testApi = iUserService.testApi();
		logger.info("testtesttesttesttesttesttesttesttesttesttesttest11111111111111111111111111111111");
		return testApi;
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("login.do")
	@ApiOperation(value = "根据账号密码登录")
	public ServerResponse<User> login(String username,String password,HttpSession session) {
		ServerResponse<User> response = iUserService.login(username, password);
		if(response.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
	}
	
	@PostMapping("logout.do")
	public ServerResponse<String> logout(HttpSession session){
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.createBySuccess();
	}
	
	@PostMapping("register.do")
	public ServerResponse<String> register(User user){
		ServerResponse<String> response = iUserService.register(user);
		return response;
	}
	
	@PostMapping("check_valid.do")
	public ServerResponse<String> checkValid(String str,String type){
		return iUserService.checkVaild(str, type);
	}
	
	@PostMapping("get_user_info.do")
	public ServerResponse<User> getUserInfo(HttpSession session){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user != null) {
			return ServerResponse.createBySuccess(user);
		}
		return ServerResponse.createByErrorMessage("用户未登录,无法获取详细信息");
	}
	
	@PostMapping("forget_get_question.do")
	public ServerResponse<String> forgetGetQuestion(String username){
		 return iUserService.selectQuestion(username);
	}
	
	@PostMapping("forget_check_answer.do")
	public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
		return iUserService.checkAnswer(username, question, answer);
	}
	
	@PostMapping("forget_reset_password.do")
	public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
		return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
	}
	
	@PostMapping("reset_password.do")
	public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
		User user = (User) session.getAttribute(Const.CURRENT_USER);
		if(user == null) {
			return ServerResponse.createByErrorMessage("用户未登录");
		}
		return iUserService.resetPassword(passwordOld, passwordNew, user);
	}
	
	@PostMapping("update_information.do")
	public ServerResponse<User> update_information(HttpSession session,User user){
		User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
		if(currentUser == null) {
			return ServerResponse.createByErrorMessage("用户未登录");
		} 
		user.setId(currentUser.getId());
		user.setUsername(currentUser.getUsername());
		ServerResponse<User> response = iUserService.updateInformation(user);
		if(response.isSuccess()) {
			response.getData().setUsername(currentUser.getUsername());
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		
		return response;
	}
	
	
	@PostMapping("get_information.do")
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }
	
}
