package com.resource.interceptor;

import com.resource.context.UserContext;
import com.resource.entity.User;
import com.resource.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2018/2/26.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        Cookie[] cookies = request.getCookies();

        String url = request.getRequestURI();


        if (UserContext.get() == null && !url.equals("/resource/toLogin")  ) {

            if(cookies!=null){
                for (Cookie cookie : cookies) {

                    if (cookie.getName().equals("uid")) {
                        String userKey = cookie.getValue();
                        User user = redisService.get(userKey, User.class);

                        if (user != null) {
                            UserContext.set(user);
                        }

                        return true;
                    }
                }
            }

        } else {
            return true;
        }

        response.sendRedirect(request.getContextPath()+"/resource/toLogin");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
