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
import com.grocery.fragment.fragmentAllProduct.FragmentProBrand;
import com.grocery.fragment.fragmentAllProduct.FragmentProManageCategory;
import com.grocery.fragment.fragmentAllProduct.FragmentProManageProduct;
import com.grocery.fragment.fragmentAllProduct.FragmentProSubCategory;
import com.grocery.utils.FragmentUtils;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentAllProducts extends Fragment implements View.OnClickListener {
    private CustomTextView tvProManagerCategory;
    private CustomTextView tvProSubCategory;
    private CustomTextView tvProBrands;
    private CustomTextView tvProManagerProducts;
    private ArrayList<TextView> arrTv;

    private LinearLayout layoutLineManageCategory;
    private LinearLayout layoutLineManageProduct;
    private LinearLayout layoutLineBrand;
    private LinearLayout layoutLineSubCategory;
    private ArrayList<LinearLayout> arrLayoutLine;

    private LinearLayout viewMain;
    private MainActivity mainActivity;
    private FragmentUtils fragmentUtils;
    private FragmentProBrand fragmentProBrand;
    private FragmentProManageProduct fragmentProManageProduct;
    private FragmentProSubCategory fragmentProSubCategory;
    private FragmentProManageCategory fragmentProManageCategory;
    public static boolean isProgessingLoadMore = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_frag, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        viewMain = (LinearLayout) view.findViewById(R.id.layout_all_products);
        mainActivity = (MainActivity) getActivity();

        // anh xa cac tv title
        tvProBrands = (CustomTextView) view.findViewById(R.id.tv_pro_manage_brands);
        tvProManagerCategory = (CustomTextView) view.findViewById(R.id.tv_pro_manage_category);
        tvProManagerProducts = (CustomTextView) view.findViewById(R.id.tv_pro_manage_products);
        tvProSubCategory = (CustomTextView) view.findViewById(R.id.tv_pro_sub_category);
        arrTv = new ArrayList<>();
        arrTv.add(tvProManagerCategory);
        arrTv.add(tvProSubCategory);
        arrTv.add(tvProBrands);
        arrTv.add(tvProManagerProducts);
        fragmentUtils = new FragmentUtils(getChildFragmentManager(), R.id.products_list_frag);
        fragmentProBrand = new FragmentProBrand();
        fragmentProManageCategory = new FragmentProManageCategory();
        fragmentProManageProduct = new FragmentProManageProduct();
        fragmentProSubCategory = new FragmentProSubCategory();
        fragmentUtils.addToFragment(fragmentProManageCategory);
        updateBackGroundTv(R.id.tv_pro_manage_category);

        // anh xa cac line
        layoutLineBrand = (LinearLayout) view.findViewById(R.id.layout_line_manage_brands);
        layoutLineManageCategory = (LinearLayout) view.findViewById(R.id.layout_line_manage_category);
        layoutLineManageProduct = (LinearLayout) view.findViewById(R.id.layout_line_manage_products);
        layoutLineSubCategory = (LinearLayout) view.findViewById(R.id.layout_line_sub_category);
        arrLayoutLine = new ArrayList<>();
        arrLayoutLine.add(layoutLineBrand);
        arrLayoutLine.add(layoutLineManageCategory);
        arrLayoutLine.add(layoutLineManageProduct);
        arrLayoutLine.add(layoutLineSubCategory);
        setColorLine(R.id.layout_line_manage_category);
    }

    public void setColorLine(int id) {
        for (int i = 0; i < arrLayoutLine.size(); i++) {
            arrLayoutLine.get(i).setBackgroundColor(getResources().getColor(R.color.colorApp));
            if (arrLayoutLine.get(i).getId() == id) {
                arrLayoutLine.get(i).setBackgroundColor(0);
            }
        }
    }

    public void updateBackGroundTv(int id) {
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setTextColor(Color.WHITE);
            arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
            if (arrTv.get(i).getId() == id) {
                arrTv.get(i).setTextColor(getResources().getColor(R.color.colorApp));
                arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_item));
            }
        }
    }

    private void initListener() {
        tvProBrands.setOnClickListener(this);
        tvProSubCategory.setOnClickListener(this);
        tvProManagerProducts.setOnClickListener(this);
        tvProManagerCategory.setOnClickListener(this);
        viewMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isProgessingLoadMore) {
            return;
        }
        switch (v.getId()) {
            case R.id.layout_all_products:
                break;
            case R.id.tv_pro_manage_brands:
                fragmentUtils.changeFragment(fragmentProBrand);
                updateBackGroundTv(v.getId());
                setColorLine(R.id.layout_line_manage_brands);
                break;
            case R.id.tv_pro_manage_category:
                fragmentUtils.changeFragment(fragmentProManageCategory);
                updateBackGroundTv(v.getId());
                setColorLine(R.id.layout_line_manage_category);
                break;
            case R.id.tv_pro_manage_products:
                fragmentUtils.changeFragment(fragmentProManageProduct);
                updateBackGroundTv(v.getId());
                setColorLine(R.id.layout_line_manage_products);
                break;
            case R.id.tv_pro_sub_category:
                fragmentUtils.changeFragment(fragmentProSubCategory);
                setColorLine(R.id.layout_line_manage_brands);
                updateBackGroundTv(v.getId());
                setColorLine(R.id.layout_line_sub_category);
                break;
        }
    }
}
