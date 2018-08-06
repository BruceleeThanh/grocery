package com.grocery.fragment.fragmentManageCategory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.CustomTextView;

/**
 * Created by ThanhBeo on 21/07/2017.
 */

public class FragmentManageCategorySuggest extends Fragment implements View.OnClickListener {
    private ImageView imAdd;
    private CustomTextView tvSuggest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_manage_category_suggest,container,false);
        initView(view);
        initListenner();
        return view;
    }

    private void initListenner() {
        imAdd.setOnClickListener(this);
        tvSuggest.setOnClickListener(this);
    }

    private void initView(View view) {
        imAdd = (ImageView) view.findViewById(R.id.im_add_suggest_manage_category);
        tvSuggest = (CustomTextView) view.findViewById(R.id.btn_suggest_products);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_add_suggest_manage_category:
                break;
            case R.id.btn_suggest_products:
                break;
        }
    }
}
