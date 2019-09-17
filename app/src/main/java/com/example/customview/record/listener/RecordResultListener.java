package com.example.customview.record.listener;

import java.io.File;

/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 录音完成回调
 */
public interface RecordResultListener {

    /**
     * 录音文件
     *
     * @param result 录音文件
     */
    void onResult(File result);
}
