package com.grocery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.adapter.StockNotifiAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemStockNotifi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DangTin on 30/06/2018.
 */

public class MissedSalesDetail extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitle;
    private ImageView imBack;
    public static TextView tvTime;
    private CRecyclerView listProduct;
    private StockNotifiAdapter stockNotifiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.missed_sales_detail);
        initView();
        initListener();
    }

    private void initListener() {
        imBack.setOnClickListener(this);
    }

    private void initView() {
        tvTime = (TextView) findViewById(R.id.tvTime);
        imBack = (ImageView) findViewById(R.id.imBack);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("Name Item Missed Sales");
        listProduct = findViewById(R.id.listProduct);

        stockNotifiAdapter = new StockNotifiAdapter(this, getListFake());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false);
        listProduct.setLayoutManager(gridLayoutManager);
        listProduct.setAdapter(stockNotifiAdapter);
    }

    private List<ItemStockNotifi> getListFake() {
        List<ItemStockNotifi> list = new ArrayList<>();
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());
        list.add(new ItemStockNotifi());

        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBack:
                onBackPressed();
                break;
        }
    }
}
