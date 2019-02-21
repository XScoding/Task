package com.xs.task;

import android.app.Application;

import com.xs.task.imp.ThirdReplaceTask;
import com.xs.task.imp.ThirdTask;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TaskManager.getInstance().init(R.xml.tasks_list);
        TaskManager.getInstance().addTask(this,"third", ThirdTask.class);
        TaskManager.getInstance().addTask(this,"thirdReplace", ThirdReplaceTask.class);
    }
}
