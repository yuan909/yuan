package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "e_addres")
public class Addres implements Serializable {
    /**
     * 地址编号
     */
    @Id
    @Column(name = "addres_id")
    private Integer addresId;

    /**
     * 地址名称
     */
    @Column(name = "addres_name")
    private String addresName;

    private static final long serialVersionUID = 1L;

    /**
     * 获取地址编号
     *
     * @return addres_id - 地址编号
     */
    public Integer getAddresId() {
        return addresId;
    }

    /**
     * 设置地址编号
     *
     * @param addresId 地址编号
     */
    public void setAddresId(Integer addresId) {
        this.addresId = addresId;
    }

    /**
     * 获取地址名称
     *
     * @return addres_name - 地址名称
     */
    public String getAddresName() {
        return addresName;
    }

    /**
     * 设置地址名称
     *
     * @param addresName 地址名称
     */
    public void setAddresName(String addresName) {
        this.addresName = addresName == null ? null : addresName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", addresId=").append(addresId);
        sb.append(", addresName=").append(addresName);
        sb.append("]");
        return sb.toString();
    }
}