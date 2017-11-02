package com.zxlvoid.dao;

import com.zxlvoid.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    
    /**
     * 通过用户名校验用户是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);
    
    /**
     * 通过邮箱校验用户是否存在
     * @param username
     * @return
     */
    int checkEmail(String email);
    
    /**
     * 通过username和password登录
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username,@Param("password") String password);
    
    /**
     * 查询输入用户的密保问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);
    
    
    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);
    
    
    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);
    
    int checkPassword(@Param("password")String password,@Param("userId")Integer userId);
    
    int checkEmailByUserId(@Param("email") String email,@Param("userId") Integer userId);
}