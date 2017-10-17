package com.cn.ttz.pojo;

public class Ttz_user_relation {
    private Integer id;

    private Integer userId;

    private Integer parentId;

    private Integer orderId;

    private Byte type;

    private Integer createTime;

    private Integer updateTime;

    private Integer ttzGoodsId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTtzGoodsId() {
        return ttzGoodsId;
    }

    public void setTtzGoodsId(Integer ttzGoodsId) {
        this.ttzGoodsId = ttzGoodsId;
    }
}