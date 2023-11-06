package com.xiaolinzi.lostandfound.model;

/**
 * 失物招领物品分类
 */
public class LostFoundType {

    /**
     * 主键ID
     */
    private int id;
    /**
     * 分类名称
     */
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LostFoundType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
