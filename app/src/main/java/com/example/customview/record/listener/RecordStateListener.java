package com.example.customview.record.listener;

import com.example.customview.record.RecordHelper;

/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 录音状态回调
 */
public interface RecordStateListener {

    /**
     * 当前的录音状态发生变化
     *
     * @param state 当前状态
     */
    void onStateChange(RecordHelper.RecordState state);

    /**
     * 录音错误
     *
     * @param error 错误
     */
    void onError(String error);

}
