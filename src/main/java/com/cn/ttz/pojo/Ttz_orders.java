package com.cn.ttz.pojo;

import java.math.BigDecimal;

public class Ttz_orders {
    private Integer id;

    private Integer userId;

    private Integer pidId;

    private String goodsId;

    private Integer createTime;

    private Integer updateTime;

    private Integer amount;

    private BigDecimal price;

    private Float commissionPercent;

    private Byte orderStatus;

    private String orderHash;

    private String orderSn;

    private Integer addTime;

    private Byte origin;

    private Float incomeRatio;

    private Float divideRatio;

    private BigDecimal payAmount;

    private BigDecimal effectsPrediction;

    private BigDecimal checkoutAmount;

    private BigDecimal incomePrediction;

    private Integer checkoutTime;

    private Float commissionRatio;

    private BigDecimal commissionAmount;

    private String category;

    private Long siteId;

    private Long adzoneId;

    private String goodsName;

    private String shopmanWangwang;

    private String storeName;

    private Float subsidyRatio;

    private BigDecimal subsidyAmount;

    private String pid;

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

    public Integer getPidId() {
        return pidId;
    }

    public void setPidId(Integer pidId) {
        this.pidId = pidId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Float commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderHash() {
        return orderHash;
    }

    public void setOrderHash(String orderHash) {
        this.orderHash = orderHash == null ? null : orderHash.trim();
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Byte getOrigin() {
        return origin;
    }

    public void setOrigin(Byte origin) {
        this.origin = origin;
    }

    public Float getIncomeRatio() {
        return incomeRatio;
    }

    public void setIncomeRatio(Float incomeRatio) {
        this.incomeRatio = incomeRatio;
    }

    public Float getDivideRatio() {
        return divideRatio;
    }

    public void setDivideRatio(Float divideRatio) {
        this.divideRatio = divideRatio;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getEffectsPrediction() {
        return effectsPrediction;
    }

    public void setEffectsPrediction(BigDecimal effectsPrediction) {
        this.effectsPrediction = effectsPrediction;
    }

    public BigDecimal getCheckoutAmount() {
        return checkoutAmount;
    }

    public void setCheckoutAmount(BigDecimal checkoutAmount) {
        this.checkoutAmount = checkoutAmount;
    }

    public BigDecimal getIncomePrediction() {
        return incomePrediction;
    }

    public void setIncomePrediction(BigDecimal incomePrediction) {
        this.incomePrediction = incomePrediction;
    }

    public Integer getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Integer checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Float getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(Float commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getShopmanWangwang() {
        return shopmanWangwang;
    }

    public void setShopmanWangwang(String shopmanWangwang) {
        this.shopmanWangwang = shopmanWangwang == null ? null : shopmanWangwang.trim();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public Float getSubsidyRatio() {
        return subsidyRatio;
    }

    public void setSubsidyRatio(Float subsidyRatio) {
        this.subsidyRatio = subsidyRatio;
    }

    public BigDecimal getSubsidyAmount() {
        return subsidyAmount;
    }

    public void setSubsidyAmount(BigDecimal subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getTtzGoodsId() {
        return ttzGoodsId;
    }

    public void setTtzGoodsId(Integer ttzGoodsId) {
        this.ttzGoodsId = ttzGoodsId;
    }
}