package com.resource.context;

import com.resource.entity.User;

/**
 * Created by admin on 2018/2/26.
 */
public class UserContext {

    private static ThreadLocal<User> userContext = new ThreadLocal();


    public static void set(User user) {
        userContext.set(user);
    }

    public static User get() {
        return userContext.get();
    }
}
