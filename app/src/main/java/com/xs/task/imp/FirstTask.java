package com.xs.task.imp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.xs.task.api.Callback;
import com.xs.task.api.Sequence;
import com.xs.task.api.Task;
import com.xs.task.bean.First;

public class FirstTask implements Task<String,First> {
    @Override
    public void run(Activity activity, Sequence sequence, String s, Callback<First> callBack) {
        Log.w("xssss","msg:" + s);
        callBack.success(new First("first"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void cancel() {

    }
}
