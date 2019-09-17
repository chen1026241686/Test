package com.example.customview.record.listener;

/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 当前的录音状态发生变化
 */

public interface RecordDataListener {

    /**
     * 当前的录音状态发生变化
     *
     * @param data 当前音频数据
     */
    void onData(byte[] data);

}
