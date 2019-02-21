package com.xs.task.imp;

import com.xs.task.BaseControl;
import com.xs.task.bean.Third;

public class TaskControl extends BaseControl<String,Third> {
    @Override
    protected String[] initKeys() {
        return new String[]{"first","second","third"};
    }
}
