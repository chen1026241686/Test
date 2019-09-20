package com.example.customview.record;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import java.io.File;


/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 音频录制/播放类
 */

public class RecordHelper {

    private volatile static RecordHelper instance;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private boolean hasFocus = false;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    /**
     * 录制/播放状态
     */
    private volatile RecordState recordState = RecordState.RECORDIDLE;

    /**
     * 录制参数
     */
    private RecordConfig currentConfig;

    /**
     * 播放状态
     */
    private RecordStateListener recordStateListener;

    /**
     * 录制音频线程
     */
    private AudioRecordThread audioRecordThread;


    /**
     * 更新播放进度
     */
    private final int UPDATE_PROGRESS = 100;

    private Handler mainHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case UPDATE_PROGRESS:
                    if (recordStateListener != null && recordState == RecordState.PLAYPLAYING && mMediaPlayer != null) {
                        recordStateListener.onStateChange(RecordState.PLAYPLAYING, mMediaPlayer.getCurrentPosition(), mMediaPlayer.getDuration());
                        mainHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 1000);
                    }
                    break;
                default:
                    break;
            }

        }
    };


    /**
     * 已经录制了多场时间
     */
    private int recoredTime = 0;

    /**
     * 开始录制时间
     */
    private long startRecordTime = 0;


    /**
     * 最终生成的录制文件
     */
    private File resultFile = null;
    private Mp3EncodeThread mp3EncodeThread;

    private RecordHelper() {

    }

    public static RecordHelper getInstance() {
        if (instance == null) {
            synchronized (RecordHelper.class) {
                if (instance == null) {
                    instance = new RecordHelper();
                }
            }
        }
        return instance;
    }

    public RecordState getRecordState() {
        return recordState;
    }

    public void setRecordStateListener(RecordStateListener recordStateListener) {
        this.recordStateListener = recordStateListener;
    }


    /**
     * 开始录音
     *
     * @param config
     */
    public void recordStart(RecordConfig config) {


        //录制状态或者录制已经结束了，则直接返回
        if (recordState == RecordState.RECORDING || recordState == RecordState.RECORDSTOP) {
            return;
        }
        //正在播放的时候或者暂停播放的时候点了重录按钮,需要重置一下MediaPlayer
        else if (recordState == RecordState.PLAYPLAYING || recordState == RecordState.PLAYPAUSE) {
            reset();
        }
        //空闲状态，录制结束状态，播放结束状态都可以直接去重新录音。不做判断
        currentConfig = config;
        recoredTime = 0;
        resultFile = new File(config.getRecordDir() + "/" + System.currentTimeMillis() + config.getFormat().getExtension());
        audioRecordThread = new AudioRecordThread();
        audioRecordThread.start();

    }


    /**
     * 结束录音
     */
    public void recordStop() {
        //只有录制状态才能进入到停止状态
        if (recordState == RecordState.RECORDING) {
            recordState = RecordState.RECORDSTOP;
            notifyState();
        } else {
            Log.e("FFF", "调用recordStop函数的时候状态不对---->" + recordState);
        }
    }

    public void recordReset() {
        //释放mediaPlayer
        if (recordState == RecordState.PLAYPAUSE || recordState == RecordState.PLAYPLAYING) {
            reset();
        } else if (recordState == RecordState.RECORDING) {
            //停止录音
            recordStop();
        }
        recordState = RecordState.RECORDIDLE;
        recoredTime = 0;
        if (recordStateListener != null) {
            recordStateListener.onStateChange(recordState, 0, currentConfig.getRecordMaxTime());
        }
    }

    public void release() {
        recordStateListener = null;
        recordReset();
    }

    public void playStart(Context context) {

        //从头开始播放只有状态在录制完成或者播放完成的状态才行，其他状态进来都不对
        if (recordState == RecordState.RECORDFINISH || recordState == RecordState.PLAYFINISH) {
            //目标播放文件存在才执行播放
            if (context != null && resultFile != null && resultFile.exists() && resultFile.isFile()) {
                resetMediaPlayer();
                if (audioFocusChangeListener == null) {
                    audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                        public void onAudioFocusChange(int focusChange) {
                            // 当失去焦点的时候停止播放
                            if (mAudioManager != null && focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                                //设置状态为播放完成
                                recordState = RecordState.PLAYFINISH;
                                if (recordStateListener != null) {
                                    recordStateListener.onStateChange(recordState, recoredTime, mMediaPlayer == null ? recoredTime : mMediaPlayer.getDuration());
                                }
                                muteAudioFocus(mAudioManager, false);
                                resetMediaPlayer();
                            }
                        }
                    };
                }

                try {
                    mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    muteAudioFocus(mAudioManager, true);
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            recordState = RecordState.PLAYFINISH;
                            if (recordStateListener != null) {
                                recordStateListener.onStateChange(recordState, recoredTime, mMediaPlayer == null ? recoredTime : mMediaPlayer.getDuration());
                            }
                            reset();
                        }
                    });
                    mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            recordState = RecordState.PLAYFINISH;
                            if (recordStateListener != null) {
                                recordStateListener.onStateChange(recordState, recoredTime, mMediaPlayer == null ? recoredTime : mMediaPlayer.getDuration());
                            }
                            reset();
                            return true;
                        }
                    });
                    mMediaPlayer.setDataSource(context, Uri.fromFile(resultFile));
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            if (hasFocus) {
                                mMediaPlayer.start();
                                recordState = RecordState.PLAYPLAYING;
                                if (recordStateListener != null) {
                                    recordStateListener.onStateChange(recordState, mMediaPlayer.getCurrentPosition(), mMediaPlayer.getDuration());
                                }
                                mainHandler.sendEmptyMessage(UPDATE_PROGRESS);
                            }
                        }
                    });
                    mMediaPlayer.prepare();
                } catch (Exception e) {
                    if (recordStateListener != null) {
                        //播放失败的话设置状态为播放完成
                        recordState = RecordState.PLAYFINISH;
                        if (recordStateListener != null) {
                            recordStateListener.onStateChange(recordState, recoredTime, mMediaPlayer == null ? recoredTime : mMediaPlayer.getDuration());
                        }
                        recordStateListener.onError("播放失败");
                    }
                    reset();
                }
            }

        } else {
            Log.e("FFF", "调用playStart函数的时候状态不对---" + recordState);
        }
    }

    public void playOrPause() {

        if (recordState == RecordState.PLAYPAUSE || recordState == RecordState.PLAYPLAYING) {

            if (recordState == RecordState.PLAYPLAYING) {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mainHandler.removeMessages(UPDATE_PROGRESS);

                    recordState = RecordState.PLAYPAUSE;
                    if (recordStateListener != null) {
                        recordStateListener.onStateChange(recordState, mMediaPlayer.getCurrentPosition(), mMediaPlayer.getDuration());
                    }

                }
            } else if (recordState == RecordState.PLAYPAUSE) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }
                recordState = RecordState.PLAYPLAYING;
                if (recordStateListener != null) {
                    recordStateListener.onStateChange(recordState, mMediaPlayer.getCurrentPosition(), mMediaPlayer.getDuration());
                }
                mainHandler.sendEmptyMessage(UPDATE_PROGRESS);
            }
        } else {
            Log.e("FFF", "调用playPauseResume函数的时候发现状态不对---" + recordState);
        }
    }


    private void reset() {
        resetMediaPlayer();
        resetAudioPlayManager();
    }

    private void resetAudioPlayManager() {
        if (mAudioManager != null) {
            muteAudioFocus(mAudioManager, false);
        }
        mAudioManager = null;
    }

    private void resetMediaPlayer() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            } catch (IllegalStateException var2) {

            }
        }

    }

    private void muteAudioFocus(AudioManager audioManager, boolean mute) {
        if (Build.VERSION.SDK_INT >= 8) {
            if (mute) {
                audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                hasFocus = true;
            } else {
                audioManager.abandonAudioFocus(audioFocusChangeListener);
                audioFocusChangeListener = null;
                hasFocus = false;
            }
        }
    }


    private void notifyState() {
        if (recordStateListener == null) {
            return;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                recordStateListener.onStateChange(recordState, recoredTime, currentConfig.getRecordMaxTime());
            }
        });
    }

    private void notifyError(final String error) {
        if (recordStateListener == null) {
            return;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                recordStateListener.onError(error);
            }
        });
    }


    private void startMp3EncoderThread(int bufferSize) {
        try {
            mp3EncodeThread = new Mp3EncodeThread(resultFile, bufferSize, this.currentConfig);
            mp3EncodeThread.start();
        } catch (Exception e) {

        }
    }

    /**
     * 录音线程
     */
    private class AudioRecordThread extends Thread {
        private AudioRecord audioRecord;
        /**
         * 最小一次录制多少byte
         */
        private int bufferSize;

        AudioRecordThread() {
            bufferSize = AudioRecord.getMinBufferSize(currentConfig.getSampleRate(), currentConfig.getChannelConfig(), currentConfig.getEncodingConfig());
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, currentConfig.getSampleRate(), currentConfig.getChannelConfig(), currentConfig.getEncodingConfig(), bufferSize);
            if (currentConfig.getFormat() == RecordConfig.RecordFormat.MP3 && mp3EncodeThread == null) {
                startMp3EncoderThread(bufferSize);
            }
        }

        @Override
        public void run() {
            super.run();
            switch (currentConfig.getFormat()) {
                case MP3:
                    startMp3Recorder();
                    break;
            }
        }

        private void startMp3Recorder() {
            recordState = RecordState.RECORDING;
            //通知listener回调开始录制了
            notifyState();
            try {
                audioRecord.startRecording();
                startRecordTime = System.currentTimeMillis();
                short[] byteBuffer = new short[bufferSize];
                while (recordState == RecordState.RECORDING) {
                    int end = audioRecord.read(byteBuffer, 0, byteBuffer.length);
                    recoredTime = (int) (System.currentTimeMillis() - startRecordTime);
                    if (mp3EncodeThread != null) {
                        mp3EncodeThread.addChangeBuffer(new Mp3EncodeThread.ChangeBuffer(byteBuffer, end));
                    }

                    //超过了最大时间就不再录制
                    if (recoredTime >= currentConfig.getRecordMaxTime()) {
                        recoredTime = currentConfig.getRecordMaxTime();
                        recordState = RecordState.RECORDSTOP;
                    }

                    if (recordStateListener != null) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                recordStateListener.onStateChange(recordState, recoredTime, currentConfig.getRecordMaxTime());
                            }
                        });
                    }
                }
                audioRecord.stop();
            } catch (Exception e) {
                notifyError("录音失败");
                recoredTime = 0;
                recordState = RecordState.RECORDIDLE;
                if (recordStateListener != null) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            recordStateListener.onStateChange(recordState, recoredTime, currentConfig.getRecordMaxTime());
                        }
                    });
                }
                return;

            }
            if (recordState == RecordState.RECORDSTOP) {
                stopMp3Encoded();
            }
        }
    }

    private void stopMp3Encoded() {
        if (mp3EncodeThread != null) {
            synchronized (this) {
                if (mp3EncodeThread != null) {
                    mp3EncodeThread.stopSafe(new Mp3EncodeThread.EncordFinishListener() {
                        @Override
                        public void onFinish() {
                            recordState = RecordState.RECORDFINISH;
                            notifyState();
                            mp3EncodeThread = null;
                        }
                    });
                }
            }
        }
    }

    /**
     * 当前录音或者播放状态
     */
    public enum RecordState {

        /**
         * 空闲状态。
         */
        RECORDIDLE,
        /**
         * 录音中
         */
        RECORDING,
        /**
         * 正在停止
         */
        RECORDSTOP,
        /**
         * 录音流程结束（转换结束）
         */
        RECORDFINISH,
        /**
         * 暂停播放
         */
        PLAYPAUSE,
        /**
         * 播放中
         */
        PLAYPLAYING,
        /**
         * 自动播放完成
         */
        PLAYFINISH;

    }

    public interface RecordStateListener {

        /**
         * 当前的录音状态发生变化
         *
         * @param state 当前状态
         */
        void onStateChange(RecordHelper.RecordState state, int recoredTime, int totalTime);

        /**
         * 录音错误
         *
         * @param error 错误
         */
        void onError(String error);

    }

}
