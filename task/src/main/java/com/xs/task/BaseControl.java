package com.xs.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xs.task.api.Callback;
import com.xs.task.api.Control;
import com.xs.task.api.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xs on 2019/1/11.
 */

public abstract class BaseControl<T,C> implements Control<T,C> {

    /**
     * 执行流程顺序
     */
    private TaskSequence tasks;

    /**
     * 正在执行的task
     */
    private Task runningTask;


    @Override
    public void control(Activity activity, T t, Callback<C> callBack) {
        if (activity == null) {
            throw new IllegalArgumentException("activity is null");
        }
        if (callBack == null) {
            throw new IllegalArgumentException("callback is null");
        }
        String[] initKeys = initKeys();
        if (initKeys == null && initKeys.length == 0) {
            throw new IllegalArgumentException("keys is null");
        }
        List<TaskInstance> taskInstances = initInstance(activity.getApplicationContext(), initKeys);
        if (taskInstances.size() != initKeys.length)
            throw new IllegalStateException("init task is error");

        String callBackClassName = TaskManager.getInstance().getT(callBack, 0);

        if (!TaskManager.getInstance().checkTask(taskInstances,t.getClass().getName(),callBackClassName)) {
            throw new IllegalStateException("please check tasks squence");
        }
        tasks = new TaskSequence(initKeys, callBackClassName,
                taskInstances, TaskManager.getInstance().getAllTask(activity));
        runTask(tasks.run(0), activity, t, callBack);
    }

    /**
     * 实例task
     *
     * @param context
     * @param initKeys
     * @return
     */
    private List<TaskInstance> initInstance(Context context,String[] initKeys) {
        List<TaskInstance> taskInstances = new ArrayList<>();
        for (String s : initKeys) {
            Task task = TaskManager.getInstance().getTask(context, s);
            if (task == null) {
                throw new IllegalArgumentException("task instance fail, key : " + s);
            }
            taskInstances.add(new TaskInstance(s,task,
                    TaskManager.getInstance().getT(task,0),
                    TaskManager.getInstance().getT(task,1)));
        }
        return taskInstances;
    }


    /**
     * @param activity
     * @param e
     * @param callback
     */
    private <E>void runTask(Task task, final Activity activity, E e, final Callback<C> callback) {
        //获取key所对应的task
        runningTask = task;
        Callback taskCallback = new Callback<C>() {
            @Override
            public void success(C c) {
                cancelTask();
                if (tasks.over()) {  //流程结束
                    callback.success(c);
                } else { //执行一下步
                    runTask(tasks.run(0), activity, c, callback);
                }
            }

            @Override
            public void fail(int code, String message) {
                cancelTask();
                callback.fail(code, message);
            }
        };
        runningTask.run(activity, tasks, e, taskCallback);
    }

    @Override
    public void cancel() {
        cancelTask();
    }

    /**
     * 释放当前task资源
     */
    private void cancelTask() {
        if (runningTask != null) {
            runningTask.cancel();
            runningTask = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (runningTask != null) {
            runningTask.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 流程顺序
     *
     * @return
     */
    protected abstract String[] initKeys();
}
