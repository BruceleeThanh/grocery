package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.grocery.R;
import com.grocery.adapter.StockNotifiAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemStockNotifi;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentStockNotifi extends Fragment implements View.OnClickListener {
    private CRecyclerView lvStockNotifi;
    private ArrayList<ItemStockNotifi> arrItemStockNotifi;
    private StockNotifiAdapter stockNotifiAdapter;
    private LinearLayout viewMain;
    private ImageView imSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_notifi, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        viewMain.setOnClickListener(this);
        imSearch.setOnClickListener(this);
    }

    private void initView(View view) {
        imSearch = (ImageView) view.findViewById(R.id.im_search_stock_notifi);
        viewMain = (LinearLayout) view.findViewById(R.id.layout_stock_notifi);
        // hien thi list view
        arrItemStockNotifi = new ArrayList<>();
        arrItemStockNotifi.add(new ItemStockNotifi(5, 3, 600, "ABC"));
        arrItemStockNotifi.add(new ItemStockNotifi(6, 4, 700, "SDS"));
        arrItemStockNotifi.add(new ItemStockNotifi(7, 5, 500, "ABC"));
        arrItemStockNotifi.add(new ItemStockNotifi(8, 2, 800, "VXC"));
        arrItemStockNotifi.add(new ItemStockNotifi(8, 1, 900, "ADA"));
        arrItemStockNotifi.add(new ItemStockNotifi(9, 6, 1000, "ABC"));
        arrItemStockNotifi.add(new ItemStockNotifi(10, 3, 400, "RWE"));
        arrItemStockNotifi.add(new ItemStockNotifi(3, 5, 800, "PPP"));
        arrItemStockNotifi.add(new ItemStockNotifi(15, 2, 1400, "AAC"));
        arrItemStockNotifi.add(new ItemStockNotifi(8, 1, 900, "ADA"));
        arrItemStockNotifi.add(new ItemStockNotifi(5, 3, 600, "ABC"));

        lvStockNotifi = (CRecyclerView) view.findViewById(R.id.lv_stock_notifi);
        lvStockNotifi.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                5, GridLayoutManager.VERTICAL, false);
        lvStockNotifi.setLayoutManager(gridLayoutManager);
        stockNotifiAdapter = new StockNotifiAdapter(getActivity(), arrItemStockNotifi);
        lvStockNotifi.setAdapter(stockNotifiAdapter);

        // tao cac spiner
        ArrayList<String> arrayArea = new ArrayList<>();
        arrayArea.add("ABC");
        arrayArea.add("ERT");
        arrayArea.add("OIU");
        arrayArea.add("MAS");
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, arrayArea);
        Spinner spinnerCagegory = (Spinner) view.findViewById(R.id.spn_choose_category);
        spinnerCagegory.setAdapter(adapter);

        Spinner spinnerSubCategory = (Spinner) view.findViewById(R.id.spn_choose_sub_category);
        spinnerSubCategory.setAdapter(adapter);

        Spinner spinnerbrand = (Spinner) view.findViewById(R.id.spn_choose_brand);
        spinnerbrand.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
