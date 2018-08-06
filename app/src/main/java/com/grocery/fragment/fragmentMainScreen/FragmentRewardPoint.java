package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.common.CustomCheckBox;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentRewardPoint.FragmentRewadManager;
import com.grocery.fragment.fragmentRewardPoint.FragmentRewardSettings;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 30/06/2017.
 */

public class FragmentRewardPoint extends Fragment implements View.OnClickListener {
    private FragmentRewadManager fragmentRewadManager;
    private FragmentRewardSettings fragmentRewardSettings;
    private ArrayList<Fragment> arrFrag;

    private CustomTextView tvManager;
    private CustomTextView tvSettings;
    private ArrayList<TextView> arrTv;

    private LinearLayout lnFilter;
    private MainActivity mainActivity;
    private LinearLayout viewMain;

    private ArrayList<Integer> arrIdCb;
    private ArrayList<Integer> arrIdTvCb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reward_point, container, false);
        initView(view);
        initListener(view);
        return view;
    }

    private void initView(View view) {
        arrIdCb = new ArrayList<>();
        arrIdTvCb = new ArrayList<>();
        arrIdCb.add(R.id.cb_reward_adđ);
        arrIdCb.add(R.id.cb_reward_not_adđ);

        arrIdTvCb.add(R.id.tv_reward_adđ);
        arrIdTvCb.add(R.id.tv_reward_not_adđ);

        viewMain = (LinearLayout) view.findViewById(R.id.layour_reward_point);
        mainActivity = (MainActivity) getActivity();
        tvManager = (CustomTextView) view.findViewById(R.id.tv_reward_manager);
        tvSettings = (CustomTextView) view.findViewById(R.id.tv_reward_settings);
        arrTv = new ArrayList<>();
        arrTv.add(tvManager);
        arrTv.add(tvSettings);

        lnFilter = (LinearLayout) view.findViewById(R.id.layout_filter_reward);
        fragmentRewadManager = new FragmentRewadManager();
        fragmentRewardSettings = new FragmentRewardSettings();
        arrFrag = new ArrayList<>();
        arrFrag.add(fragmentRewadManager);
        arrFrag.add(fragmentRewardSettings);
//        addFragment();
        replaceFragment(new FragmentRewadManager());
        setBackGroundTv(R.id.tv_reward_manager);
    }

    private void initListener(View view) {
        viewMain.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
        tvManager.setOnClickListener(this);

        for (int i = 0; i < arrIdCb.size(); i++) {
            CustomCheckBox customCheckBox = (CustomCheckBox) view.findViewById(arrIdCb.get(i));
            customCheckBox.setOncheckListener(new CustomCheckBox.OnCheckListener() {
                @Override
                public void onCheck(CustomCheckBox view, boolean check) {
                    for (int i = 0; i < arrIdCb.size(); i++) {
                        TextView textView = (TextView) view.getRootView().findViewById(arrIdTvCb.get(i));
                        CustomCheckBox customCheckBox = (CustomCheckBox) view.getRootView().findViewById(arrIdCb.get(i));
                        if (view.getId() == arrIdCb.get(i)) {
                            if (check) {
                                textView.setTextColor(getResources().getColor(R.color.colorApp));
                            } else {
                                textView.setTextColor(getResources().getColor(R.color.text_spiner));
                            }
                        }
                        else {
                            customCheckBox.setChecked(false);
                            textView.setTextColor(getResources().getColor(R.color.text_spiner));
                        }
                    }
                }
            });
        }
    }

//    private void addFragment() {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.add(R.id.fragment_item_reward, fragmentRewadManager);
//        transaction.add(R.id.fragment_item_reward, fragmentRewardSettings);
//        transaction.commit();
//    }

//    public void changedFragment(Fragment fragment) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        for (int i = 0; i < arrFrag.size(); i++) {
//            transaction.hide(arrFrag.get(i));
//        }
//        transaction.show(fragment);
//        mainActivity.setFragmentTMP(fragment);
//        transaction.commit();
//    }
    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getChildFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
            ft.add(R.id.fragment_item_reward, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public void setBackGroundTv(int id) {
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
            if (arrTv.get(i).getId() == id) {
                arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reward_manager:
                replaceFragment(new FragmentRewadManager());
                setBackGroundTv(R.id.tv_reward_manager);
                lnFilter.setVisibility(LinearLayout.VISIBLE);
                break;
            case R.id.tv_reward_settings:
                setBackGroundTv(R.id.tv_reward_settings);
                replaceFragment(new FragmentRewardSettings());
                lnFilter.setVisibility(LinearLayout.INVISIBLE);
                break;
        }
    }
}
