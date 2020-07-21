package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "e_express")
public class Express implements Serializable {
    /**
     * 快递编号
     */
    @Id
    @Column(name = "e_id")
    private Integer eId;

    /**
     * 快递名称
     */
    private String ename;

    /**
     * 成立时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * 注册资金
     */
    private String money;

    /**
     * 快递地址
     */
    @Column(name = "addres_id")
    private Integer addresId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取快递编号
     *
     * @return e_id - 快递编号
     */
    public Integer geteId() {
        return eId;
    }

    /**
     * 设置快递编号
     *
     * @param eId 快递编号
     */
    public void seteId(Integer eId) {
        this.eId = eId;
    }

    /**
     * 获取快递名称
     *
     * @return ename - 快递名称
     */
    public String getEname() {
        return ename;
    }

    /**
     * 设置快递名称
     *
     * @param ename 快递名称
     */
    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    /**
     * 获取成立时间
     *
     * @return birthday - 成立时间
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置成立时间
     *
     * @param birthday 成立时间
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取注册资金
     *
     * @return money - 注册资金
     */
    public String getMoney() {
        return money;
    }

    /**
     * 设置注册资金
     *
     * @param money 注册资金
     */
    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    /**
     * 获取快递地址
     *
     * @return addres_id - 快递地址
     */
    public Integer getAddresId() {
        return addresId;
    }

    /**
     * 设置快递地址
     *
     * @param addresId 快递地址
     */
    public void setAddresId(Integer addresId) {
        this.addresId = addresId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eId=").append(eId);
        sb.append(", ename=").append(ename);
        sb.append(", birthday=").append(birthday);
        sb.append(", money=").append(money);
        sb.append(", addresId=").append(addresId);
        sb.append("]");
        return sb.toString();
    }
}