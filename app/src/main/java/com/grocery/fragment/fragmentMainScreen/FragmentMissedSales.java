package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.adapter.MissedSalesAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.model.ItemOrders;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 30/06/2017.
 */

public class FragmentMissedSales extends Fragment {

    private MissedSalesAdapter missedSalesAdapter;
    private CRecyclerView listMissedSales;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.missed_sales, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        loadMore();
    }

    private void initListener() {
        listMissedSales.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isReadyForPullEnd(recyclerView) && isCanNext && !isProgessingLoadMore) {
                    loadMore();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                cleanAdapter();
                loadMore();
            }
        });
    }

    private void cleanAdapter() {
        page_number = 0;
        missedSalesAdapter.clear();
    }

    private void loadMore() {
        ArrayList<ItemOrders> listData = new ArrayList<>();
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

        missedSalesAdapter.addAll(listData);
    }

    private void initView(View view) {
        listMissedSales = (CRecyclerView) view.findViewById(R.id.listMissedSales);
        missedSalesAdapter = new MissedSalesAdapter(getActivity(), new ArrayList<ItemOrders>());
        listMissedSales.setAdapter(missedSalesAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
    }
}
