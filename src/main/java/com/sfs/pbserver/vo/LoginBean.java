package com.sfs.pbserver.vo;

/**
 * 登录接口封装对象
 * @author liaowm5
 * @date Dec 10, 2018
 */
public class LoginBean {
    private String email;
    private String passwd;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
