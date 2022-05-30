package com.zqh.mysystem.entity;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.entity
 * @className: Applicant
 * @author: Zhangqihao
 * @description: Applicant 实体继承 User 实体
 * @date: 2022/5/22
 */
public class Applicant extends User{

    public Applicant() {
        super();
    }
    public Applicant(String account, String password) {
        super(account, password, "applicant");
    }
}
