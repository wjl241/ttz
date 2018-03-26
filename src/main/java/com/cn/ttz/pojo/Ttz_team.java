package com.cn.ttz.pojo;

public class Ttz_team {
    private Integer id;

    private Integer captain;

    private String teamMember;

    private Byte number;

    private Byte type;

    private Byte status;

    private Integer ttzGoodsId;

    private Integer captainOrderId;

    private String memberOrderId;

    private Integer createTime;

    private Integer updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaptain() {
        return captain;
    }

    public void setCaptain(Integer captain) {
        this.captain = captain;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember == null ? null : teamMember.trim();
    }

    public Byte getNumber() {
        return number;
    }

    public void setNumber(Byte number) {
        this.number = number;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getTtzGoodsId() {
        return ttzGoodsId;
    }

    public void setTtzGoodsId(Integer ttzGoodsId) {
        this.ttzGoodsId = ttzGoodsId;
    }

    public Integer getCaptainOrderId() {
        return captainOrderId;
    }

    public void setCaptainOrderId(Integer captainOrderId) {
        this.captainOrderId = captainOrderId;
    }

    public String getMemberOrderId() {
        return memberOrderId;
    }

    public void setMemberOrderId(String memberOrderId) {
        this.memberOrderId = memberOrderId == null ? null : memberOrderId.trim();
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
}