package com.xiaolinzi.lostandfound.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 招领表
 */
public class Found {
    /**
     * 主键id
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图
     */
    private String img;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pubDate;
    /**
     * 内容
     */
    private String content;
    /**
     * 地点
     */
    private String place;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 状态
     */
    private String state;
    /**
     * 是否置顶（0：否，1：是）
     */
    private Boolean stick;
    /**
     * 点击量
     */
    private int clickCount;

    /**
     * 招领物品的类型
     */
    private LostFoundType lostFoundType;
    /**
     * 发布人
     */
    private User user;

    @Override
    public String toString() {
        return "Found{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", pubDate=" + pubDate +
                ", content='" + content + '\'' +
                ", place='" + place + '\'' +
                ", phone='" + phone + '\'' +
                ", state='" + state + '\'' +
                ", stick=" + stick +
                ", clickCount=" + clickCount +
                ", lostFoundType=" + lostFoundType +
                ", user=" + user +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getStick() {
        return stick;
    }

    public void setStick(Boolean stick) {
        this.stick = stick;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public LostFoundType getLostFoundType() {
        return lostFoundType;
    }

    public void setLostFoundType(LostFoundType lostFoundType) {
        this.lostFoundType = lostFoundType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
