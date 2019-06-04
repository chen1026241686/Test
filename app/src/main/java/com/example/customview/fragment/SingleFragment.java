package com.example.customview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SingleFragment extends Fragment {
    public static final String ARGUMENT = "ARGUMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String text = "";
        if (bundle != null) {
            text = bundle.getString(ARGUMENT);
        }
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    public static SingleFragment createFragment(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        SingleFragment fragment = new SingleFragment();
        fragment.setArguments(bundle);

        return fragment;
    }
}

