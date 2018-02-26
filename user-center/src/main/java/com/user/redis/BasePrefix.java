package com.user.redis;

/**
 * Created by admin on 2018/1/18.
 */
public abstract class BasePrefix implements Prefix {

    private int expireTime;

    private String prefix;

    public BasePrefix() {
    }

    public BasePrefix(String prefix) {
        this.expireTime = 0;
        this.prefix = prefix;
    }

    public BasePrefix(int expireTime, String prefix) {
        this.expireTime = expireTime;
        this.prefix = prefix;
    }

    @Override
    public int getExpire() {  //默认0  永不过期
        return expireTime;
    }

    @Override
    public String getPrefix() {
        String prefix = getClass().getName() + ":" + this.prefix;
        return prefix;
    }
}
