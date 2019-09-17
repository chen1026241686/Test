package com.example.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author ChenYasheng
 * @date 2019/9/16
 * @Description
 */
public class DialogRecordFragment extends BottomSheetDialogFragment {

    private Unbinder unbinder;

    @BindView(R.id.imgRecord)
    public ImageView imgRecord;

    @OnClick({R.id.imgRecord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgRecord:
                imgRecord.setImageResource(R.drawable.stop);
                break;
            default:
                break;
        }
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.view_bottom_sheet_fragment, container);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
