package com.example.customview;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

/**
 * @author ChenYasheng
 * @date 2019/11/15
 * @Description
 */
public class BaseActivity extends AppCompatActivity {


    public void showSnackBar() {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "断开", Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        layoutParams.height = 100;
        layoutParams.width = 200;
        layoutParams.gravity = Gravity.CENTER;
        snackbar.show();
    }

}
