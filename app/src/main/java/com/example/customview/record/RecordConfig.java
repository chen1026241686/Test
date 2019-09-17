package com.example.customview.record;

import android.media.AudioFormat;
import android.os.Environment;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 音频录制配置参数
 * <p>
 * 1)可以设置录制格式：mp3,wav,pcm。详细看{@link RecordFormat}
 * 2)可以设置录制的采样率，详细看{@link RecordRate}
 * 3)可以设置音频位宽，详细看{@link RecordBit}
 */
public class RecordConfig implements Serializable {
    /**
     * 录音格式 默认MP3格式
     */
    private RecordFormat format = RecordFormat.MP3;
    /**
     * 通道数:默认单通道
     */
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;

    /**
     * 位宽
     */
    private int encodingConfig = AudioFormat.ENCODING_PCM_16BIT;

    /**
     * 采样率
     */
    private int sampleRate = 16000;

    /*
     * 录音文件存放路径， TODO
     */
    private String recordDir = Environment.getExternalStorageDirectory() + "/hachi/recordAudio";

    public RecordConfig() {
    }

    public String getRecordDir() {
        return recordDir;
    }

    public void setRecordDir(String recordDir) {
        this.recordDir = recordDir;
    }

    /**
     * 获取当前录音的采样位宽 单位bit
     *
     * @return 采样位宽 0: error
     */
    public int getEncoding() {
        if (format == RecordFormat.MP3) {//mp3后期转换
            return 16;
        }

        if (encodingConfig == AudioFormat.ENCODING_PCM_8BIT) {
            return 8;
        } else if (encodingConfig == AudioFormat.ENCODING_PCM_16BIT) {
            return 16;
        } else {
            return 0;
        }
    }

    /**
     * 获取当前录音的采样位宽 单位bit
     *
     * @return 采样位宽 0: error
     */
    public int getRealEncoding() {
        if (encodingConfig == AudioFormat.ENCODING_PCM_8BIT) {
            return 8;
        } else if (encodingConfig == AudioFormat.ENCODING_PCM_16BIT) {
            return 16;
        } else {
            return 0;
        }
    }

    /**
     * 当前的声道数
     *
     * @return 声道数： 0：error
     */
    public int getChannelCount() {
        if (channelConfig == AudioFormat.CHANNEL_IN_MONO) {
            return 1;
        } else if (channelConfig == AudioFormat.CHANNEL_IN_STEREO) {
            return 2;
        } else {
            return 0;
        }
    }

    //get&set

    public RecordFormat getFormat() {
        return format;
    }

    public RecordConfig setFormat(RecordFormat format) {
        this.format = format;
        return this;
    }

    public int getChannelConfig() {
        return channelConfig;
    }

    public int getEncodingConfig() {
        if (format == RecordFormat.MP3) {//mp3后期转换
            return AudioFormat.ENCODING_PCM_16BIT;
        }
        return encodingConfig;
    }

    public RecordConfig setEncodingConfig(int encodingConfig) {
        this.encodingConfig = encodingConfig;
        return this;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public RecordConfig setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
        return this;
    }


    /**
     * 音频录制支持的格式
     */
    public enum RecordFormat {/**
     * mp3格式
     */
    MP3(".mp3"),
        /**
         * wav格式
         */
        WAV(".wav"),
        /**
         * pcm格式
         */
        PCM(".pcm");

        private String extension;

        public String getExtension() {
            return extension;
        }

        RecordFormat(String extension) {
            this.extension = extension;
        }}

    /**
     * 音频采样率
     */
    public enum RecordRate {

        RB8K(8000), RB16K(16000), RB44K(44100);
        private int rate;

        public int getRate() {
            return rate;
        }

        RecordRate(int rate) {
            this.rate = rate;
        }}

    /**
     * 音频位宽
     */
    public enum RecordBit {

        EIGHT_BIT(AudioFormat.ENCODING_PCM_8BIT), SIXTEEN_BIT(AudioFormat.ENCODING_PCM_16BIT);
        private int bit;

        public int getBit() {
            return bit;
        }

        RecordBit(int bit) {
            this.bit = bit;
        }}
}
