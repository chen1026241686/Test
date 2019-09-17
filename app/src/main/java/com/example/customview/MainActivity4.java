package com.example.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Log.e("FFF", "MainActivity4 started,taskID---->" + getTaskId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("FFF", "MainActivity4---->onDestroy");
    }

    public void bbb(View view) {


        Intent intent1 = new Intent(this, MainActivity2.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

    }
}
