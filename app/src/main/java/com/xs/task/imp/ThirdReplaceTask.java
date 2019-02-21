package com.xs.task.imp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.xs.task.api.Callback;
import com.xs.task.api.Sequence;
import com.xs.task.api.Task;
import com.xs.task.bean.Second;
import com.xs.task.bean.Third;

public class ThirdReplaceTask implements Task<Second,Third> {
    @Override
    public void run(Activity activity, Sequence sequence, Second second, Callback<Third> callBack) {
        Log.w("xssss", "ThirdReplaceTask msg:" + second.toString());
        callBack.success(new Third(false));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void cancel() {

    }
}
