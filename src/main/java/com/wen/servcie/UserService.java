package com.wen.servcie;

import com.wen.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author Mr.文
 */
public interface UserService {
    /**
     * 查询全部用户
     *
     * @return 全部用户
     */
    List<User> queryUsers();

    /**
     * 增加全部用户
     *
     * @param user
     * @return 修改状态
     */
    int addUser(User user);


    /**
     * 修改全部用户
     *
     * @param userID
     * @return
     */
    int deleteUser(int userID);

    /**
     * 修改全部用户
     *
     * @param user
     * @return 修改状态
     */
    int updateUser(User user);


    /**
     * 登录
     *
     * @param loginName
     * @param pwd
     * @return
     */
    User login(String loginName, String pwd);


    Map<String,Object> register(String userName, String loginName, String pwd);

    User getUserById(int userID);
}
