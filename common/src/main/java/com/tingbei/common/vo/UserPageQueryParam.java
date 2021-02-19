package com.tingbei.common.vo;

import java.io.Serializable;


public class UserPageQueryParam extends PageParam implements Serializable{
    private static final long serialVersionUID = -3977324913640655381L;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
