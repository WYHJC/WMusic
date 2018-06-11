package com.example.wyhjc.musicplayer.model;

import java.util.UUID;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject{
    private String id;
    private String phone;
    private String password;
    private String name;
    private String imageUrl;

    public User(){
        id = getUUID();//生成随机32位id
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String getUUID(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }
}
