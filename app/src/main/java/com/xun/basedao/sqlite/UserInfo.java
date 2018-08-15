package com.xun.basedao.sqlite;


import com.xun.basedao.annotation.DbTable;

@DbTable("user_info")
public class UserInfo {
    private Integer _id;
    private String name;
    private String picHad;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicHad() {
        return picHad;
    }

    public void setPicHad(String picHad) {
        this.picHad = picHad;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", picHad='" + picHad + '\'' +
                ", age=" + age +
                '}';
    }
}
