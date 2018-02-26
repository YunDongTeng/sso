package com.user.result;

public class CodeMsg {

    private int code;
    private String msg;

    /**
     * 疑问1：使用静态内部类和使用枚举的效率问题
     */


    //成功
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");

    //登录用户不存在
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500, "user not exist");

    //密码错误
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500, "password error");

    //用户名已存在
    public static CodeMsg USERNAME_EXIST = new CodeMsg(500, "username has exist");

    //添加用户失败
    public static CodeMsg ADD_USER_FAILURE = new CodeMsg(500, "add user failure");

    //退出失败
    public static CodeMsg LOGOUT_ERROR = new CodeMsg(500, "logout failure");

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
