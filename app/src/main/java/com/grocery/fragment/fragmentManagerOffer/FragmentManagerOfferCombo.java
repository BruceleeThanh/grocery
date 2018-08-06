package com.grocery.fragment.fragmentManagerOffer;

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
import com.grocery.fragment.fragmentComboOffer.FragmentManageOfferComboCreat;
import com.grocery.fragment.fragmentComboOffer.FragmentManageOfferComboView;
import com.grocery.utils.FragmentUtils;
import com.grocery.utils.HideSoftKeyBroad;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 05/07/2017.
 */

public class FragmentManagerOfferCombo extends Fragment implements View.OnClickListener {
    public static boolean RELOAD_FRM = false;
    private ArrayList<TextView> arrTv;
    private CustomTextView tvCreat;
    private CustomTextView tvView;
    private FragmentUtils fragmentUtils;
    private LinearLayout layoutManagerComboSearch;
    private MainActivity mainActivity;
    private FragmentManageOfferComboCreat fragmentManageOfferComboCreat;
    private FragmentManageOfferComboView fragmentManageOfferComboView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_offers_combo, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        arrTv = new ArrayList<>();
        arrTv.add(tvCreat);
        arrTv.add(tvView);
        setBackGround(R.id.tv_combo_manager_offer_creat);
        fragmentUtils.addToFragment(fragmentManageOfferComboCreat);
    }

    private void initListener() {
        tvCreat.setOnClickListener(this);
        tvView.setOnClickListener(this);
    }

    private void initView(View view) {
        mainActivity = (MainActivity) getActivity();
        tvCreat = (CustomTextView) view.findViewById(R.id.tv_combo_manager_offer_creat);
        tvView = (CustomTextView) view.findViewById(R.id.tv_combo_manager_offer_view);
        layoutManagerComboSearch = view.findViewById(R.id.layout_manager_combo_search);
        /*inti fragment*/
        fragmentUtils = new FragmentUtils(getChildFragmentManager(), R.id.show_frag_manage_offer_combo);
        fragmentManageOfferComboCreat = new FragmentManageOfferComboCreat();
        fragmentManageOfferComboView = new FragmentManageOfferComboView();
    }

    public void setBackGround(int id) {
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
            if (arrTv.get(i).getId() == id) {
                arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            }
        }
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.tv_combo_manager_offer_creat:
                setBackGround(R.id.tv_combo_manager_offer_creat);
                fragmentUtils.changeFragment(fragmentManageOfferComboCreat);
                layoutManagerComboSearch.setVisibility(View.GONE);
                break;
            case R.id.tv_combo_manager_offer_view:
                setBackGround(R.id.tv_combo_manager_offer_view);
                layoutManagerComboSearch.setVisibility(View.VISIBLE);
                if (RELOAD_FRM) {
                    fragmentUtils.changeFragment(new FragmentManageOfferComboView());
                } else {
                    fragmentUtils.changeFragment(fragmentManageOfferComboView);
                }
                break;
        }
    }
}
