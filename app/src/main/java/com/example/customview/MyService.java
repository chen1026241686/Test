package com.example.customview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.example.customview.aidl.IMyAidlInterface;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    public MyService() {
    }


    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void justPrintMessage(String obj) throws RemoteException {
            Log.e("FFF", "PrintMessage PID=" + Process.myPid());
            Log.e("FFF", "obj==" + obj);
        }
    };

    private Timer timer = new Timer();

    private Context context;

    TimerTask timerTask = new TimerTask() {
        int j = 1;

        @Override
        public void run() {
            Log.e("FFF", "timerTask--------->");
            Intent intent = new Intent(context, MainActivity4.class);
            intent.putExtra("FFF", j);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            j++;
        }
    };


    @Override
    public void onCreate() {

        context = this;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        timer.schedule(timerTask, 10000, 20000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        Log.e("FFF", "onBind PID=" + Process.myPid());
        return mBinder;
    }

    public void haha() {

    }

}
