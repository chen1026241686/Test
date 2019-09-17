package com.example.customview.record;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;


import com.example.customview.record.fftlib.ByteUtils;
import com.example.customview.record.listener.RecordDataListener;
import com.example.customview.record.listener.RecordFftDataListener;
import com.example.customview.record.listener.RecordResultListener;
import com.example.customview.record.listener.RecordSoundSizeListener;
import com.example.customview.record.listener.RecordStateListener;
import com.example.customview.record.mp3.Mp3EncodeThread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 音频录制类
 */

public class RecordHelper {
    private volatile static RecordHelper instance;
    /**
     * 录制状态
     */
    private volatile RecordState recordState = RecordState.IDLE;

    /**
     * 录制参数
     */
    private RecordConfig currentConfig;

    private RecordStateListener recordStateListener;
    private RecordDataListener recordDataListener;
    private RecordSoundSizeListener recordSoundSizeListener;
    private RecordResultListener recordResultListener;
    private RecordFftDataListener recordFftDataListener;

    private AudioRecordThread audioRecordThread;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * 最终生成的录制文件
     */
    private File resultFile = null;
    /**
     * 录制的时候的临时文件
     */
    private File tmpFile = null;
    private List<File> files = new ArrayList<>();
    private Mp3EncodeThread mp3EncodeThread;

    private RecordHelper() {

    }

    static RecordHelper getInstance() {
        if (instance == null) {
            synchronized (RecordHelper.class) {
                if (instance == null) {
                    instance = new RecordHelper();
                }
            }
        }
        return instance;
    }

    RecordState getRecordState() {
        return recordState;
    }

    void setRecordStateListener(RecordStateListener recordStateListener) {
        this.recordStateListener = recordStateListener;
    }

    void setRecordDataListener(RecordDataListener recordDataListener) {
        this.recordDataListener = recordDataListener;
    }

    void setRecordSoundSizeListener(RecordSoundSizeListener recordSoundSizeListener) {
        this.recordSoundSizeListener = recordSoundSizeListener;
    }

    void setRecordResultListener(RecordResultListener recordResultListener) {
        this.recordResultListener = recordResultListener;
    }

    public void setRecordFftDataListener(RecordFftDataListener recordFftDataListener) {
        this.recordFftDataListener = recordFftDataListener;
    }

    public void start(RecordConfig config) {
        this.currentConfig = config;
        if (recordState != RecordState.IDLE && recordState != RecordState.STOP) {
            return;
        }
        resultFile = new File(config.getRecordDir() + "/" + System.currentTimeMillis() + config.getEncoding());
        String tempFilePath = config.getRecordDir() + "/" + System.currentTimeMillis() + ".pcm";
        tmpFile = new File(tempFilePath);
        audioRecordThread = new AudioRecordThread();
        audioRecordThread.start();
    }

    public void stop() {
        if (recordState == RecordState.IDLE) {
            return;
        }
        if (recordState == RecordState.PAUSE) {
            makeFile();
            recordState = RecordState.IDLE;
            notifyState();
            stopMp3Encoded();
        } else {
            recordState = RecordState.STOP;
            notifyState();
        }
    }

//    public void pause() {
//        if (recordState != RecordState.RECORDING) {
//            Log.e("FFF", "状态不对，调用pause应该当前状态是RECORDING,但是当前状态是--->" + recordState.name());
//            return;
//        }
//        recordState = RecordState.PAUSE;
//        notifyState();
//    }

//    public void resume() {
//        if (recordState != RecordState.PAUSE) {
//            Log.e("FFF", "状态不对，调用resume应该当前状态是PAUSE,但是当前状态是--->" + recordState.name());
//            return;
//        }
//        String tempFilePath = currentConfig.getRecordDir() + "/" + System.currentTimeMillis() + ".pcm";
//        tmpFile = new File(tempFilePath);
//        audioRecordThread = new AudioRecordThread();
//        audioRecordThread.start();
//    }

