package com.xs.task;

import android.text.TextUtils;

import com.xs.task.api.Sequence;
import com.xs.task.api.Session;
import com.xs.task.api.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xs on 2019/1/14.
 */

public class TaskSequence implements Sequence {

    private List<String> keys = new ArrayList<>();

    private List<TaskInstance> taskInstances = new ArrayList<>();

    private Map<String, TaskInfo> allTask;

    private String callBackClassName;

    private String currentClassName;

    public TaskSequence(String[] keys, String callBackClassName, List<TaskInstance>
            tasks, Map<String, TaskInfo> allTask) {
        if (keys != null) {
            this.keys.addAll(Arrays.asList(keys));
        }
        if (allTask != null) {
            this.allTask = allTask;
        }
        this.callBackClassName = callBackClassName;
        this.taskInstances.addAll(tasks);
    }

    @Override
    public Task run(int index) {
        if (index > keys.size() || index < 0)
            throw new IndexOutOfBoundsException(error(index));
        if (keys.size() != taskInstances.size())
            throw new IllegalStateException("keys size != task size");
        String key = keys.remove(0);
        TaskInstance task = taskInstances.remove(0);
        if (TextUtils.equals(key, task.getKey())) {
            currentClassName = task.getOutput();
            return task.getTask();
        }
        return null;
    }

    @Override
    public boolean over() {
        return keys.size() == 0;
    }

    @Override
    public String[] getRunTasks() {
        String[] runTasks = new String[keys.size()];
        keys.toArray(runTasks);
        return runTasks;
    }

    @Override
    public boolean submit(String[] runTasks) {
        if (runTasks == null || runTasks.length == 0) {
            if (TextUtils.equals(currentClassName, callBackClassName)) {
                keys.clear();
                taskInstances.clear();
                return true;
            } else {
                return false;
            }
        } else {
            Set<String> keySet = allTask.keySet();
            for (String key : runTasks) {
                if (keySet.contains(key)) {
                    continue;
                } else {
                    return false;
                }
            }
            List<TaskInstance> newTaskInstances = new ArrayList<>();
            for (String runTask : runTasks) {
                int i = keys.indexOf(runTask);
                if (i >= 0) {
                    newTaskInstances.add(taskInstances.get(i));
                } else {
                    Task task = TaskManager.getInstance().getTask(null, runTask);
                    if (task == null) {
                        return false;
                    }
                    newTaskInstances.add(new TaskInstance(runTask, task,
                            TaskManager.getInstance().getT(task, 0),
                            TaskManager.getInstance().getT(task, 1)));
                }
            }
            if (!TaskManager.getInstance().checkTask(newTaskInstances,currentClassName,callBackClassName)) {
                return false;
            }
            keys.clear();
            keys.addAll(Arrays.asList(runTasks));
            taskInstances.clear();
            taskInstances.addAll(newTaskInstances);
            return true;

        }

    }


    private String error(int index) {
        return "Index: " + index + ", Size: " + keys.size();
    }


    @Override
    public Session createSession() {
        return new TaskSession(this);
    }

}
