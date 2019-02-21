package com.xs.task.api;

/**
 * Created by xs on 2019/1/19.
 */

public interface Session {

    /**
     * 执行顺序尾添加task
     * @param task
     * @return
     */
    boolean add(String task);

    /**
     * 执行顺序添加task
     * @param task
     * @param index
     */
    void add(String task,int index);

    /**
     * 替换task
     * @param task
     * @param index
     */
    void change(String task,int index);

    /**
     * 替换task
     * @param newTask
     * @param oldTask
     */
    void change(String newTask,String oldTask);

    /**
     * 替换所有task
     * @param tasks
     */
    boolean replace(String[] tasks);

    /**
     * 删除task
     * @param index
     * @return
     */
    String delete(int index);

    /**
     * 删除task
     * @param task
     * @return
     */
    boolean delete(String task);


    /**
     * 查询task
     * @param index
     * @return
     */
    String get(int index);

    /**
     * 获取位置
     * @param task
     * @return
     */
    int indexOf(String task);

    /**
     * 获取位置
     * @param task
     * @return
     */
    int lastIndexOf(String task);

    /**
     * 数据
     * @return
     */
    int size();

    /**
     * 提交
     *
     * @return
     */
    boolean commit();
}
