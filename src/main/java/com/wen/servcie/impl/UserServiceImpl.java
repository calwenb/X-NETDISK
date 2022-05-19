package com.wen.servcie.impl;

import com.wen.mapper.UserMapper;
import com.wen.pojo.User;
import com.wen.servcie.FileStoreService;
import com.wen.servcie.MailService;
import com.wen.servcie.TokenService;
import com.wen.servcie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.文
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    FileStoreService storeService;
    @Resource
    MailService mailService;
    @Resource
    RedisTemplate redisTemplate;


    /**
     * 查询全部用户
     *
     * @return 全部用户
     */
    @Override
    public List<User> queryUsers() {
        return userMapper.queryUsers();
    }

    /**
     * 增加全部用户
     *
     * @param user
     * @return 修改状态
     */
    @Override
    public int addUser(User user) {
        return 0;
    }

    /**
     * 修改全部用户
     *
     * @param userID
     * @return
     */
    @Override
    public int deleteUser(int userID) {
        return 0;
    }

    /**
     * 修改全部用户
     *
     * @param user
     * @return 修改状态
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 登录
     *
     * @param loginName
     * @param pwd
     * @return
     */
    @Override
    public User login(String loginName, String pwd) {
        return userMapper.login(loginName, pwd);
    }

    @Override
    public Map<String, Object> register(User user) {
        HashMap<String, Object> rs = new HashMap<>(2);
        try {
            userMapper.addUser(user);
        } catch (Exception e) {
            rs.put("error", "注册失败，账号已存在");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return rs;
        }
        if (!storeService.initStore(user.getId())) {
            //回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rs.put("error", "初始化用户仓库失败");
        }
        rs.put("user", user);
        return rs;
    }


    @Override
    public User getUserById(int userID) {
        return userMapper.getUserById(userID);
    }

    @Override
    public boolean sendCode(String loginName, String email) {
        User user = userMapper.getUserByLName(loginName);
        if (user == null) {
            return false;
        }
        if (!email.equals(user.getEmail())) {
            return false;
        }
        try {
            String code = this.createCode();
            String subject, content;
            subject = "重置密码";
            content = "账号: " + loginName + "，您好。\n" +
                    "您当前正在重置密码，您的验证码为：" + code;
            mailService.sendSimpleMail(email, subject, content);
            String key = "code:lname:" + loginName;
            redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean verifyCode(String loginName, String code) {
        String key = "code:lname:" + loginName;
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return false;
        }
        String realCode = String.valueOf(value);
        if (realCode.equals(code)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean repwd(String loginName, String password) {
        User user = userMapper.getUserByLName(loginName);
        if (user == null) {
            return false;
        }
        user.setPassWord(password);
        userMapper.updateUser(user);
        return true;
    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        //文件生成码
        for (int i = 0; i < 5; i++) {
            code.append(new Random().nextInt(9));
        }
        return String.valueOf(code);
    }
}
