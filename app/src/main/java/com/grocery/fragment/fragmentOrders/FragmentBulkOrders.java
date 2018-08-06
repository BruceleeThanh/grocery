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
import com.grocery.adapter.BulkOrdersAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.controller.Controller;
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

public class FragmentBulkOrders extends Fragment {

    private static final int BULK_ORDER = 3;
    private BulkOrdersAdapter bulkOrderAdapter;
    private CRecyclerView lvBulkOrder;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isReLoadBulk = false;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bulk_orders, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isReLoadBulk) {
            Config.statusTypeOrder = 3;
            bulkOrderAdapter.clear();
            page_number = 0;
            loadMore();
            isReLoadBulk = false;
        }
    }

    private void initListener() {
        lvBulkOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                bulkOrderAdapter.clear();
                updateTitleFragment();
                page_number = 0;
                mainActivity.cleanSearch();
            }
        });
    }

    public void cleanAdapter() {
        bulkOrderAdapter.clear();
        page_number = 0;
    }

    public void updateTitleFragment() {
        mainActivity.loadMenu();
    }

    public void loadMore() {
        getAllOrders(Config.AdminId, Config.apiToken, BULK_ORDER, Config.FILTER, Config.txtSearchFlat,
                Config.txtSearchId, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lvBulkOrder = (CRecyclerView) view.findViewById(R.id.lv_bulk_order);
        lvBulkOrder.setNestedScrollingEnabled(false);
        bulkOrderAdapter = new BulkOrdersAdapter(getActivity(), new ArrayList<ItemOrders>());
        lvBulkOrder.setAdapter(bulkOrderAdapter);
        loadMore();
    }

    private void getAllOrders(int userID, String apiToken, int order_type, int filter_type, String flat_search,
                              String order_id_search, final int page_size, int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        Controller controller = new Controller();
        Call<OrdersResponse> call = controller.service.getAllOders(userID, apiToken, order_type, filter_type,
                flat_search, order_id_search, page_size, page_number, is_groceryAdmin);
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
                            bulkOrderAdapter.addAll(ordersResponse.result);
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
