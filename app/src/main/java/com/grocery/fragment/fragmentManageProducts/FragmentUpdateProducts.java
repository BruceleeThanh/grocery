package com.grocery.fragment.fragmentManageProducts;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.adapter.ManagerProductsUpdateAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.RecyclerItemClickListener;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogEditUpdateProduct;
import com.grocery.fragment.fragmentAllProduct.FragmentProManageProduct;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.response.ManagerProductsToUpdatResqonse;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 19/09/2017.
 */

public class FragmentUpdateProducts extends Fragment {
    private static final int PAGE_SIZE = 15;
    private CRecyclerView lvAdd;
    private ManagerProductsUpdateAdapter managerProductsUpdateAdapter;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static int productsId = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_manage_pro_update, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListner();
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        managerProductsUpdateAdapter = new ManagerProductsUpdateAdapter(getActivity(), new ArrayList<ItemManagerProductsUpdate>());
        lvAdd = (CRecyclerView) view.findViewById(R.id.lv_manage_pro_add);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL, false);
        lvAdd.setLayoutManager(gridLayoutManager);
        lvAdd.setAdapter(managerProductsUpdateAdapter);
    }

    private void initData() {
        loadMore();
    }

    private void initListner() {
        lvAdd.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                FragmentProManageProduct.cleanSearch();
                cleanAdapter();
                loadMore();
            }
        });

        lvAdd.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                productsId = managerProductsUpdateAdapter.getList().get(position).getId();
                DialogEditUpdateProduct dialogEditUpdateProduct = new DialogEditUpdateProduct(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogEditUpdateProduct.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogEditUpdateProduct.show();
                dialogEditUpdateProduct.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (DialogEditUpdateProduct.isLoad) {
                            cleanAdapter();
                            loadMore();
                            DialogEditUpdateProduct.isLoad = false;
                        }
                    }
                });
            }
        }));

    }

    public void cleanAdapter() {
        managerProductsUpdateAdapter.clear();
        page_number = 0;
    }

    public void loadMore() {
        getListManageProductsToUpdate(Config.AdminId, Config.apiToken, FragmentAddProducts.ID_CATEGORY,
                FragmentAddProducts.ID_SUB_CATEGORY, FragmentAddProducts.ID_BRAND,
                FragmentAddProducts.TEXT_SEARCH, PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void getListManageProductsToUpdate(int userID, String apiToken,
                                               int category_id, int sub_category_id, int brand_id,
                                               String text_search, int page_size, int page_number,
                                               int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        Controller controller = new Controller();
        Call<ManagerProductsToUpdatResqonse> call = controller.service.getListManageProductsToUpdate(userID, apiToken,
                category_id, sub_category_id, brand_id, text_search, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<ManagerProductsToUpdatResqonse>() {
            @Override
            public void onResponse(Response<ManagerProductsToUpdatResqonse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    ManagerProductsToUpdatResqonse managerProductsToUpdatResqonse = response.body();
                    if (managerProductsToUpdatResqonse != null) {
                        if (managerProductsToUpdatResqonse.code == 200) {
                            if (managerProductsToUpdatResqonse.result.size() == PAGE_SIZE) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            managerProductsUpdateAdapter.addAll(managerProductsToUpdatResqonse.result);
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
