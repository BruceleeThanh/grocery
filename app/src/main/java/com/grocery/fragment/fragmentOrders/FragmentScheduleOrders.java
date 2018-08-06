package com.grocery.fragment.fragmentOrders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.ScheduleOrderAdapter;
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

public class FragmentScheduleOrders extends Fragment implements View.OnClickListener {

    private static final int SCHEDULE_ORDER = 2;
    private ScheduleOrderAdapter scheduleOrderAdapter;
    private CRecyclerView lvSchedule;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isReLoadSchedule = false;
    private MainActivity mainActivity;

    private LinearLayout viewMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_orders, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isReLoadSchedule) {
            Config.statusTypeOrder = 2;
            scheduleOrderAdapter.clear();
            updateTitleFragment();
            page_number = 0;
            loadMore();
            isReLoadSchedule = false;
        }
    }

    public void updateTitleFragment() {
        mainActivity.loadMenu();
    }

    private void initListener() {
        viewMain.setOnClickListener(this);
        lvSchedule.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                scheduleOrderAdapter.clear();
                page_number = 0;
                mainActivity.cleanSearch();
            }
        });
    }


    public void cleanAdapter() {
        scheduleOrderAdapter.clear();
        page_number = 0;
    }

    public void loadMore() {
        getAllOrders(Config.AdminId, Config.apiToken, SCHEDULE_ORDER, Config.FILTER, Config.txtSearchFlat,
                Config.txtSearchId, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        viewMain = (LinearLayout) view.findViewById(R.id.layout_schedule_orders);
        lvSchedule = (CRecyclerView) view.findViewById(R.id.lv_schedule_order);
        lvSchedule.setNestedScrollingEnabled(false);
        scheduleOrderAdapter = new ScheduleOrderAdapter(getActivity(), new ArrayList<ItemOrders>());
        lvSchedule.setAdapter(scheduleOrderAdapter);
        loadMore();
    }

    @Override
    public void onClick(View v) {

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
                            scheduleOrderAdapter.addAll(ordersResponse.result);
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
