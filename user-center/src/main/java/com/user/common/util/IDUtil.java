package com.user.common.util;

import java.util.UUID;

/**
 * Created by admin on 2018/2/26.
 */
public class IDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
