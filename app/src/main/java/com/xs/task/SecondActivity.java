package com.xs.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xs.task.api.Callback;
import com.xs.task.imp.DoubleClickTask;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    private DoubleClickTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(this);
        Button result = (Button) findViewById(R.id.result);
        result.setOnClickListener(this);
        task = new DoubleClickTask();
    }


    @Override
    public void onClick(View view) {
        task.run(this, null, view, new Callback<View>() {
            @Override
            public void success(View view) {
                switch (view.getId()) {
                    case R.id.finish:
                        finish();
                        break;
                    case R.id.result:
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
            }

            @Override
            public void fail(int code, String message) {

            }
        });
    }
}
