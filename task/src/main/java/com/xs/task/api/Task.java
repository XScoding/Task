package com.xs.task.api;

import android.app.Activity;
import android.content.Intent;

/**
 * task
 * Created by xs on 2019/1/10.
 */

public interface Task<T,C> {

    /**
     * run task
     * @param activity
     * @param t         input data
     * @param callBack      callback must be success or fail
     */
    void run(Activity activity, Sequence sequence, T t,Callback<C> callBack);

    /**
     * activity for reuslt
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * release resource
     */
    void cancel();
}
