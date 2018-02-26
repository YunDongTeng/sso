package com.user.dao;

import com.user.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2018/2/26.
 */
@Repository("userDao")
public interface UserDao {

    User getUserByName(String userName);

    int insertUser(User user);
}
