package com.zxlvoid.service;

import com.zxlvoid.common.ServerResponse;
import com.zxlvoid.pojo.User;

/**
 * 
 * @author whoiszxl
 *
 */
public interface IUserService {
	
	ServerResponse<User> login(String username,String password);
	
	ServerResponse<String> register(User user);
	
	ServerResponse<String> checkVaild(String str,String type);
	
	ServerResponse<String> selectQuestion(String username);
	
	ServerResponse<String> checkAnswer(String username,String question,String answer);
	
	ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);
	
	ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);
	
	ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
    
    ServerResponse<String> testApi();
}
