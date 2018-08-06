package com.grocery.fragment.fragmentOrders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.DeliveredOrderAdapter;
import com.grocery.adapter.DespatchOrderAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentMainScreen.FragmentOrders;
import com.grocery.fragment.fragmentViewOrders.FragmentViewDespatchDeliveredOrder;
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
 * Created by ThanhBeo on 03/07/2017.
 */

public class FragmentDeliveredOrders extends Fragment {

    private static final int DELIVERED_ORDER = 5;
    public static DeliveredOrderAdapter deliveredOrderAdapter;
    private CRecyclerView lvDelevered;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isReLoadDeleverd = false;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delivered_orders, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        initData();
        loadData();
        initListener();
        return view;
    }

    private void loadData() {
        FragmentViewDespatchDeliveredOrder.setOnItemClickListener(new FragmentViewDespatchDeliveredOrder.OnClickListener() {
            @Override
            public void onItemClicked2() {
                if (Config.statusTypeOrder != 5) {
                    return;
                }
                if (isCanNext && !isProgessingLoadMore) {
                    loadMore();
                }
            }
        });
    }

    private void initData() {
        DespatchOrderAdapter.setOnClickListener(new DespatchOrderAdapter.OnClickListener() {
            @Override
            public void onClicked() {
                cleanAdapter();
                loadMore();
            }
        });
    }

    public void cleanFilterCheckBox() {
        for (int i = 0; i < FragmentOrders.arrCb.size(); i++) {
            FragmentOrders.arrCb.get(i).setSelected(false);
        }
        FragmentOrders.arrStatusFilterfrm[Config.statusTypeOrder] = 0;
        Config.FILTER = 0;
    }

    public void filter(int typeSort) {
        Config.FILTER = typeSort;
        cleanAdapter();
        loadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isReLoadDeleverd) {
            deliveredOrderAdapter.clear();
            page_number = 0;
            loadMore();
            isReLoadDeleverd = false;
        }
    }

    private void initListener() {
        lvDelevered.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                updateTitleFragment();
                deliveredOrderAdapter.clear();
                page_number = 0;
                cleanFilterCheckBox();
                mainActivity.cleanSearch();
            }
        });
    }

    public void updateTitleFragment() {
        mainActivity.loadMenu();
    }


    public void cleanAdapter() {
        deliveredOrderAdapter.clear();
        page_number = 0;
    }


    public void loadMore() {
        getAllOrders(Config.AdminId, Config.apiToken, DELIVERED_ORDER, Config.FILTER, Config.txtSearchFlat,
                Config.txtSearchId, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lvDelevered = (CRecyclerView) view.findViewById(R.id.lv_Delivery_order);
        lvDelevered.setNestedScrollingEnabled(false);
        deliveredOrderAdapter = new DeliveredOrderAdapter(getActivity(), new ArrayList<ItemOrders>());
        lvDelevered.setAdapter(deliveredOrderAdapter);
        loadMore();
    }

    private void getAllOrders(int userID, String apiToken, int order_type, int filter_type,
                              String flat_search, String order_id_search, final int page_size,
                              int page_number, int is_groceryAdmin) {
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
                            deliveredOrderAdapter.addAll(ordersResponse.result);
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
