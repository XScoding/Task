package com.xs.task.api;

/**
 * callback
 * Created by xs on 2019/1/10.
 */

public interface Callback<C> {

    /**
     * success
     * @param c
     */
    void success(C c);

    /**
     * fail
     * @param code
     * @param message
     */
    void fail(int code,String message);
}
