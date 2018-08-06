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
import com.grocery.activity.MainActivity;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentManagerOffer.FragmentManagerOfferCombo;
import com.grocery.fragment.fragmentManagerOffer.FragmentManagerOffersLive;
import com.grocery.fragment.fragmentManagerOffer.FragmentManagerOffersRegular;
import com.grocery.utils.FragmentUtils;
import com.grocery.utils.HideSoftKeyBroad;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentManagerOffers extends Fragment implements View.OnClickListener {
    private CustomTextView tvLive;
    private CustomTextView tvRegular;
    private CustomTextView tvCombo;
    private ArrayList<TextView> arrTv;
    private LinearLayout layoutLineCombo;
    private LinearLayout layoutLineLive;
    private LinearLayout layoutLineRegular;
    private ArrayList<LinearLayout> arrLayoutLine;

    private MainActivity mainActivity;
    private LinearLayout viewMain;
    private FragmentUtils fragmentUtils;
    private FragmentManagerOfferCombo fragmentManagerOfferCombo;
    private FragmentManagerOffersLive fragmentManagerOffersLive;
    private FragmentManagerOffersRegular fragmentManagerOffersRegular;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_offers, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        mainActivity = (MainActivity) getActivity();
        /**/
        arrTv = new ArrayList<>();
        arrTv.add(tvLive);
        arrTv.add(tvRegular);
        arrTv.add(tvCombo);
        setBackGround(R.id.tv_live_manager_offer);
        /**/
        arrLayoutLine = new ArrayList<>();
        arrLayoutLine.add(layoutLineCombo);
        arrLayoutLine.add(layoutLineLive);
        arrLayoutLine.add(layoutLineRegular);
        /**/
        setColorLine(R.id.layout_line_live_customer);
        fragmentUtils.addToFragment(fragmentManagerOffersLive);
    }

    private void initView(View view) {
        viewMain = (LinearLayout) view.findViewById(R.id.layout_manager_offers);
        // anh xa cac textView, listView
        tvLive = (CustomTextView) view.findViewById(R.id.tv_live_manager_offer);
        tvRegular = (CustomTextView) view.findViewById(R.id.tv_regular_manager_offer);
        tvCombo = (CustomTextView) view.findViewById(R.id.tv_combo_manager_offer);
        layoutLineCombo = (LinearLayout) view.findViewById(R.id.layout_line_combo_offer);
        layoutLineLive = (LinearLayout) view.findViewById(R.id.layout_line_live_customer);
        layoutLineRegular = (LinearLayout) view.findViewById(R.id.layout_line_regular_offer);
        /*init fragment*/
        fragmentUtils = new FragmentUtils(getFragmentManager(), R.id.list_fragment_manager_offers);
        fragmentManagerOfferCombo = new FragmentManagerOfferCombo();
        fragmentManagerOffersLive = new FragmentManagerOffersLive();
        fragmentManagerOffersRegular = new FragmentManagerOffersRegular();
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
        viewMain.setOnClickListener(this);
        tvLive.setOnClickListener(this);
        tvRegular.setOnClickListener(this);
        tvCombo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.tv_live_manager_offer:
                setBackGround(R.id.tv_live_manager_offer);
                fragmentUtils.changeFragment(fragmentManagerOffersLive);
                setColorLine(R.id.layout_line_live_customer);
                break;
            case R.id.tv_regular_manager_offer:
                setBackGround(R.id.tv_regular_manager_offer);
                fragmentUtils.changeFragment(fragmentManagerOffersRegular);
                setColorLine(R.id.layout_line_regular_offer);
                break;
            case R.id.tv_combo_manager_offer:
                setBackGround(R.id.tv_combo_manager_offer);
                fragmentUtils.changeFragment(fragmentManagerOfferCombo);
                setColorLine(R.id.layout_line_combo_offer);
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
