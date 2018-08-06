package com.grocery.fragment.fragmentMainScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentPromotions.FragmentCreatePromotion;
import com.grocery.fragment.fragmentPromotions.FragmentCurrentPromotions;
import com.grocery.utils.FragmentUtils;
import com.grocery.utils.HideSoftKeyBroad;

import java.util.ArrayList;

/**
 * Created by DangTin on 02/07/2018.
 */

public class FragmentPromotions extends Fragment implements View.OnClickListener {
    private CustomTextView tvCurrentPromotions;
    private CustomTextView tvCreatePromotion;

    private ArrayList<TextView> arrTv;
    private LinearLayout layoutLineCurrent;
    private LinearLayout layoutLineCreate;
    private ArrayList<LinearLayout> arrLayoutLine;

    private FragmentUtils fragmentUtils;
    private FragmentCurrentPromotions fragmentCurrentPromotions;
    private FragmentCreatePromotion fragmentCreatePromotion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_promotions, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        arrTv = new ArrayList<>();
        arrTv.add(tvCurrentPromotions);
        arrTv.add(tvCreatePromotion);

        setBackGround(R.id.tv_current_promotions);
        /**/
        arrLayoutLine = new ArrayList<>();
        arrLayoutLine.add(layoutLineCurrent);
        arrLayoutLine.add(layoutLineCreate);
        /**/
        setColorLine(R.id.layout_line_current_promotions);
        fragmentUtils.addToFragment(fragmentCurrentPromotions);
    }

    private void initView(View view) {
        // anh xa cac textView, listView
        tvCurrentPromotions = (CustomTextView) view.findViewById(R.id.tv_current_promotions);
        tvCreatePromotion = (CustomTextView) view.findViewById(R.id.tv_create_promotion);
        layoutLineCurrent = (LinearLayout) view.findViewById(R.id.layout_line_current_promotions);
        layoutLineCreate = (LinearLayout) view.findViewById(R.id.layout_line_create_promotion);
        /*init fragment*/
        fragmentUtils = new FragmentUtils(getFragmentManager(), R.id.list_fragment_manager_promotions);
        fragmentCurrentPromotions = new FragmentCurrentPromotions();
        fragmentCreatePromotion = new FragmentCreatePromotion();
    }

    public void setColorLine(int id) {
        for (int i = 0; i < arrLayoutLine.size(); i++) {
            arrLayoutLine.get(i).setBackgroundColor(getResources().getColor(R.color.colorApp));
            if (arrLayoutLine.get(i).getId() == id) {
                arrLayoutLine.get(i).setBackgroundColor(0);
            }
        }
    }

    private void initListener() {
        tvCurrentPromotions.setOnClickListener(this);
        tvCreatePromotion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.tv_current_promotions:
                setBackGround(R.id.tv_current_promotions);
                fragmentUtils.changeFragment(fragmentCurrentPromotions);
                setColorLine(R.id.layout_line_current_promotions);
                break;
            case R.id.tv_create_promotion:
                setBackGround(R.id.tv_create_promotion);
                fragmentUtils.changeFragment(fragmentCreatePromotion);
                setColorLine(R.id.layout_line_create_promotion);
                break;
        }
    }

    public void setBackGround(int id) {
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setTextColor(Color.WHITE);
            arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
            if (arrTv.get(i).getId() == id) {
                arrTv.get(i).setTextColor(getResources().getColor(R.color.colorApp));
                arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_item));
            }
        }
    }
}
