package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentLiveCustomer.FragmentLiveCusAllCategory;
import com.grocery.fragment.fragmentLiveCustomer.FragmentLiveCusItemCategory;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentLiveCustomer extends Fragment implements View.OnClickListener {

    private FragmentLiveCusAllCategory fragmentLiveCusAllCategory;
    private FragmentLiveCusItemCategory fragmentLiveCusItemCategory;
    private MainActivity mMainActivity;

    private CustomTextView tvMakeOffers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_customer, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        tvMakeOffers.setOnClickListener(this);
    }

    private void initView(View view) {
        mMainActivity = (MainActivity) getActivity();
        mMainActivity.showLayoutHome(true);
        tvMakeOffers = (CustomTextView) view.findViewById(R.id.btn_make_offer_live_cus);
        fragmentLiveCusAllCategory = new FragmentLiveCusAllCategory();
        fragmentLiveCusItemCategory = new FragmentLiveCusItemCategory();
        replaceFragment(new FragmentLiveCusAllCategory());
    }

    /*public void changedFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.list_fragment_live_customers, fragment);
        mainActivity.setFragmentTMP(fragment);
        transaction.commit();
    }*/

    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getChildFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
            ft.add(R.id.list_fragment_live_customers, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public FragmentLiveCusAllCategory getFragmentLiveCusAllCategory() {
        return fragmentLiveCusAllCategory;
    }

    public FragmentLiveCusItemCategory getFragmentLiveCusItemCategory() {
        return fragmentLiveCusItemCategory;
    }

    @Override
    public void onClick(View v) {

    }
}
