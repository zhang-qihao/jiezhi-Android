package com.zqh.mysystem.bean;

/**
 * @projectName: MySystem
 * @package: com.zqh.mysystem.bean
 * @className: job_infos
 * @author: Zhangqihao
 * @description: TODO
 * @date: 2022/5/31
 */
public class job_infos {
    String jid;
    String title;
    String shortName;
    String salary;
    String education;
    String experience;
    String address;
    String industry;
    String scale;
    String companyType;
    String nature;

    public job_infos(String jid, String title, String shortName, String salary, String education, String experience, String address,
                     String industry, String scale, String companyType, String nature) {
        this.jid = jid;
        this.title = title;
        this.shortName = shortName;
        this.salary = salary;
        this.education = education;
        this.experience = experience;
        this.address = address;
        this.industry = industry;
        this.scale = scale;
        this.companyType = companyType;
        this.nature = nature;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
