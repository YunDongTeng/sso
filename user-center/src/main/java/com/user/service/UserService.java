package com.user.service;

import com.user.common.util.IDUtil;
import com.user.common.util.Md5Util;
import com.user.dao.UserDao;
import com.user.entity.User;
import com.user.redis.RedisService;
import com.user.redis.UserKey;
import com.user.result.CodeMsg;
import com.user.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 2018/2/26.
 */
@Service
public class UserService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserDao userDao;

    public Result<User> login(String userName, String password) {

        User user = userDao.getUserByName(userName);

        if (user == null) {
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }

        if (Md5Util.vertifyPassword(password, user.getPassword(), user.getSalt()) == false) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

        user.setTid(IDUtil.uuid());
        user.setUid(IDUtil.uuid());
        redisService.set(user.getUid(), user, 3 * 24 * 60 * 60 * 1000);

        return Result.success(user);
    }

    public Result<User> registerUser(User user) {

        user.setId(IDUtil.uuid());
        user.setSalt(Md5Util.getSalt());
        user.setPassword(Md5Util.md5WithSalt(user.getPassword(), user.getSalt()));
        user.setLastLogin(new Date());

        //检查用户名是否合法
        if (userDao.getUserByName(user.getUserName()) != null) {
            return Result.error(CodeMsg.USERNAME_EXIST);
        }
        int result = userDao.insertUser(user);
        if (result > 0) {
            return Result.success(user);
        }
        return Result.error(CodeMsg.ADD_USER_FAILURE);
    }

    public Result<String> logout(String uid){

        boolean result = redisService.delete(uid);

        if(result==true){
            return Result.success("退出成功");
        }
        return Result.error(CodeMsg.LOGOUT_ERROR);

    }


}
