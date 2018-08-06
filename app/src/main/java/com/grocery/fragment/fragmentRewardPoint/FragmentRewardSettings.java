package com.grocery.fragment.fragmentRewardPoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.common.CustomTextView;

/**
 * Created by ThanhBeo on 04/07/2017.
 */

public class FragmentRewardSettings extends Fragment implements View.OnClickListener {
    private CustomTextView tvUpdate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.reward_settings,container,false);
        initView(view);
        initListenner();
        return view;
    }

    private void initListenner() {
        tvUpdate.setOnClickListener(this);
    }

    private void initView(View view) {
        tvUpdate = (CustomTextView) view.findViewById(R.id.btn_save_update_reward_settings);
    }

    @Override
    public void onClick(View v) {

    }
}