    private void notifyState() {
        if (recordStateListener == null) {
            return;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                recordStateListener.onStateChange(recordState);
            }
        });

        if (recordState == RecordState.STOP || recordState == RecordState.PAUSE) {
            if (recordSoundSizeListener != null) {
                recordSoundSizeListener.onSoundSize(0);
            }
        }
    }

    private void notifyFinish() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (recordStateListener != null) {
                    recordStateListener.onStateChange(RecordState.FINISH);
                }
                if (recordResultListener != null) {
                    recordResultListener.onResult(resultFile);
                }
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


    private void notifyData(final byte[] data) {
        if (recordDataListener == null && recordSoundSizeListener == null && recordFftDataListener == null) {
            return;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (recordDataListener != null) {
                    recordDataListener.onData(data);
                }

                if (recordFftDataListener != null || recordSoundSizeListener != null) {
                    byte[] fftData = ByteUtils.makeFftData(data);
                    if (fftData != null) {
                        if (recordSoundSizeListener != null) {
                            recordSoundSizeListener.onSoundSize(getDb(fftData));
                        }
                        if (recordFftDataListener != null) {
                            recordFftDataListener.onFftData(fftData);
                        }
                    }
                }
            }
        });
    }

    private int getDb(byte[] data) {
        double sum = 0;
        double ave;
        int length = data.length > 128 ? 128 : data.length;
        int offsetStart = 8;
        for (int i = offsetStart; i < length; i++) {
            sum += data[i];
        }
        ave = (sum / (length - offsetStart)) * 65536 / 128f;
        int i = (int) (Math.log10(ave) * 20);
        return i < 0 ? 27 : i;
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
                default:
                    startPcmRecorder();
                    break;
            }
        }

        private void startPcmRecorder() {
            recordState = RecordState.RECORDING;
            notifyState();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(tmpFile);
                audioRecord.startRecording();
                byte[] byteBuffer = new byte[bufferSize];

                while (recordState == RecordState.RECORDING) {
                    int end = audioRecord.read(byteBuffer, 0, byteBuffer.length);
                    notifyData(byteBuffer);
                    fos.write(byteBuffer, 0, end);
                    fos.flush();
                }
                audioRecord.stop();
                files.add(tmpFile);
                if (recordState == RecordState.STOP) {
                    makeFile();
                }
            } catch (Exception e) {
                notifyError("录音失败");
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (recordState != RecordState.PAUSE) {
                recordState = RecordState.IDLE;
                notifyState();
            }
        }

        private void startMp3Recorder() {
            recordState = RecordState.RECORDING;
            //通知listener回调开始录制了
            notifyState();
            try {
                audioRecord.startRecording();
                short[] byteBuffer = new short[bufferSize];
                while (recordState == RecordState.RECORDING) {
                    int end = audioRecord.read(byteBuffer, 0, byteBuffer.length);
                    if (mp3EncodeThread != null) {
                        mp3EncodeThread.addChangeBuffer(new Mp3EncodeThread.ChangeBuffer(byteBuffer, end));
                    }
                    //通知声音回调和傅里叶数据回调
                    notifyData(ByteUtils.toBytes(byteBuffer));
                }
                audioRecord.stop();
            } catch (Exception e) {
                notifyError("录音失败");
            }
            if (recordState != RecordState.PAUSE) {
                recordState = RecordState.IDLE;
                //通知listener回调录制结束了
                notifyState();
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
                            notifyFinish();
                            mp3EncodeThread = null;
                        }
                    });
                }
            }
        }
    }

    private void makeFile() {
        switch (currentConfig.getFormat()) {
            case MP3:
                return;
            case WAV:
                mergePcmFile();
                makeWav();
                break;
            case PCM:
                mergePcmFile();
                break;
            default:
                break;
        }
        notifyFinish();
    }

    /**
     * 添加Wav头文件
     */
    private void makeWav() {
        if (!FileUtils.isFile(resultFile) || resultFile.length() == 0) {
            return;
        }
        byte[] header = WavUtils.generateWavFileHeader((int) resultFile.length(), currentConfig.getSampleRate(), currentConfig.getChannelCount(), currentConfig.getEncoding());
        WavUtils.writeHeader(resultFile, header);
    }

    /**
     * 合并文件
     */
    private void mergePcmFile() {
        boolean mergeSuccess = mergePcmFiles(resultFile, files);
        if (!mergeSuccess) {
            notifyError("合并失败");
        }
    }

    /**
     * 合并Pcm文件
     *
     * @param recordFile 输出文件
     * @param files      多个文件源
     * @return 是否成功
     */
    private boolean mergePcmFiles(File recordFile, List<File> files) {
        if (recordFile == null || files == null || files.size() <= 0) {
            return false;
        }

        FileOutputStream fos = null;
        BufferedOutputStream outputStream = null;
        byte[] buffer = new byte[1024];
        try {
            fos = new FileOutputStream(recordFile);
            outputStream = new BufferedOutputStream(fos);

            for (int i = 0; i < files.size(); i++) {
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(files.get(i)));
                int readCount;
                while ((readCount = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readCount);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < files.size(); i++) {
            files.get(i).delete();
        }
        files.clear();
        return true;
    }

    /**
     * 表示当前状态
     */
    public enum RecordState {/**
     * 空闲状态
     */
    IDLE,
        /**
         * 录音中
         */
        RECORDING,
        /**
         * 暂停中
         */
        PAUSE,
        /**
         * 正在停止
         */
        STOP,
        /**
         * 录音流程结束（转换结束）
         */
        FINISH}

}
