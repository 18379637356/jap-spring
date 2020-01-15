package com.wym.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 */
@Table(name = "sys_role")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Integer rid;

    @Column(name = "roldName")
    private String roldName;

    @Column(name = "roldMemo")
    private String roldMemo;

    /*配置多对多
     * mappedBy = "users" 对方的属性名
     *放弃维护权
     * */
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getRoldName() {
        return roldName;
    }

    public void setRoldName(String roldName) {
        this.roldName = roldName;
    }

    public String getRoldMemo() {
        return roldMemo;
    }

    public void setRoldMemo(String roldMemo) {
        this.roldMemo = roldMemo;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", roldName='" + roldName + '\'' +
                ", roldMemo='" + roldMemo + '\'' +
                ", users=" + users +
                '}';
    }
}
