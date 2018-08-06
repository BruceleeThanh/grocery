package com.grocery.fragment.fragmentOrders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.NewOrderAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentMainScreen.FragmentOrders;
import com.grocery.model.ItemOrders;
import com.grocery.response.OrdersResponse;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 20/06/2017.
 */

public class FragmentNewOrders extends Fragment {

    private static final int NEW_ORDERS = 0;
    private NewOrderAdapter newOrderAdapter;
    private CRecyclerView lvNewOrder;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    public static boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_orders, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        initListener();
        return view;
    }

    public void filter(int typeSort) {
        Config.FILTER = typeSort;
        newOrderAdapter.clear();
        page_number = 0;
        loadMore();
    }

    public void cleanFilterCheckBox() {
        for (int i = 0; i < FragmentOrders.arrCb.size(); i++) {
            FragmentOrders.arrCb.get(i).setSelected(false);
        }
        FragmentOrders.arrStatusFilterfrm[Config.statusTypeOrder] = 0;
        Config.FILTER = 0;
    }

    private void initListener() {
        lvNewOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                mainActivity.loadMenu();
                mainActivity.updateTitleFragmentOrder(mainActivity.getInforOrdersModel());
                cleanAdapter();
                cleanFilterCheckBox();
                mainActivity.cleanSearch();
            }
        });
    }

    public void cleanAdapter() {
        newOrderAdapter.clear();
        page_number = 0;
    }

    public void loadMore() {
        getAllOrders(Config.AdminId, Config.apiToken, NEW_ORDERS, Config.FILTER, Config.txtSearchFlat,
                Config.txtSearchId, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lvNewOrder = (CRecyclerView) view.findViewById(R.id.lv_new_order);
        lvNewOrder.setNestedScrollingEnabled(false);
        newOrderAdapter = new NewOrderAdapter(getActivity(), new ArrayList<ItemOrders>());
        lvNewOrder.setAdapter(newOrderAdapter);
        loadMore();
    }

    private IntentFilter makeIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_RELOAD_DATA);
        return filter;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            String action = intent.getAction();
            if (action.equals(Config.ACTION_RELOAD_DATA)) {
                newOrderAdapter.clear();
                page_number = 0;
                loadMore();
                Config.isUpdateOrder = false;
                FragmentPreparingOrders.isReLoadPreparing = true;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, makeIntentFilter());
        if (Config.isUpdateOrder) {
            newOrderAdapter.clear();
            page_number = 0;
            loadMore();
            mainActivity.loadMenu();
            mainActivity.updateTitleFragmentOrder(mainActivity.getInforOrdersModel());
            Config.isUpdateOrder = false;
            FragmentPreparingOrders.isReLoadPreparing = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void getAllOrders(int userID, String apiToken, int order_type, int filter_type, String flat_search,
                              String order_id_search, final int page_size, int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        Controller controller = new Controller();
        Call<OrdersResponse> call = controller.service.getAllOders(userID, apiToken, order_type,
                filter_type, flat_search, order_id_search, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Response<OrdersResponse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    OrdersResponse ordersResponse = response.body();
                    if (ordersResponse != null) {
                        if (ordersResponse.code == 200) {
                            if (ordersResponse.result.size() == page_size) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            newOrderAdapter.addAll(ordersResponse.result);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
