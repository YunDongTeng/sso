package com.resource.web;

import ch.qos.logback.core.util.ContextUtil;
import com.resource.context.UserContext;
import com.resource.entity.User;
import com.resource.redis.RedisService;
import com.resource.result.Result;
import com.resource.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/2/26.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/toLogin")
    public String login() {

        if (UserContext.get() != null) {
            return "index";
        }
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String toLogin(@RequestParam("userName") String userName, @RequestParam("password") String password) {

        String url = "http://127.0.0.1:8081/user/login";
        Map<String, String> param = new HashMap<String, String>();

        param.put("userName", userName);
        param.put("password", password);
        String jsonResult = HttpClientUtil.doPost(url, param);

        return jsonResult;
    }

    @GetMapping("/index")
    public String toIndex() {
        return "index";
    }
}
