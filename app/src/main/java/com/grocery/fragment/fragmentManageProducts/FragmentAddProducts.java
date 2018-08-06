package com.grocery.fragment.fragmentManageProducts;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.ManageProductsAddAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DiaglogSave;
import com.grocery.fragment.fragmentAllProduct.FragmentProManageProduct;
import com.grocery.model.ItemManagerProductsAdd;
import com.grocery.request.UpdateManagerProductsRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.MangerProductsToAddResponse;
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

public class FragmentAddProducts extends Fragment implements View.OnClickListener {
    private static final int PAGE_SIZE = 15;
    public static int ID_CATEGORY = 0;
    public static int ID_SUB_CATEGORY = 0;
    public static int ID_BRAND = 0;
    public static String TEXT_SEARCH = "";
    private CRecyclerView lvAdd;
    private ManageProductsAddAdapter manageProductsAddAdapter;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    public static int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CustomTextView tvSave;
    private CustomTextView tvNumber;
    private CustomTextView tvSelect;
    private ImageView imRound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_manage_pro_add, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListner();
        return view;
    }

    private void initView(View view) {
        FragmentAddProducts.TEXT_SEARCH = "";
        FragmentAddProducts.page_number = 0;
        tvNumber = (CustomTextView) view.findViewById(R.id.tv_number_select_add_products);
        tvSave = (CustomTextView) view.findViewById(R.id.btn_save_manager_category);
        tvSelect = (CustomTextView) view.findViewById(R.id.tvSelect);
        imRound = (ImageView) view.findViewById(R.id.im_round_selected);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        manageProductsAddAdapter = new ManageProductsAddAdapter(getActivity(), new ArrayList<ItemManagerProductsAdd>(), this);
        lvAdd = (CRecyclerView) view.findViewById(R.id.lv_manage_pro_add);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL, false);
        lvAdd.setLayoutManager(gridLayoutManager);
        lvAdd.setAdapter(manageProductsAddAdapter);
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
        tvSave.setOnClickListener(this);
    }

    public void cleanAdapter() {
        manageProductsAddAdapter.clear();
        page_number = 0;
        tvSave.setEnabled(false);
        tvSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
        tvSave.setTextColor(getResources().getColor(R.color.text_save));
        tvNumber.setTextColor(getResources().getColor(R.color.text_save));
        tvSelect.setTextColor(getResources().getColor(R.color.text_save));
        imRound.setBackgroundResource(R.mipmap.icon_selected);
        tvNumber.setText(0 + "");
        ManageProductsAddAdapter.arrAdd = new ArrayList<ItemManagerProductsAdd>();
        ManageProductsAddAdapter.arrDelete = new ArrayList<ItemManagerProductsAdd>();
    }

    public void loadMore() {
        getListManageProductsToAdd(Config.AdminId, Config.apiToken, ID_CATEGORY, ID_SUB_CATEGORY,
                ID_BRAND, TEXT_SEARCH, PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }


    public void updateLayoutSelectes(int count) {
        if (count == 0) {
            tvSave.setEnabled(false);
            tvSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
            tvSave.setTextColor(getResources().getColor(R.color.text_save));
            tvNumber.setTextColor(getResources().getColor(R.color.text_save));
            tvSelect.setTextColor(getResources().getColor(R.color.text_save));
            imRound.setBackgroundResource(R.mipmap.icon_selected);
        } else {
            tvSave.setEnabled(true);
            tvSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            tvSave.setTextColor(Color.WHITE);
            tvNumber.setTextColor(getResources().getColor(R.color.colorApp));
            tvSelect.setTextColor(getResources().getColor(R.color.colorApp));
            imRound.setBackgroundResource(R.mipmap.icon_selected_2);
        }
        tvNumber.setText(count + "");
    }

    @Override
    public void onClick(View v) {
        if (FragmentProManageProduct.isProgessing) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_save_manager_category:
                Config.number_update = Integer.parseInt(String.valueOf(tvNumber.getText()));
                DiaglogSave.title = getString(R.string.product_update);
                DiaglogSave diaglogSave = new DiaglogSave(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                diaglogSave.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                diaglogSave.show();
                diaglogSave.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (Config.checkUpdate) {
                            UpdateManagerProductsRequest updateManagerProductsRequest = new UpdateManagerProductsRequest(Config.AdminId,
                                    Config.apiToken, converListStringId(ManageProductsAddAdapter.arrDelete), converListStringId(ManageProductsAddAdapter.arrAdd), Config.is_groceryAdmin);
                            updateManageProducts(getActivity(), updateManagerProductsRequest);
                        }
                        Config.checkUpdate = false;
                        Config.number_update = 0;
                    }
                });
                break;

        }
    }

    public String converListStringId(ArrayList<ItemManagerProductsAdd> arrayList) {
        String id = "";
        for (int i = 0; i < arrayList.size(); i++) {
            if (id.length() == 0) {
                id = arrayList.get(i).getId() + "";
            } else {
                id += "," + arrayList.get(i).getId();
            }
        }
        return id;
    }

    private void getListManageProductsToAdd(int userID, String apiToken,
                                            int category_id, int sub_category_id, int brand_id,
                                            String text_search, int page_size, int page_number,
                                            int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        FragmentProManageProduct.isProgessing = true;

        Controller controller = new Controller();
        Call<MangerProductsToAddResponse> call = controller.service.getListManageProductsToAdd(userID, apiToken,
                category_id, sub_category_id, brand_id, text_search, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<MangerProductsToAddResponse>() {
            @Override
            public void onResponse(Response<MangerProductsToAddResponse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                FragmentProManageProduct.isProgessing = false;
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    MangerProductsToAddResponse mangerProductsToAddResponse = response.body();
                    if (mangerProductsToAddResponse != null) {
                        if (mangerProductsToAddResponse.code == 200) {
                            if (mangerProductsToAddResponse.result.size() == PAGE_SIZE) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            manageProductsAddAdapter.addAll(mangerProductsToAddResponse.result);
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

    private void updateManageProducts(final Context context, UpdateManagerProductsRequest updateManagerProductsRequest) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateManageProducts(updateManagerProductsRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            if (baseResponse.code == 200) {
                                cleanAdapter();
                                loadMore();
                            }
                            Toast.makeText(context, baseResponse.message.toString(), Toast.LENGTH_SHORT).show();
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
