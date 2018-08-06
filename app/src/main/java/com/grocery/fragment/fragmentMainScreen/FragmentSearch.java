package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.SearchOrderAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.model.ItemOrders;
import com.grocery.response.OrdersResponse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 30/06/2017.
 */

public class FragmentSearch extends Fragment implements View.OnClickListener {

    private LinearLayout viewMain;
    private LinearLayout rdFlatNumber;
    private LinearLayout rdOrderId;
    private CustomTextView tvFlatNumber;
    private CustomTextView tvOrderId;
    private LinearLayout layoutFlat;
    private LinearLayout layoutOrder;
    private SearchOrderAdapter searchOrderAdapter;
    private CRecyclerView listOrder;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int typeSearch = 0;
    private String textSearch = "";
    private ImageView btnSearch;
    private CustomEditText edtSearch;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_orders, container, false);
        mainActivity = (MainActivity) getActivity();
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        mainActivity.showLayoutHome(true);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        rdFlatNumber.setSelected(true);
        tvFlatNumber.setTextColor(getResources().getColor(R.color.color_Sat));
        rdOrderId.setSelected(false);
        tvOrderId.setTextColor(getResources().getColor(R.color.text_spiner));
        loadMore();
    }

    private void initListener() {
        viewMain.setOnClickListener(this);
        layoutFlat.setOnClickListener(this);
        layoutOrder.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        listOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        rdFlatNumber.setSelected(true);
        tvFlatNumber.setTextColor(getResources().getColor(R.color.color_Sat));
        rdOrderId.setSelected(false);
        tvOrderId.setTextColor(getResources().getColor(R.color.text_spiner));
        page_number = 0;
        typeSearch = 0;
        textSearch = "";
        edtSearch.setText("");
        searchOrderAdapter.clear();
    }

    private void loadMore() {
        getSearchOrder(Config.AdminId, Config.apiToken, typeSearch, textSearch,
                Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        viewMain = (LinearLayout) view.findViewById(R.id.layout_search_orders);
        rdFlatNumber = (LinearLayout) view.findViewById(R.id.rd_btn_search_flat);
        rdOrderId = (LinearLayout) view.findViewById(R.id.rd_btn_search_id);
        tvFlatNumber = (CustomTextView) view.findViewById(R.id.tv_search_flat);
        tvOrderId = (CustomTextView) view.findViewById(R.id.tv_search_id);
        layoutFlat = (LinearLayout) view.findViewById(R.id.layout_flat_number);
        layoutOrder = (LinearLayout) view.findViewById(R.id.layout_order_id);
        listOrder = (CRecyclerView) view.findViewById(R.id.listSearchOrder);
        searchOrderAdapter = new SearchOrderAdapter(getActivity(), new ArrayList<ItemOrders>());
        listOrder.setAdapter(searchOrderAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        btnSearch = (ImageView) view.findViewById(R.id.btnSearch);
        edtSearch = (CustomEditText) view.findViewById(R.id.edtSearch);
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.layout_flat_number:
                rdFlatNumber.setSelected(true);
                tvFlatNumber.setTextColor(getResources().getColor(R.color.color_Sat));
                rdOrderId.setSelected(false);
                tvOrderId.setTextColor(getResources().getColor(R.color.text_spiner));
                typeSearch = 0;
                break;
            case R.id.layout_order_id:
                rdOrderId.setSelected(true);
                tvOrderId.setTextColor(getResources().getColor(R.color.color_Sat));
                rdFlatNumber.setSelected(false);
                tvFlatNumber.setTextColor(getResources().getColor(R.color.text_spiner));
                typeSearch = 1;
                break;
            case R.id.btnSearch:
                if (edtSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                }
                if (typeSearch != 0) {
                    try {
                        typeSearch = Integer.parseInt(edtSearch.getText().toString());
                        textSearch = "";
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.search_input_number), Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    textSearch = edtSearch.getText().toString();
                }
                searchOrderAdapter.clear();
                page_number = 0;
                loadMore();
                break;
        }
    }

    private void getSearchOrder(int userID, String apiToken, int order_id, String flat_search,
                                final int page_size, int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<OrdersResponse> call = controller.service.getSearchOrder(userID, apiToken, order_id,
                flat_search, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Response<OrdersResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                isProgessingLoadMore = false;
                if (response != null) {
                    OrdersResponse ordersResponse = response.body();
                    if (ordersResponse != null) {
                        if (ordersResponse.code == 200) {
                            if (ordersResponse.result.size() == page_size) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            searchOrderAdapter.addAll(ordersResponse.result);
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
