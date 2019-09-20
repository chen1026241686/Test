package com.example.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.aidl.IMyAidlInterface;
import com.example.customview.http.HttpUtils;
import com.example.customview.listview.AdapterViewHolder;
import com.example.customview.listview.RecyclerAdapter;
import com.example.customview.view.TouchView;
import com.example.customview.viewgroup.FlowLayout;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION;

/**
 * 我得儿意的笑
 */
public class MainActivity extends AutoLayoutActivity {


    private ImageView imageaasd;

    Drawable drawable;
    Bitmap bitmap;
    BitmapFactory bitmapFactory;
    Button btn;
    ImageButton imgBtn;

    LinearLayout linearLayout;

    ViewGroup viewGroup;

    Matrix matrix;
    private View mContentView;
    private String ab;
    private TouchView touchview;

    private FlowLayout flowLayout;

    private TextView tv;


    IMyAidlInterface mIRemoteService;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            mIRemoteService = IMyAidlInterface.Stub.asInterface(service);

            try {
                mIRemoteService.justPrintMessage("5566");
            } catch (Exception e) {
                Log.e("FFF", "exception=" + e.toString());
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            mIRemoteService = null;
        }
    };


    View animation;
    private DialogRecordFragment mXBottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        DaggerMainActivityComponent.create("123").inject(this);
//        Log.e("FFF", "Inject onCreate=" + pot.show());
//        Log.e("FFF", "Inject onCreate=" + pot.roseShow());

        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mContentView);

        touchview = findViewById(R.id.touchview);

        flowLayout = findViewById(R.id.flowLayout);

        List<String> maps = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            maps.add(i + "F");
        }

        flowLayout.setFlowLayout(maps, null);

        HandlerThread handlerThread = new HandlerThread("MainActivity");
        handlerThread.start();


        Intent intent = new Intent(this, MyService.class);
        this.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);

        animation = findViewById(R.id.animation);


        listView();


        handler = new MusicPlayerHandler(this, Looper.getMainLooper());

        AndPermission.with(this).runtime().permission(new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.RECORD_AUDIO}).start();
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            Log.e("FFF", "MyRunnable--------------");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    private MusicPlayerHandler handler;

    private static final class MusicPlayerHandler extends Handler {
        private final WeakReference<MainActivity> mService;


        public MusicPlayerHandler(final MainActivity service, final Looper looper) {
            super(looper);
            mService = new WeakReference<>(service);
        }


        @Override
        public void handleMessage(final Message msg) {
            final MainActivity service = mService.get();
            if (service == null) {
                return;
            }

            synchronized (service) {
                switch (msg.what) {
                    default:
                        break;
                }
            }
        }
    }

    ObjectAnimator inObjectAnimator, outObjectAnimator, alphaAnimator;

    AnimatorSet set = new AnimatorSet();


    public void aaaa(View view) {


        if (mXBottomSheetDialogFragment == null) {
            mXBottomSheetDialogFragment = new DialogRecordFragment();
        }
        mXBottomSheetDialogFragment.show(getSupportFragmentManager(), "Dialog");


        boolean a = true;
        if (a)
            return;


        if (outObjectAnimator != null && outObjectAnimator.isRunning()) {
            outObjectAnimator.cancel();
        }

        if (inObjectAnimator == null) {
            inObjectAnimator = ObjectAnimator.ofFloat(animation, "translationY", -animation.getHeight(), 0);
            inObjectAnimator.setDuration(500);
            inObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        if (alphaAnimator == null) {
            alphaAnimator = ObjectAnimator.ofFloat(animation, "alpha", 0, 1);
            alphaAnimator.setDuration(500);
        }
        set.playTogether(inObjectAnimator, alphaAnimator);
    }

    public void bbbb(View view) {

        if (inObjectAnimator != null && inObjectAnimator.isRunning()) {
            inObjectAnimator.cancel();
        }

        if (outObjectAnimator == null) {
            outObjectAnimator = ObjectAnimator.ofFloat(animation, "translationY", 0, -animation.getHeight());
            outObjectAnimator.setDuration(1500);
            outObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        outObjectAnimator.start();
    }

    private void listView() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 109; i++) {
            data.add("I am " + i);
        }
        RecyclerView recyclerView = findViewById(R.id.listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerAdapter<String>(this, data, R.layout.item_view) {
            @Override
            public AdapterViewHolder onCreateViewHolder(View itemView) {
                return new AdapterViewHolder(itemView) {
                    @Override
                    public void bindView(Object o) {
                        TextView name = this.itemView.findViewById(R.id.name);
                        name.setText(o.toString());
                    }
                };
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


//        multiplyRetrofit(null);

//        int[] location = new int[2];
//        mContentView.getLocationOnScreen(location);
//
//
//        Log.e("FFF", "location[0]=" + location[0]);
//        Log.e("FFF", "location[1]=" + location[1]);
//
//        final Rect displayFrame = new Rect();
//        View appRootView = mContentView.getRootView();
//        appRootView.getWindowVisibleDisplayFrame(displayFrame);
//
//        Log.e("FFF", String.format("left:%s,top:%s,right:%s,bottom:%s", displayFrame.left, displayFrame.top, displayFrame.right, displayFrame.bottom));
//
//
//        ab = "";

    }


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

        WindowManager wManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        wManager.addView(btn, layoutParams);
        HashMap map;
    }

    private void multiplyRetrofit(@NonNull String abcd) {
        HttpUtils.requestApi("https://api.jisuapi.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(mConnection);
        Log.e("FFF", "MainActivity1---->onDestroy");
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("FFF", "MainActivity-->dispatchTouchEvent-->ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("FFF", "MainActivity-->dispatchTouchEvent-->ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("FFF", "MainActivity-->dispatchTouchEvent-->ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("FFF", "MainActivity-->dispatchTouchEvent-->ACTION_UP");
//                break;
//            default:
//                Log.e("FFF", "MainActivity-->dispatchTouchEvent-->default");
//                break;
//        }
////        return true;
//        return super.dispatchTouchEvent(ev);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("FFF", "MainActivity-->onTouchEvent-->ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("FFF", "MainActivity-->onTouchEvent-->ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("FFF", "MainActivity-->onTouchEvent-->ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("FFF", "MainActivity-->onTouchEvent-->ACTION_UP");
//                break;
//            default:
//                Log.e("FFF", "MainActivity-->onTouchEvent-->default");
//                break;
//        }
//        return super.onTouchEvent(event);
//    }


}
