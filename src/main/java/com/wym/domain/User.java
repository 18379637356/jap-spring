package com.wym.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 */
@Table(name = "sys_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;

    @Column(name = "userCode")
    private String userCode;

    @Column(name = "userName")
    private String userName;

    @Column(name = "userPwd")
    private String userPwd;

    @Column(name = "userState")
    private String userState;

    @ManyToMany
    @JoinTable(name = "user_role_rel",//中間表類型
            //中间表user_role_rel字段关联sys_user表的主键字段user_id
            //中间表的外键字段关联当前实体类所对应表的主键字段
            joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "uid")},
            //中间表user_role_rel的字段关联sys_role表的主键rold_id
            //中间表的外键字段关联对方表的主键字段
            inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "rid")}
    )
    private Set<Role> roles = new HashSet<>();


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userState='" + userState + '\'' +
                ", roles=" + roles +
                '}';
    }
}
