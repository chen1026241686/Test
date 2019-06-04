package com.example.customview;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.customview.fragment.SingleFragment;
import com.example.customview.http.HttpUtils;
import com.example.customview.interfaces.TestProxy;
import com.example.customview.interfaces.TestProxy1;
import com.example.customview.popwindow.FanclityTypePopWindow;
import com.example.customview.view.RadarAnimationView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION;

/**
 * 我在master
 */
/**
 * 我在developer
 */
public class MainActivity extends AppCompatActivity {


    private ImageView image;

    Drawable drawable;
    Bitmap bitmap;
    BitmapFactory bitmapFactory;
    Button btn;
    ImageButton imgBtn;

    LinearLayout linearLayout;

    ViewGroup viewGroup;

    Matrix matrix;
    private View mContentView;

    class TextProxyReal implements TestProxy, TestProxy1 {


        public void myRealTest() {
            Log.e("FFF", "myRealTest");
        }

        @Override
        public void aa() {

        }

        @Override
        public String bb(String args, String bb) {
            return 45 + args + bb;
        }

        @Override
        public void cc() {

        }

        @Override
        public String dd(String args, String bb) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mContentView);

        btn = findViewById(R.id.aa);

        TextProxyReal real = new TextProxyReal();
        TestProxy bb = (TestProxy) Proxy.newProxyInstance(getClassLoader(), real.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.e("FFF", "method=" + method);
                if (args != null && args.length > 0) {
                    for (int i = 0; i < args.length; i++) {
                        Log.e("FFF", "args[" + i + "]=" + args[i]);
                    }
                }

                return method.invoke(real, args);
            }
        });
        Log.e("FFF", "bb.bb()=" + bb.bb("参数", "hehe"));

        Log.e("FFF", "isProxyClass=" + Proxy.isProxyClass(bb.getClass()));
        Log.e("FFF", "isProxyClass=" + Proxy.isProxyClass(real.getClass()));

//        int[] location = new int[2];
//        mContentView.getRootView().getLocationOnScreen(location);
//
//
//        Log.e("FFF", "location[0]=" + location[0]);
//        Log.e("FFF", "location[1]=" + location[1]);

//        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();


        int[] location = new int[2];
        mContentView.getLocationOnScreen(location);


        Log.e("FFF", "location[0]=" + location[0]);
        Log.e("FFF", "location[1]=" + location[1]);

        final Rect displayFrame = new Rect();
        View appRootView = btn.getRootView();
        appRootView.getWindowVisibleDisplayFrame(displayFrame);

        Log.e("FFF", String.format("left:%s,top:%s,right:%s,bottom:%s", displayFrame.left, displayFrame.top, displayFrame.right, displayFrame.bottom));

    }


    //    private void initViews() {
//        final RadarAnimationView radarAnimationView =
//                (RadarAnimationView) findViewById(R.id.radar_animation_view);
//        radarAnimationView.setNumberOfItemsToDiscover(7);
//        radarAnimationView.setCounterTextSizeDp(40);
//        radarAnimationView.beginAnimation(new Animator.AnimatorListener() {
//            @Override public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override public void onAnimationEnd(Animator animator) {
//                Toast.makeText(MainActivity.this, "Discovered 7 items!", Toast.LENGTH_SHORT).show();
//                radarAnimationView.beginAnimation(this);
//            }
//
//            @Override public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//    }


    public void aa(View view) {
//        image = findViewById(R.id.cat);
//
//        BitmapFactory.Options options1 = new BitmapFactory.Options();
//        options1.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap cat1 = BitmapFactory.decodeResource(getResources(), R.drawable.cat, options1);
//
//
//        image.setImageDrawable(new BitmapDrawable(getResources(), cat1));
//
//
//        Log.e("FFF", "cat1=" + cat1.getWidth() + "," + cat1.getHeight());
//
//        Drawable drawable = getDrawable(R.drawable.cat);
//
//        Log.e("FFF", "drawable=" + drawable.getIntrinsicWidth() + "," + drawable.getIntrinsicHeight());


//        Bitmap cat2=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat3=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat4=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat5=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat6=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat7=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat8=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat9=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat10=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat11=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat12=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat13=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat14=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat15=BitmapFactory.decodeResource(getResources(),R.drawable.cat);
//        Bitmap cat16=BitmapFactory.decodeResource(getResources(),R.drawable.cat);


        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(150, 150, 100, 200, TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.OPAQUE);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(150, 150, 500, 500, TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, PixelFormat.OPAQUE);


        FloatingActionButton btn = new FloatingActionButton(this);

        WindowManager wManager = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);

        wManager.addView(btn, layoutParams);

    }

    private void multiplyRetrofit() {
        HttpUtils.requestApi("https://api.jisuapi.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
