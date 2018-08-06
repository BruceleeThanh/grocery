package com.grocery.fragment.fragmentOrders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.DespatchOrderAdapter;
import com.grocery.adapter.PreparingOrderAdapter;
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
 * Created by ThanhBeo on 20/06/2017.
 */

public class FragmentDespatchedOrders extends Fragment {
    private static final int DESPATCH_ORDER = 4;
    public static DespatchOrderAdapter despatchOrderAdapter;
    private CRecyclerView lvDespatchOreder;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isReLoadDespatch = false;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.despatched_order, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        loadData();
        initListener();
        return view;
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

    public void loadData() {
        PreparingOrderAdapter.setOnClickListener(new PreparingOrderAdapter.OnClickListener() {
            @Override
            public void onClicked() {
                cleanAdapter();
                loadMore();
            }
        });
        FragmentViewDespatchDeliveredOrder.setOnItemClickListener(new FragmentViewDespatchDeliveredOrder.OnClickListener() {
            @Override
            public void onItemClicked2() {
                if (Config.statusTypeOrder != 4) {
                    return;
                }
                if (isCanNext && !isProgessingLoadMore) {
                    loadMore();
                }
            }
        });

    }

    @Override
    public void onResume() {
        if (isReLoadDespatch) {
            despatchOrderAdapter.clear();
            page_number = 0;
            loadMore();
            isReLoadDespatch = false;
        }
        super.onResume();
    }

    public void updateTitleFragment() {
        mainActivity.loadMenu();
    }

    public FragmentManager getFragment() {
        return getFragmentManager();
    }

    private void initListener() {
        lvDespatchOreder.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                despatchOrderAdapter.clear();
                page_number = 0;
                cleanFilterCheckBox();
                mainActivity.cleanSearch();
            }
        });
    }

    public void cleanAdapter() {
        despatchOrderAdapter.clear();
        page_number = 0;
    }


    public void loadMore() {
        getAllOrders(Config.AdminId, Config.apiToken, DESPATCH_ORDER, Config.FILTER, Config.txtSearchFlat,
                Config.txtSearchId, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lvDespatchOreder = (CRecyclerView) view.findViewById(R.id.lv_despatch_order);
        lvDespatchOreder.setNestedScrollingEnabled(false);
        despatchOrderAdapter = new DespatchOrderAdapter(getActivity(), new ArrayList<ItemOrders>(), this);
        lvDespatchOreder.setAdapter(despatchOrderAdapter);
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
                            despatchOrderAdapter.addAll(ordersResponse.result);
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