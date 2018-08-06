package com.grocery.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.grocery.R;
import com.grocery.activity.MainViewOrders;
import com.grocery.adapter.AlternaviteAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentViewOrders.FragmentViewBulkSchduleOrder;
import com.grocery.fragment.fragmentViewOrders.FragmentViewNormalOrder;
import com.grocery.model.ItemAlternavite;
import com.grocery.model.ItemProductsOrders;
import com.grocery.response.AlternativeResponse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 04/10/2017.
 */

public class DialogAlternativeProducts extends Dialog implements View.OnClickListener {
    private CustomTextView tvCancel;
    private CRecyclerView lView;
    private AlternaviteAdapter alternaviteAdapter;
    private int page_number = 0;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static ItemProductsOrders itemProductsOrders;
    private LinearLayout search;
    private RelativeLayout layoutHide;
    private LinearLayout layoutMain;
    private String text_search = "";
    private CustomEditText edtSearch;
    private CustomTextView tvChoose;
    private MainViewOrders mainViewOrders;
    private ArrayList<ItemAlternavite> arrSelect = new ArrayList<>();

    public DialogAlternativeProducts(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_alternative_products);
        if (context instanceof MainViewOrders) {
            setOwnerActivity((MainViewOrders) context);
        }
        mainViewOrders = (MainViewOrders) getOwnerActivity();
        initView();
        initData();
        initListener();
    }

    private void initData() {
        ArrayList<ItemProductsOrders> arrProductsOrder = new ArrayList<>();
        if (mainViewOrders.getItemOrders().getOrder_type() == 0 || mainViewOrders.getItemOrders().getOrder_type() == 1 || mainViewOrders.getItemOrders().getOrder_type() == 2) {
            arrProductsOrder = (ArrayList<ItemProductsOrders>) FragmentViewNormalOrder.viewProductsOrderAdapter.getList();
            arrSelect = FragmentViewNormalOrder.arrRepleace[getPositionOrderRepalece(itemProductsOrders.getId(), arrProductsOrder)];
        } else if (mainViewOrders.getItemOrders().getOrder_type() == 3 || mainViewOrders.getItemOrders().getOrder_type() == 4) {
            arrProductsOrder = (ArrayList<ItemProductsOrders>) FragmentViewBulkSchduleOrder.viewProductsOrderAdapter.getList();
            arrSelect = FragmentViewBulkSchduleOrder.arrRepleace[getPositionOrderRepalece(itemProductsOrders.getId(), arrProductsOrder)];
        }
        alternaviteAdapter = new AlternaviteAdapter(getContext(), new ArrayList<ItemAlternavite>(), this, arrSelect);
        lView.setAdapter(alternaviteAdapter);
    }

    private void initListener() {
        tvCancel.setOnClickListener(this);
        search.setOnClickListener(this);
        tvChoose.setOnClickListener(this);
        lView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

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
                page_number = 0;
                text_search = "";
                alternaviteAdapter.clear();
                edtSearch.setText("");
                loadMore();
            }
        });
        loadMore();
    }

    public void hideLayout(boolean check) {
        if (!check) {
            layoutHide.setVisibility(View.GONE);
            layoutMain.setBackgroundResource(R.drawable.radius_edt);
        } else {
            layoutHide.setVisibility(View.VISIBLE);
            layoutMain.setBackgroundResource(0);
        }
    }

    private void loadMore() {
        getAlternavite(getContext(), Config.AdminId, Config.apiToken,
                itemProductsOrders.getProduct_id(), itemProductsOrders.getBrand_id(), Config.PAGE_SIZE,
                ++page_number, Config.is_groceryAdmin, text_search);
    }

    private void initView() {
        tvCancel = (CustomTextView) findViewById(R.id.tvCancel);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        lView = (CRecyclerView) findViewById(R.id.listAlternavite);
        search = (LinearLayout) findViewById(R.id.search);
        layoutHide = (RelativeLayout) findViewById(R.id.layoutHide);
        layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        edtSearch = (CustomEditText) findViewById(R.id.edtSearch);
        tvChoose = (CustomTextView) findViewById(R.id.tvChoose);
    }

    public void setStatusChoosebtn(boolean check) {
        if (check) {
            tvChoose.setEnabled(true);
            tvChoose.setTextColor(Color.WHITE);
            tvChoose.setBackgroundResource(R.drawable.btn_open_again_shop);
        } else {
            tvChoose.setEnabled(false);
            tvChoose.setTextColor(getContext().getResources().getColor(R.color.text_save));
            tvChoose.setBackgroundResource(R.drawable.radius_list);
        }
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getOwnerActivity(), getCurrentFocus());
        switch (v.getId()) {
            case R.id.tvCancel:
                cancel();
                break;
            case R.id.search:
                if (edtSearch.getText().toString().isEmpty()) {
                    text_search = "";
                } else {
                    text_search = edtSearch.getText().toString();
                }
                page_number = 0;
                alternaviteAdapter.clear();
                loadMore();
                hideLayout(false);
                break;
            case R.id.tvChoose:
                ArrayList<ItemProductsOrders> arrProductsOrder = new ArrayList<>();
                if (mainViewOrders.getItemOrders().getOrder_type() == 0 || mainViewOrders.getItemOrders().getOrder_type() == 1 || mainViewOrders.getItemOrders().getOrder_type() == 2) {
                    arrProductsOrder = (ArrayList<ItemProductsOrders>) FragmentViewNormalOrder.viewProductsOrderAdapter.getList();
                    FragmentViewNormalOrder.arrRepleace[getPositionOrderRepalece(itemProductsOrders.getId(), arrProductsOrder)] = getListSelect((ArrayList<ItemAlternavite>) alternaviteAdapter.getList());
                } else if (mainViewOrders.getItemOrders().getOrder_type() == 3 || mainViewOrders.getItemOrders().getOrder_type() == 4) {
                    arrProductsOrder = (ArrayList<ItemProductsOrders>) FragmentViewBulkSchduleOrder.viewProductsOrderAdapter.getList();
                    FragmentViewBulkSchduleOrder.arrRepleace[getPositionOrderRepalece(itemProductsOrders.getId(), arrProductsOrder)] = getListSelect((ArrayList<ItemAlternavite>) alternaviteAdapter.getList());
                }
                cancel();
                break;
        }
    }

    public int getPositionOrderRepalece(int id, ArrayList<ItemProductsOrders> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<ItemAlternavite> getListSelect(ArrayList<ItemAlternavite> arr) {
        ArrayList<ItemAlternavite> arrayList = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).ischoose()) {
                arrayList.add(arr.get(i));
            }
        }
        return arrayList;
    }

    private void getAlternavite(Context context, int userID, String apiToken, int product_id, int brand_id,
                                int page_size, int page_number, int is_groceryAdmin, String text_search) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<AlternativeResponse> call = controller.service.getAlternavite(userID, apiToken, product_id,
                brand_id, page_size, page_number, is_groceryAdmin, text_search);
        call.enqueue(new Callback<AlternativeResponse>() {
            @Override
            public void onResponse(Response<AlternativeResponse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                dialog.dismiss();
                if (response != null) {
                    AlternativeResponse alternativeResponse = response.body();
                    if (alternativeResponse != null) {
                        if (alternativeResponse.code == 200) {
                            if (alternativeResponse.result.size() == Config.PAGE_SIZE) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            alternaviteAdapter.addAll(alternativeResponse.result);
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
