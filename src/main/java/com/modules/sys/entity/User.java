package com.modules.sys.entity;

import com.common.persistence.DataEntity;

import java.io.Serializable;
import java.util.Date;

public class User extends DataEntity<User> {

    private static final long serialVersionUID = 1L;

    private String loginName;
    private String password;
    private String name;
    private String loginFlag;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }
}