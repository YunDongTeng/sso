package com.user.web;

import com.user.entity.User;
import com.user.result.Result;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by admin on 2018/2/26.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestParam("userName") String userName,
                              @RequestParam("password") String password,
                              HttpServletRequest request, HttpServletResponse response) {

        Result<User> result = userService.login(userName, password);

        return result;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<User> register(@Valid User user) {
        return userService.registerUser(user);
    }

    /**
     * 退出
     * @param uid
     * @param response
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(@RequestParam("uid") String uid, HttpServletResponse response) {

        Cookie userCookie = new Cookie("uid", uid);
        userCookie.setPath("/");
        userCookie.setMaxAge(0);

        return userService.logout(uid);

    }
}
