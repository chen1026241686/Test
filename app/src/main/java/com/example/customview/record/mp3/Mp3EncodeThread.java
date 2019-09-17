package com.example.customview.record.mp3;


import com.example.customview.record.RecordConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 录制的音频信息转换成为mp3格式
 */

public class Mp3EncodeThread extends Thread {
    /**
     * 把录制的音频信息添加到该队列，线程进行处理该队列，在来到数据之后处理成为mp3
     */
    private List<ChangeBuffer> cacheBufferList = Collections.synchronizedList(new LinkedList<ChangeBuffer>());
    private File file;
    private FileOutputStream fileOutputStream;
    private byte[] mp3Buffer;
    private EncordFinishListener encordFinishListener;

    /**
     * 是否已停止录音
     */
    private volatile boolean isOver = false;

    /**
     * 是否继续轮询数据队列
     */
    private volatile boolean start = true;

    public Mp3EncodeThread(File file, int bufferSize, RecordConfig currentConfig) {
        this.file = file;
        mp3Buffer = new byte[(int) (7200 + (bufferSize * 2 * 1.25))];
        int sampleRate = currentConfig.getSampleRate();
        Mp3Encoder.init(sampleRate, currentConfig.getChannelCount(), sampleRate, currentConfig.getRealEncoding());
    }

    @Override
    public void run() {
        try {
            this.fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            return;
        }

        while (start) {
            ChangeBuffer next = next();
            lameData(next);
        }
    }

    public void addChangeBuffer(ChangeBuffer changeBuffer) {
        if (changeBuffer != null) {
            cacheBufferList.add(changeBuffer);
            synchronized (this) {
                notify();
            }
        }
    }

    public void stopSafe(EncordFinishListener encordFinishListener) {
        this.encordFinishListener = encordFinishListener;
        isOver = true;
        synchronized (this) {
            notify();
        }
    }

    private ChangeBuffer next() {
        for (; ; ) {
            if (cacheBufferList == null || cacheBufferList.size() == 0) {
                try {
                    if (isOver) {
                        finish();
                    }
                    synchronized (this) {
                        wait();
                    }
                } catch (Exception e) {
                }
            } else {
                return cacheBufferList.remove(0);
            }
        }
    }

    private void lameData(ChangeBuffer changeBuffer) {
        if (changeBuffer == null) {
            return;
        }
        short[] buffer = changeBuffer.getData();
        int readSize = changeBuffer.getReadSize();
        if (readSize > 0) {
            int encodedSize = Mp3Encoder.encode(buffer, buffer, readSize, mp3Buffer);
            try {
                fileOutputStream.write(mp3Buffer, 0, encodedSize);
            } catch (IOException e) {
            }
        }
    }

    private void finish() {
        start = false;
        final int flushResult = Mp3Encoder.flush(mp3Buffer);
        if (flushResult > 0) {
            try {
                fileOutputStream.write(mp3Buffer, 0, flushResult);
                fileOutputStream.close();
            } catch (final IOException e) {
            }
        }
        if (encordFinishListener != null) {
            encordFinishListener.onFinish();
        }
    }

    public static class ChangeBuffer {
        private short[] rawData;
        private int readSize;

        public ChangeBuffer(short[] rawData, int readSize) {
            this.rawData = rawData.clone();
            this.readSize = readSize;
        }

        short[] getData() {
            return rawData;
        }

        int getReadSize() {
            return readSize;
        }
    }

    public interface EncordFinishListener {
        /**
         * 格式转换完毕
         */
        void onFinish();
    }
}
