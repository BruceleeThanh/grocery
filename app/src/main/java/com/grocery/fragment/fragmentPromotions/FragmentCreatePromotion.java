package com.grocery.fragment.fragmentPromotions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.utils.ProgerssBarUntil;

/**
 * Created by DangTin on 02/07/2018.
 */

public class FragmentCreatePromotion extends Fragment implements View.OnClickListener {

    private ProgerssBarUntil progerssBarUntil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_promotion, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {

    }

    private void initListener() {

    }

    private void initView(View view) {

    }

    @Override
    public void onClick(View v) {

    }
}
