package com.xs.task.api;

/**
 * task执行顺序
 * 执行顺序从0开始，每执行一项将其task删除
 * Created by xs on 2019/1/14.
 */

public interface Sequence {

    /**
     * 执行task
     *
     * @param index
     * @return
     */
    Task run(int index);

    /**
     * 判断结束
     *
     * @return
     */
    boolean over();

    /**
     * 获取执行task顺序
     *
     * @return
     */
    String[] getRunTasks();

    /**
     * 提交新执行顺序
     *
     * @param runTasks
     * @return
     */
    boolean submit(String[] runTasks);

    /**
     * task change session
     *
     * @return
     */
    Session createSession();
}
