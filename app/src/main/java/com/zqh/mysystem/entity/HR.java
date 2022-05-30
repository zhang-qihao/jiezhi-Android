package com.zqh.mysystem.entity;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.entity
 * @className: HR
 * @author: Zhangqihao
 * @description: TODO
 * @date: 2022/5/22
 */
public class HR extends User{
    public HR() {
        super();
    }
    public HR(String account, String password) {
        super(account, password, "hr");
    }
}
