package com.example.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.e("FFF", "MainActivity3 started，TaskID---->" + getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("FFF", "MainActivity3---->onDestroy");
    }

    public void ac3(View view) {

        Intent intent1 = new Intent(this, MainActivity4.class);

        startActivity(intent1);
    }
}
