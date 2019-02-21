package com.xs.task;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xs.task.api.Callback;
import com.xs.task.bean.First;
import com.xs.task.bean.Third;
import com.xs.task.imp.DoubleClickTask;
import com.xs.task.imp.FirstTask;
import com.xs.task.imp.TaskControl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TaskControl control;
    private FirstTask task;
    private DoubleClickTask doubleClickTask;
    private TaskAndControl tac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new TaskControl();
        task = new FirstTask();
        doubleClickTask = new DoubleClickTask();
        tac = new TaskAndControl();
        tac.add(control);
        tac.add(task);
        tac.add(doubleClickTask);

        Button taskButton = (Button) findViewById(R.id.task);
        taskButton.setOnClickListener(this);
        Button controlButtoon = (Button) findViewById(R.id.control);
        controlButtoon.setOnClickListener(this);
    }

    private void testControl() {
        control.control(this, "test control", new Callback<Third>() {
            @Override
            public void success(Third third) {
                Log.w("xssss","msg:" + third.toString());
            }

            @Override
            public void fail(int code, String message) {

            }
        });
    }

    private void testTask() {
        task.run(this, null, "test task", new Callback<First>() {
            @Override
            public void success(First first) {
                Log.w("xssss","msg:" + first.toString());
            }

            @Override
            public void fail(int code, String message) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tac.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tac.cancel();
    }

    @Override
    public void onClick(View view) {
        doubleClickTask.run(this, null, view, new Callback<View>() {
            @Override
            public void success(View view) {
                switch (view.getId()) {
                    case R.id.task:
                        testTask();
                        break;
                    case R.id.control:
                        testControl();
                        break;
                }
            }

            @Override
            public void fail(int code, String message) {
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}
