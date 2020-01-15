package com.wym.domain;

import javax.persistence.*;

/**
 * 联系人表
 * 多的一方
 */
@Entity
@Table(name = "linkman_table")
public class LikeMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lid")
    private Integer lid ;
    @Column(name="lname")
    private String lname;
    @Column(name="lphone")
    private String lphone;

    /*配置多的一方*/
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "lik_cust_id",referencedColumnName = "id")//referencedColumnName:指定引用主表的主键字段名称
    //用它的主键，对应联系人表中的外键
    private Customer customer;

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLphone() {
        return lphone;
    }

    public void setLphone(String lphone) {
        this.lphone = lphone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "LikeMan{" +
                "lid=" + lid +
                ", lname='" + lname + '\'' +
                ", lphone='" + lphone + '\'' +
                ", customer=" + customer +
                '}';
    }
}
