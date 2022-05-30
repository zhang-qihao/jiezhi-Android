package com.zqh.mysystem.bean;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.entity
 * @className: User
 * @author: Zhangqihao
 * @description: 账号密码实体
 * @date: 2022/5/21
 */
public class User {
    private String account;
    private String password;
    private String identity;

    public User() {
    }

    public User(String account, String password, String identity) {
        this.account = account;
        this.password = password;
        this.identity = identity;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
