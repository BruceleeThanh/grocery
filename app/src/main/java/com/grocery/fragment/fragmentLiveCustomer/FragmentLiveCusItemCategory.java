package com.grocery.fragment.fragmentLiveCustomer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.common.CustomTextView;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 18/07/2017.
 */

public class FragmentLiveCusItemCategory extends Fragment implements View.OnClickListener {
    private LinearLayout viewMain;
    private ArrayList<Integer> arrIdRadioBtn;
    private ArrayList<Integer> arrIdTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_customers_item_category, container, false);
        initView(view);
        initListener(view);
        return view;
    }

    private void initListener(View view) {
        viewMain.setOnClickListener(this);
        for (int i = 0; i < arrIdRadioBtn.size(); i++) {
            LinearLayout layoutRdo = (LinearLayout) view.findViewById(arrIdRadioBtn.get(i));
            layoutRdo.setOnClickListener(this);
        }
    }

    private void initView(View view) {
        arrIdRadioBtn = new ArrayList<>();
        arrIdRadioBtn.add(R.id.rd_live_5min);
        arrIdRadioBtn.add(R.id.rd_live_10min);
        arrIdRadioBtn.add(R.id.rd_live_15min);
        arrIdRadioBtn.add(R.id.rd_live_30min);
        arrIdRadioBtn.add(R.id.rd_live_45min);
        arrIdRadioBtn.add(R.id.rd_live_60min);
        arrIdTv = new ArrayList<>();
        arrIdTv.add(R.id.tv_live_5min);
        arrIdTv.add(R.id.tv_live_10min);
        arrIdTv.add(R.id.tv_live_15min);
        arrIdTv.add(R.id.tv_live_30min);
        arrIdTv.add(R.id.tv_live_45min);
        arrIdTv.add(R.id.tv_live_60min);
        viewMain = (LinearLayout) view.findViewById(R.id.layout_live_customer_item_category);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_live_customer_item_category) {
            return;
        }
        for (int i = 0; i < arrIdRadioBtn.size(); i++) {
            LinearLayout layoutRdo = (LinearLayout) getView().findViewById(arrIdRadioBtn.get(i));
            layoutRdo.setSelected(false);
            if (arrIdRadioBtn.get(i) == v.getId()) {
                layoutRdo.setSelected(true);
                TextView tvrdo;
                for (int j = 0; j < arrIdTv.size(); j++) {
                    tvrdo = (CustomTextView) getView().findViewById(arrIdTv.get(j));
                    tvrdo.setTextColor(getResources().getColor(R.color.text_spiner));
                }
                tvrdo = (CustomTextView) getView().findViewById(arrIdTv.get(i));
                tvrdo.setTextColor(getResources().getColor(R.color.color_Sat));
            }
        }
    }
}
