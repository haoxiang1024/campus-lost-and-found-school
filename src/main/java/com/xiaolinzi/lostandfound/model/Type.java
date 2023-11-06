package com.xiaolinzi.lostandfound.model;
/**
 * 失物招领物品分类(带信息数量)
 */
public class Type {

    /**
     * 主键ID
     */
    private int id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 该分类下的招领信息数量
     */
    private int foundCount;

    /**
     * 该分类下的失物信息数量
     */
    private int lostCount;

    /**
     * 该分类下的信息数量
     */
    private int sumCount;

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", foundCount=" + foundCount +
                ", lostCount=" + lostCount +
                ", sumCount=" + sumCount +
                '}';
    }

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

    public int getFoundCount() {
        return foundCount;
    }

    public void setFoundCount(int foundCount) {
        this.foundCount = foundCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public void setLostCount(int lostCount) {
        this.lostCount = lostCount;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }
}
