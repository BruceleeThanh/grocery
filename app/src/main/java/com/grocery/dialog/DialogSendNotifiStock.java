package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.common.CustomTextView;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 17/10/2017.
 */

public class DialogSendNotifiStock extends Dialog implements View.OnClickListener {
    private CustomTextView tvCancel;
    private LinearLayout rdAllUser1;
    private LinearLayout rdAllUser2;
    private LinearLayout rdAllUser3;
    private ArrayList<LinearLayout> arrRd;
    private CustomTextView tvAllUser1;
    private CustomTextView tvAllUser2;
    private CustomTextView tvAllUser3;
    private ArrayList<CustomTextView> arrTvRd;
    private LinearLayout layoutAllUsers1;
    private LinearLayout layoutAllUsers2;
    private LinearLayout layoutAllUsers3;
    private ArrayList<LinearLayout> arrLayout1;
    private LinearLayout rdCustomNotifi;
    private LinearLayout rdSavedNotifi;
    private ArrayList<LinearLayout> arrRd2;
    private CustomTextView tvCustomNotifi;
    private CustomTextView tvSavedNotifi;
    private ArrayList<CustomTextView> arrTvRd2;
    private LinearLayout layoutSavedNotifi;
    private LinearLayout layoutCustomNotifi;
    private ArrayList<LinearLayout> arrLayout2;

    public DialogSendNotifiStock(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_send_notifi_stock);
        if (context instanceof MainActivity) {
            setOwnerActivity((MainActivity) context);
        }
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tvCancel = (CustomTextView) findViewById(R.id.tvCancel);
        rdAllUser1 = (LinearLayout) findViewById(R.id.rdAllUser1);
        rdAllUser2 = (LinearLayout) findViewById(R.id.rdAllUser2);
        rdAllUser3 = (LinearLayout) findViewById(R.id.rdAllUser3);
        tvAllUser1 = (CustomTextView) findViewById(R.id.tvAllUser1);
        tvAllUser2 = (CustomTextView) findViewById(R.id.tvAllUser2);
        tvAllUser3 = (CustomTextView) findViewById(R.id.tvAllUser3);
        layoutAllUsers1 = (LinearLayout) findViewById(R.id.layoutAllUsers1);
        layoutAllUsers2 = (LinearLayout) findViewById(R.id.layoutAllUsers2);
        layoutAllUsers3 = (LinearLayout) findViewById(R.id.layoutAllUsers3);
        rdSavedNotifi = (LinearLayout) findViewById(R.id.rdSendNotifi);
        rdCustomNotifi = (LinearLayout) findViewById(R.id.rdCustomNotifi);
        tvSavedNotifi = (CustomTextView) findViewById(R.id.tvSendNotifi);
        tvCustomNotifi = (CustomTextView) findViewById(R.id.tvCustomNotifi);
        layoutCustomNotifi = (LinearLayout) findViewById(R.id.layoutCustomNotifi);
        layoutSavedNotifi = (LinearLayout) findViewById(R.id.layoutSendNotifi);
    }

    private void initData() {
        arrRd = new ArrayList<>();
        arrRd.add(rdAllUser1);
        arrRd.add(rdAllUser2);
        arrRd.add(rdAllUser3);
        arrTvRd = new ArrayList<>();
        arrTvRd.add(tvAllUser1);
        arrTvRd.add(tvAllUser2);
        arrTvRd.add(tvAllUser3);
        arrLayout1 = new ArrayList<>();
        arrLayout1.add(layoutAllUsers1);
        arrLayout1.add(layoutAllUsers2);
        arrLayout1.add(layoutAllUsers3);
        arrRd2 = new ArrayList<>();
        arrRd2.add(rdCustomNotifi);
        arrRd2.add(rdSavedNotifi);
        arrTvRd2 = new ArrayList<>();
        arrTvRd2.add(tvCustomNotifi);
        arrTvRd2.add(tvSavedNotifi);
        arrLayout2 = new ArrayList<>();
        arrLayout2.add(layoutCustomNotifi);
        arrLayout2.add(layoutSavedNotifi);
    }

    private void initListener() {
        tvCancel.setOnClickListener(this);
        for (int i = 0; i < arrLayout1.size(); i++) {
            final int finalI = i;
            arrLayout1.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < arrRd.size(); i++) {
                        arrRd.get(i).setSelected(false);
                        arrTvRd.get(i).setTextColor(getContext().getResources().getColor(R.color.text_spiner));
                    }
                    arrRd.get(finalI).setSelected(true);
                    arrTvRd.get(finalI).setTextColor(getContext().getResources().getColor(R.color.bg_search_main));
                }
            });
        }
        for (int i = 0; i < arrLayout2.size(); i++) {
            final int finalI = i;
            arrLayout2.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < arrRd2.size(); i++) {
                        arrRd2.get(i).setSelected(false);
                        arrTvRd2.get(i).setTextColor(getContext().getResources().getColor(R.color.text_spiner));
                    }
                    arrRd2.get(finalI).setSelected(true);
                    arrTvRd2.get(finalI).setTextColor(getContext().getResources().getColor(R.color.bg_search_main));
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                cancel();
                break;
        }
    }
}
