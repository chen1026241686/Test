package com.example.customview;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

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
