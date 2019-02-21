package com.xs.task;

import android.content.Intent;

import com.xs.task.api.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAndControl {

    /**
     * task list
     */
    private List<Task> tasks = new ArrayList<>();

    /**
     * control list
     */
    private List<BaseControl> controls = new ArrayList<>();

    /**
     * add task
     * @param task
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * add control
     * @param control
     */
    public void add(BaseControl control) {
        controls.add(control);
    }

    /**
     * activity for result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Task task : tasks) {
            task.onActivityResult(requestCode,resultCode,data);
        }
        for (BaseControl control : controls) {
            control.onActivityResult(requestCode,resultCode,data);
        }
    }

    /**
     * activity destroy
     */
    void cancel(){
        int taskSize = tasks.size();
        for (int i = 0; i < taskSize; i++) {
            tasks.remove(0).cancel();
        }
        int controlSize = controls.size();
        for (int i = 0; i < controlSize; i++ ) {
            controls.remove(0).cancel();
        }
    }
}
