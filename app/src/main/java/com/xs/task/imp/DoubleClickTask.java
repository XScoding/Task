package com.xs.task.imp;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.xs.task.api.Callback;
import com.xs.task.api.Sequence;
import com.xs.task.api.Task;

public class DoubleClickTask implements Task<View,View> {

    private long lastTime = 0;

    private int id = 0;

    @Override
    public void run(Activity activity, Sequence sequence, View view, Callback<View> callBack) {
        int viewId = view.getId();
        long time = System.currentTimeMillis();
        if (viewId != id || time - lastTime > 500) {
            callBack.success(view);
        } else {
            callBack.fail(-1,"double click time:" + (time - lastTime) + " view id:" + viewId);
        }
        this.id = viewId;
        this.lastTime = time;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void cancel() {

    }
}
