package com.grocery.fragment.fragmentAllProduct;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentManageCategory.FragmentManageCategoryAdd;
import com.grocery.fragment.fragmentManageCategory.FragmentManageCategorySuggest;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 23/06/2017.
 */

public class FragmentProManageCategory extends Fragment {
    private CustomTextView tvAdd;
    private CustomTextView tvSuggest;
    private ArrayList<TextView> arrTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_manage_category, container, false);
        initView(view);
        initListener(view);
        return view;
    }

    private void initView(View view) {

        tvAdd = (CustomTextView) view.findViewById(R.id.tv_manager_add_category);
        tvSuggest = (CustomTextView) view.findViewById(R.id.tv_manager_suggest_category);

        arrTv = new ArrayList<>();
        arrTv.add(tvAdd);
        arrTv.add(tvSuggest);
        updateBackGroundTv(R.id.tv_manager_add_category);
        replaceFragment(new FragmentManageCategoryAdd());
    }

    private void initListener(View view) {

//        su kien khi click vao cac muc
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateBackGroundTv(v.getId());
                    switch (v.getId()) {
                        case R.id.tv_manager_add_category:
                            replaceFragment(new FragmentManageCategoryAdd());
                            break;
                        case R.id.tv_manager_suggest_category:
                            replaceFragment(new FragmentManageCategorySuggest());
                            break;
                    }
                }
            });
        }
    }

    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getChildFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
            ft.add(R.id.fragment_manage_category, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }


    public void updateBackGroundTv(int id) {
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
            if (arrTv.get(i).getId() == id) {
                arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            }
        }
    }
}
