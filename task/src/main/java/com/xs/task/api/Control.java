package com.xs.task.api;

import android.app.Activity;
import android.content.Intent;

/**
 * 控制流程
 * Created by xs on 2019/1/10.
 */

public interface Control<T,C> {

    /**
     * start
     * @param activity
     * @param t
     * @param callBack
     */
    void control(Activity activity, T t,Callback<C> callBack);

    /**
     * activity for result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * activity destroy
     */
    void cancel();
}
