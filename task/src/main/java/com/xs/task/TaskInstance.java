package com.xs.task;

import com.xs.task.api.Task;

/**
 * 实例化 task
 *
 * Created by xs on 2019/1/24.
 */

public class TaskInstance {

    private String key;

    private Task task;

    private String input;

    private String output;

    public TaskInstance(String key, Task task, String input, String output) {
        this.key = key;
        this.task = task;
        this.input = input;
        this.output = output;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
