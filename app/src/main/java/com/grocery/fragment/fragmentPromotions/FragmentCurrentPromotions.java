package com.grocery.fragment.fragmentPromotions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.adapter.CurrentPromotionAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemOrders;
import com.grocery.utils.ProgerssBarUntil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DangTin on 02/07/2018.
 */

public class FragmentCurrentPromotions extends Fragment implements View.OnClickListener {

    private ProgerssBarUntil progerssBarUntil;
    private LinearLayout layoutSearch;
    private LinearLayout layoutSearchExpan;
    private ImageView imgCloseLayoutSearch;
    private LinearLayout btnSearch;
    private CRecyclerView lvPromotion;
    private CurrentPromotionAdapter currentPromotionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_promotions, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {

    }

    private void initListener() {
        btnSearch.setOnClickListener(this);
        imgCloseLayoutSearch.setOnClickListener(this);
    }


    private void initView(View view) {
        layoutSearch = view.findViewById(R.id.layout_search);
        layoutSearchExpan = view.findViewById(R.id.layout_search_expan);
        imgCloseLayoutSearch = view.findViewById(R.id.img_close_layout_search);
        btnSearch = view.findViewById(R.id.btn_search);
        lvPromotion = view.findViewById(R.id.lv_promotion);
        currentPromotionAdapter = new CurrentPromotionAdapter(getActivity(), fakeData());
        lvPromotion.setAdapter(currentPromotionAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                layoutSearch.setBackgroundResource(R.color.despatched_orders);
                layoutSearchExpan.setVisibility(View.VISIBLE);
                break;
            case R.id.img_close_layout_search:
                layoutSearch.setBackgroundResource(R.color.colorWhite);
                layoutSearchExpan.setVisibility(View.GONE);
                break;
        }
    }

    private List<ItemOrders> fakeData() {
        List<ItemOrders> listData = new ArrayList<>();
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());
        listData.add(new ItemOrders());

        return listData;
    }
}
