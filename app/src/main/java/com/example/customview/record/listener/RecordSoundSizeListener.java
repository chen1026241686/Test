package com.example.customview.record.listener;

/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 录音音量回调
 */
public interface RecordSoundSizeListener {

    /**
     * 实时返回音量大小
     *
     * @param soundSize 当前音量大小
     */
    void onSoundSize(int soundSize);

}
