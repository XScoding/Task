package com.xs.task.imp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.xs.task.SecondActivity;
import com.xs.task.api.Callback;
import com.xs.task.api.Sequence;
import com.xs.task.api.Session;
import com.xs.task.api.Task;
import com.xs.task.bean.First;
import com.xs.task.bean.Second;

public class SecondTask implements Task<First, Second> {
    private Callback<Second> callBack;

    @Override
    public void run(Activity activity, Sequence sequence, First first, Callback<Second> callBack) {
        Log.w("xssss", "msg:" + first.toString());
        this.callBack = callBack;

        //更改task list
        Session session = sequence.createSession();
        session.change("thirdReplace","third");
        session.commit();

        activity.startActivityForResult(new Intent(activity, SecondActivity.class), 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("xssss", "onActivityResult requestCode:" + requestCode + " resultCode:" + resultCode);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            callBack.success(new Second(18));
        }
    }

    @Override
    public void cancel() {

    }
}
