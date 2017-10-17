package com.cn.ttz.pojo;

public class Ttz_pid {
    private Integer id;

    private Byte pidType;

    private Byte status;

    private Byte isdelete;

    private Integer createTime;

    private Integer updateTime;

    private String pidName;

    private String pidHash;

    private String pid;

    private String appkey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getPidType() {
        return pidType;
    }

    public void setPidType(Byte pidType) {
        this.pidType = pidType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Byte isdelete) {
        this.isdelete = isdelete;
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

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName == null ? null : pidName.trim();
    }

    public String getPidHash() {
        return pidHash;
    }

    public void setPidHash(String pidHash) {
        this.pidHash = pidHash == null ? null : pidHash.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey == null ? null : appkey.trim();
    }
}