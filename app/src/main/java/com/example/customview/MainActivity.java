package com.example.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.aidl.IMyAidlInterface;
import com.example.customview.http.HttpUtils;
import com.example.customview.listview.AdapterViewHolder;
import com.example.customview.listview.RecyclerAdapter;
import com.example.customview.view.TouchView;
import com.example.customview.viewgroup.FlowLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pujitech.atsmarthome.CallType;
import com.pujitech.atsmarthome.MainActivitySS;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION;

/**
 * 我得儿意的笑
 */
public class MainActivity extends AutoLayoutActivity {


    Activity activity;

    ActivityManager activityManager;

    MediaPlayer mediaPlayer;

    DexClassLoader dexClassLoader;
    PathClassLoader pathClassLoader;

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

    private String tag = "FFF";

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

    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //TestReset

    View animation;
    private DialogRecordFragment mXBottomSheetDialogFragment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(mContentView);

        touchview = findViewById(R.id.touchview);

        flowLayout = findViewById(R.id.flowLayout);

//        List<String> maps = new ArrayList<>();
//        for (int i = 0; i < 14; i++) {
//            maps.add(i + "F");
//        }
//
//        flowLayout.setFlowLayout(maps, null);


        Intent intent = new Intent(this, MyService.class);
        startService(intent);

        animation = findViewById(R.id.animation);


//        listView();


//        AndPermission.with(this).runtime().permission(new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.RECORD_AUDIO}).start();


//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_4444;

//        Bitmap mdpi = BitmapFactory.decodeResource(getResources(), R.drawable.aaa, options);
//        Bitmap hdpi = BitmapFactory.decodeResource(getResources(), R.drawable.bbb);
//        Bitmap xhdpi = BitmapFactory.decodeResource(getResources(), R.drawable.ccc);
//        Bitmap xxhdpi = BitmapFactory.decodeResource(getResources(), R.drawable.ddd);
//        Bitmap xxxhdpi = BitmapFactory.decodeResource(getResources(), R.drawable.eee);
//
//        Log.e("FFF", "mdpi------>" + mdpi.getWidth());
//        Log.e("FFF", "hdpi------>" + hdpi.getWidth());
//        Log.e("FFF", "xhdpi----->" + xhdpi.getWidth());
//        Log.e("FFF", "xxhdpi---->" + xxhdpi.getWidth());
//        Log.e("FFF", "xxxhdpi--->" + xxxhdpi.getWidth());
//
//
//        Log.e("FFF", "getAllocationByteCount--->" + mdpi.getAllocationByteCount());
//        Log.e("FFF", "getByteCount------------->" + mdpi.getByteCount());
//        Log.e("FFF", "getRowBytes-------------->" + mdpi.getRowBytes());
//        Log.e("FFF", "getRowBytes*height------->" + mdpi.getRowBytes() * mdpi.getHeight());
//        Log.e("FFF", "getWidth------->" + mdpi.getWidth() + "  getHeight----->" + mdpi.getHeight());
//        Log.e("FFF", "width*height*4----------->" + mdpi.getWidth() * mdpi.getHeight() * 4);


//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.baidu.com/").build();
//        retrofit.create(Api.class).requestApi("aa", "bb",12);

//        Class clz = Api.class;
//        try {
//            Method method = clz.getMethod("requestApi", String.class, String.class, int.class);
//
//            for (Annotation annotation : method.getAnnotations()) {
//                parseMethodAnnotation(annotation);
//            }
//
//            Type[] parameterTypes = method.getGenericParameterTypes();
//            for (int i = 0; i < parameterTypes.length; i++) {
//                Log.e("FFF", parameterTypes[i].toString());
//            }
//
//            Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();
//            for (int i = 0; i < parameterAnnotationsArray.length; i++) {
//                Log.e("FFF", "Out---------->" + parameterAnnotationsArray[i].toString());
//                for (int i1 = 0; i1 < parameterAnnotationsArray[i].length; i1++) {
//                    Log.e("FFF", "In---------->" + parameterAnnotationsArray[i][i1].toString());
//                }
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }


    }

    private void parseMethodAnnotation(Annotation annotation) {
        if (annotation instanceof DELETE) {
            parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
        } else if (annotation instanceof GET) {
            parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
        } else if (annotation instanceof HEAD) {
            parseHttpMethodAndPath("HEAD", ((HEAD) annotation).value(), false);
        } else if (annotation instanceof PATCH) {
            parseHttpMethodAndPath("PATCH", ((PATCH) annotation).value(), true);
        } else if (annotation instanceof POST) {
            parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
        } else if (annotation instanceof PUT) {
            parseHttpMethodAndPath("PUT", ((PUT) annotation).value(), true);
        } else if (annotation instanceof OPTIONS) {
            parseHttpMethodAndPath("OPTIONS", ((OPTIONS) annotation).value(), false);
        } else if (annotation instanceof HTTP) {
            HTTP http = (HTTP) annotation;
            parseHttpMethodAndPath(http.method(), http.path(), http.hasBody());
        }
    }

    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");
    private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);

    private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {


        // Get the relative URL path and existing query string, if present.
        int question = value.indexOf('?');
        if (question != -1 && question < value.length() - 1) {
            // Ensure the query string does not have any named parameters.
            String queryParams = value.substring(question + 1);
            Matcher queryParamMatcher = PARAM_URL_REGEX.matcher(queryParams);
            if (queryParamMatcher.find()) {
                Log.e(tag, "URL query string must not have replace block.  For dynamic query parameters use @Query.");
            }
        }

        Log.e(tag, "value--->" + value);
        Log.e(tag, "relativeUrlParamNames--->" + parsePathParameters(value));
    }

    static Set<String> parsePathParameters(String path) {
        Matcher m = PARAM_URL_REGEX.matcher(path);
        Set<String> patterns = new LinkedHashSet<>();
        while (m.find()) {
            patterns.add(m.group(1));
        }
        return patterns;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("FFF", "onStop----");
    }


    ObjectAnimator inObjectAnimator, outObjectAnimator, alphaAnimator;

    AnimatorSet set = new AnimatorSet();


    public void animation(View view) {

        Intent intent=new Intent(this, MainActivitySS.class);
        intent.putExtra("aa", CallType.EMERGENCY);
        startActivity(intent);
    }

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
        Log.e("FFF", "onresume");

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
//        this.unbindService(mConnection);
//        Log.e("FFF", "MainActivity1---->onDestroy");
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("FFF", "MainActivitySS-->dispatchTouchEvent-->ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("FFF", "MainActivitySS-->dispatchTouchEvent-->ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("FFF", "MainActivitySS-->dispatchTouchEvent-->ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("FFF", "MainActivitySS-->dispatchTouchEvent-->ACTION_UP");
//                break;
//            default:
//                Log.e("FFF", "MainActivitySS-->dispatchTouchEvent-->default");
//                break;
//        }
////        return true;
//        return super.dispatchTouchEvent(ev);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("FFF", "MainActivitySS-->onTouchEvent-->ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.e("FFF", "MainActivitySS-->onTouchEvent-->ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("FFF", "MainActivitySS-->onTouchEvent-->ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("FFF", "MainActivitySS-->onTouchEvent-->ACTION_UP");
//                break;
//            default:
//                Log.e("FFF", "MainActivitySS-->onTouchEvent-->default");
//                break;
//        }
//        return super.onTouchEvent(event);
//    }


}
