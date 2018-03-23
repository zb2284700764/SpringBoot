package com.modules.sys.entity;

import com.common.persistence.DataEntity;

public class Role extends DataEntity<Role> {

    private String name;    // 角色名称
    private String enname;    // 英文名称
    private String roleType;// 权限类型
    private String useable;        //是否是可用

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }
}