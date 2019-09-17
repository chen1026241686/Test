package com.example.customview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.example.customview.aidl.IMyAidlInterface;

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


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
