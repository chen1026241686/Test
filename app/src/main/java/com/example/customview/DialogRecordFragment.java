package com.example.customview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.record.RecordConfig;
import com.example.customview.record.RecordHelper;
import com.example.customview.view.CircleText;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author ChenYasheng
 * @date 2019/9/16
 * @Description 底部弹出来的录制音频Fragment，弹出来之前需要请求录音权限
 */
public class DialogRecordFragment extends BottomSheetDialogFragment {


    /**
     * 最大录制时间
     */
    private int recordTotalTime = 60 * 1000;

    private RecordConfig config = new RecordConfig();

    {
        //mp3格式
        config.setFormat(RecordConfig.RecordFormat.MP3);
        //采样率
        config.setSampleRate(RecordConfig.RecordRate.RB16K);
        //位宽
        config.setEncodingConfig(RecordConfig.RecordBit.SIXTEEN_BIT);
        //最大记录时长
        config.setRecordMaxTime(recordTotalTime);
    }


    private Context mContext;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");


    private Unbinder unbinder;

    /**
     * 录制按钮
     */
    @BindView(R.id.imgRecord)
    public ImageView imgRecord;
    /**
     * 已经录制时间
     */
    @BindView(R.id.tvRecordedTime)
    public TextView tvRecordedTime;
    /**
     * 最大录制时长
     */
    @BindView(R.id.tvRecordMax)
    public TextView tvRecordMax;
    /**
     * 斜杠
     */
    @BindView(R.id.slash)
    public TextView slash;
    /**
     * 播放录音/录制状态
     */
    @BindView(R.id.tvPlaySate)
    public TextView tvPlaySate;
    /**
     * 重新录制
     */
    @BindView(R.id.recordRestart)
    public CircleText recordRestart;
    /**
     * 录制完成
     */
    @BindView(R.id.recordDone)
    public CircleText recordDone;
    private RecordHelper.RecordStateListener recordStateListener = new RecordHelper.RecordStateListener() {
        @Override
        public void onStateChange(RecordHelper.RecordState state, int currentTime, int totalTime) {
            if (state == RecordHelper.RecordState.RECORDING) {
                setRecordingView(currentTime, totalTime);
            } else if (state == RecordHelper.RecordState.RECORDIDLE) {
                setRecordIdleView(currentTime, totalTime);
            } else if (state == RecordHelper.RecordState.RECORDFINISH || state == RecordHelper.RecordState.RECORDSTOP || state == RecordHelper.RecordState.PLAYFINISH) {
                setRecordFinishView(currentTime);
            } else if (state == RecordHelper.RecordState.PLAYPAUSE) {
                setPlayingView(false, currentTime, totalTime);
            } else if (state == RecordHelper.RecordState.PLAYPLAYING) {
                setPlayingView(true, currentTime, totalTime);
            }
        }

        @Override
        public void onError(String error) {
            ToastUtils.showLong(error);
        }
    };

    @OnClick({R.id.imgRecord, R.id.recordRestart, R.id.recordDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgRecord:
                RecordHelper.RecordState state = RecordHelper.getInstance().getRecordState();
                if (state == RecordHelper.RecordState.RECORDIDLE || state == RecordHelper.RecordState.RECORDSTOP) {
                    RecordHelper.getInstance().recordStart(config);
                } else if (state == RecordHelper.RecordState.RECORDING) {
                    RecordHelper.getInstance().recordStop();
                } else if (state == RecordHelper.RecordState.RECORDFINISH || state == RecordHelper.RecordState.PLAYFINISH) {
                    RecordHelper.getInstance().playStart(mContext);
                } else if (state == RecordHelper.RecordState.PLAYPAUSE || state == RecordHelper.RecordState.PLAYPLAYING) {
                    RecordHelper.getInstance().playOrPause();
                }
                break;
            case R.id.recordRestart:
                RecordHelper.getInstance().recordReset();
                break;
            case R.id.recordDone:
                //TODO
                this.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.view_bottom_sheet_fragment, container);
        unbinder = ButterKnife.bind(this, inflate);
        RecordHelper.getInstance().setRecordStateListener(recordStateListener);
        setRecordIdleView(0, recordTotalTime);
        return inflate;
    }


    /**
     * @param recoredTime 已经录制时间
     * @param totalTime   最大录制时间
     */
    private void setRecordIdleView(int recoredTime, int totalTime) {

        tvRecordedTime.setText(simpleDateFormat.format(new Date(recoredTime)));
        slash.setVisibility(View.VISIBLE);
        tvRecordMax.setVisibility(View.VISIBLE);
        tvRecordMax.setText(simpleDateFormat.format(new Date(totalTime)));

        recordRestart.setVisibility(View.INVISIBLE);
        imgRecord.setImageResource(R.drawable.record_audio);
        recordDone.setVisibility(View.INVISIBLE);

        tvPlaySate.setText(R.string.start_record);
    }

    /**
     * @param recoredTime 已经录制时间
     * @param totalTime   最大录制时间
     */
    private void setRecordingView(int recoredTime, int totalTime) {
        tvRecordedTime.setText(simpleDateFormat.format(new Date(recoredTime)));
        slash.setVisibility(View.VISIBLE);
        tvRecordMax.setVisibility(View.VISIBLE);
        tvRecordMax.setText(simpleDateFormat.format(new Date(totalTime)));

        recordRestart.setVisibility(View.INVISIBLE);
        imgRecord.setImageResource(R.drawable.record_stop);
        recordDone.setVisibility(View.INVISIBLE);

        tvPlaySate.setText(R.string.recording_audio);
    }

    /**
     * @param recoredTime 已经录制时间
     */
    private void setRecordFinishView(int recoredTime) {

        tvRecordedTime.setText(simpleDateFormat.format(new Date(recoredTime)));
        slash.setVisibility(View.GONE);
        tvRecordMax.setVisibility(View.GONE);

        recordRestart.setVisibility(View.VISIBLE);
        imgRecord.setImageResource(R.drawable.play_pause);
        recordDone.setVisibility(View.VISIBLE);

        tvPlaySate.setText(R.string.play_audio);
    }

    /**
     * @param isPlaying       是否正在播放
     * @param currentPosition 当前播放位置
     * @param totalTime       音频总时长
     */
    private void setPlayingView(boolean isPlaying, int currentPosition, int totalTime) {
        tvRecordedTime.setText(simpleDateFormat.format(new Date(currentPosition)));
        slash.setVisibility(View.VISIBLE);
        tvRecordMax.setVisibility(View.VISIBLE);
        tvRecordMax.setText(simpleDateFormat.format(new Date(totalTime)));

        recordRestart.setVisibility(View.VISIBLE);
        imgRecord.setImageResource(isPlaying ? R.drawable.play_playing : R.drawable.play_pause);
        recordDone.setVisibility(View.VISIBLE);

        tvPlaySate.setText(R.string.pause_audio);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        RecordHelper.getInstance().release();
    }
}
