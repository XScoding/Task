package com.xs.task;

/**
 * 注册 task
 *
 * Created by xs on 2019/1/19.
 */

public class TaskInfo {

    private String key;

    private String clz;

    public TaskInfo(){}

    public TaskInfo(String key, String clz) {
        this.key = key;
        this.clz = clz;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

}
