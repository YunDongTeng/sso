package com.resource.redis;

/**
 * Created by admin on 2018/1/18.
 */
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireTime, String prefix) {
        super(expireTime, prefix);
    }

    public static UserKey GET_BY_ID = new UserKey("id");

    public static UserKey GET_BY_NAME = new UserKey(3000, "name");

    public static UserKey USER = new UserKey(7*24*60*60*1000,"user");

}
