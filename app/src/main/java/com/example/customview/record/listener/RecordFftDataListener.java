package com.example.customview.record.listener;


/**
 * @author ChenYasheng
 * @date 2019/9/17
 * @Description 傅里叶转换后的数据
 */
public interface RecordFftDataListener {

    /**
     * @param data 录音可视化数据，即傅里叶转换后的数据：fftData
     */
    void onFftData(byte[] data);

}
