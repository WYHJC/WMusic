package com.example.wyhjc.musicplayer.manager;

import com.example.wyhjc.musicplayer.model.User;

public class UserManager {
    private static UserManager instance = null;
    private User mUser = null;

    private UserManager(){}

    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    /**
     * 判断当前是否是登录状态
     * @return
     */
    public boolean isLogined(){
        return (mUser != null);
    }
}
