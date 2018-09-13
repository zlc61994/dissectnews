package com.zlc.dissectnews.bean;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

public class User {
    String id;
    @Email(message = "邮箱格式错误")
    String username;
    @Size(min = 6,max = 8,message = "密码长度应该在6-8位")
    String password;
    String headUrl;//保存头像地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
