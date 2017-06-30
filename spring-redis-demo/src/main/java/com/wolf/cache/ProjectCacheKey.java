package com.wolf.cache;

/**
 * 项目redis缓存key基础类，目的为了统一项目前缀
 */
public abstract class ProjectCacheKey extends ICacheKey {

    private static final String PROJECT = "zhenailive";

    /**
     * 取类的前一级包名作为模块前缀，若前一级名字为cachekey，则取前二级包名
     *
     * @return
     */
    @Override
    public String getPrefixKey() {
        String[] packages = this.getClass().getPackage().getName().split("\\.");
        int len = packages.length;
        if (len >= 2) {
            return packages[len - 1].equals("cachekey") ? packages[len - 2] : packages[len - 1];
        }
        return "common";
    }


    @Override
    public String toString() {
        return PROJECT + ":" + super.toString();
    }
}
