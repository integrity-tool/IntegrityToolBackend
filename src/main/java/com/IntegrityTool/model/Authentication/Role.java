package com.IntegrityTool.model.Authentication;

public class Role {
    private int roleId;
    private String roleName;

    public Role() {
        this.roleId = 0;
        this.roleName = "";
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}
