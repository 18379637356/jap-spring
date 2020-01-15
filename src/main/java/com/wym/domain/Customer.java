package com.wym.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户表
 * 实体类
 * 一的一方
 */
@Entity
@Table(name="customer_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "addr")
    private String addr;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private String sex;
    @Column(name = "tell")
    private Integer tell;

    @Transient  //并非一个到数据库表的字段的映射,ORM 框架将忽略该属性。
    private Integer nuzz;

    /**
     * 配置一对多
     */
    /**
     * cascade:配置级联操作
     * 		CascadeType.MERGE	级联更新
     * 		CascadeType.PERSIST	级联保存：
     * 		CascadeType.REFRESH 级联刷新：
     * 		CascadeType.REMOVE	级联删除：
     * 		CascadeType.ALL		包含所有
     */
   /* @OneToMany(targetEntity = LikeMan.class)
    @JoinColumn(name = "lik_cust_id",referencedColumnName = "id")*/
   //fetch =FetchType.EAGER 立即加载
   @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch =FetchType.EAGER)//配置属性名
    private Set<LikeMan>  likemans=new HashSet<>();

    public Set<LikeMan> getLikemans() {
        return likemans;
    }

    public void setLikemans(Set<LikeMan> likemans) {
        this.likemans = likemans;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getTell() {
        return tell;
    }

    public void setTell(Integer tell) {
        this.tell = tell;
    }

    public Integer getNuzz() {
        return nuzz;
    }

    public void setNuzz(Integer nuzz) {
        this.nuzz = nuzz;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", addr='" + addr + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tell=" + tell +
                ", likemans=" + likemans +
                '}';
    }
}
