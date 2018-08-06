package com.grocery.fragment.fragmentManageCategory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.grocery.R;
import com.grocery.adapter.CategoryAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DiaglogSave;
import com.grocery.fragment.fragmentMainScreen.FragmentAllProducts;
import com.grocery.model.AdminModel;
import com.grocery.model.ItemCategory;
import com.grocery.request.UpdateCategoryRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.GetCategoryResponse;
import com.grocery.utils.ProgerssBarUntil;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 21/07/2017.
 */

public class FragmentManageCategoryAdd extends Fragment {
    private CategoryAdapter categoryAdapter;
    private CRecyclerView lvAdd;
    /*private CustomCheckBox cbAdd;
    private CustomCheckBox cbNotAdd;
    private CustomTextView tvCbAdd;
    private CustomTextView tvCbNotAdd;*/

    private CustomTextView tvSave;
    private CustomTextView tvNumber;
    private CustomTextView tvSelect;
    private ImageView imRound;

    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private AdminModel adminModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_manage_category_add, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.number_update = Integer.parseInt(String.valueOf(tvNumber.getText()));
                DiaglogSave.title = getString(R.string.category_update);
                DiaglogSave diaglogSave = new DiaglogSave(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                diaglogSave.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                diaglogSave.show();
                diaglogSave.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (Config.checkUpdate) {
                            UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(Config.AdminId,
                                    Config.apiToken, converListStringId(CategoryAdapter.arrDelete),
                                    converListStringId(CategoryAdapter.arrChoose), Config.is_groceryAdmin);
                            updateCategory(getActivity(), updateCategoryRequest);
                        }
                        Config.checkUpdate = false;
                        Config.number_update = 0;
                    }
                });
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cleanAdapter();
                // cleanCheckBox();
                swipeRefreshLayout.setRefreshing(false);
                categoryAdapter.clear();
                loadMore();
            }
        });

        /*cbAdd.setOncheckListener(new CustomCheckBox.OnCheckListener() {
            @Override
            public void onCheck(CustomCheckBox view, boolean check) {
                updateLayoutCheckBox(R.id.cb_added, check);

                if (!cbNotAdd.isCheck() && !check) {
                    categoryAdapter.filter(Config.arrItemCategory);
                    return;
                }
                ArrayList<ItemCategory> arr = new ArrayList<ItemCategory>();
                for (int i = 0; i < Config.arrItemCategory.size(); i++) {
                    if (Config.arrItemCategory.get(i).getIs_added() == 1) {
                        arr.add(Config.arrItemCategory.get(i));
                    }
                }
                categoryAdapter.filter(arr);
            }
        });

        cbNotAdd.setOncheckListener(new CustomCheckBox.OnCheckListener() {
            @Override
            public void onCheck(CustomCheckBox view, boolean check) {
                updateLayoutCheckBox(R.id.cb_not_add, check);
                cleanAdapter();
                if (!cbAdd.isCheck() && !check) {
                    categoryAdapter.filter(Config.arrItemCategory);
                    return;
                }
                ArrayList<ItemCategory> arr = new ArrayList<ItemCategory>();
                for (int i = 0; i < Config.arrItemCategory.size(); i++) {
                    if (Config.arrItemCategory.get(i).getIs_added() == 0) {
                        arr.add(Config.arrItemCategory.get(i));
                    }
                }
                categoryAdapter.filter(arr);
            }
        });*/
    }

    private void initView(View view) {
        sharedPreferences = getActivity().getSharedPreferences(Config.Pref, getActivity().MODE_PRIVATE);
        String profile = sharedPreferences.getString(Config.KEY_ADMIN_PROFILE, "");
        Gson gson = new Gson();
        adminModel = gson.fromJson(profile, AdminModel.class);

        /*cbAdd = (CustomCheckBox) view.findViewById(R.id.cb_added);
        cbNotAdd = (CustomCheckBox) view.findViewById(R.id.cb_not_add);
        tvCbAdd = (CustomTextView) view.findViewById(R.id.tv_added);
        tvCbNotAdd = (CustomTextView) view.findViewById(R.id.tv_not_add);*/

        lvAdd = (CRecyclerView) view.findViewById(R.id.lv_manage_category_add);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        lvAdd.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(getActivity(), this, new ArrayList<ItemCategory>());
        lvAdd.setAdapter(categoryAdapter);

        tvNumber = (CustomTextView) view.findViewById(R.id.tv_number_select_add_products);
        tvSave = (CustomTextView) view.findViewById(R.id.btn_save_manager_category);
        tvSelect = (CustomTextView) view.findViewById(R.id.tvSelect);
        imRound = (ImageView) view.findViewById(R.id.im_round_selected);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        loadMore();
    }

    /*public void updateLayoutCheckBox(int id, boolean check) {
        switch (id) {
            case R.id.cb_added:
                if (!check) {
                    tvCbAdd.setTextColor(getResources().getColor(R.color.bg_list_frag));
                    break;
                }
                tvCbAdd.setTextColor(getResources().getColor(R.color.colorApp));
                cbNotAdd.setChecked(false);
                tvCbNotAdd.setTextColor(getResources().getColor(R.color.bg_list_frag));
                break;
            case R.id.cb_not_add:
                if (!check) {
                    tvCbNotAdd.setTextColor(getResources().getColor(R.color.bg_list_frag));
                    break;
                }
                tvCbNotAdd.setTextColor(getResources().getColor(R.color.colorApp));
                cbAdd.setChecked(false);
                tvCbAdd.setTextColor(getResources().getColor(R.color.bg_list_frag));
                break;
        }
    }*/

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

    private void loadMore() {
        getAllCategory(adminModel.getId(), adminModel.getApiToken(), adminModel.getId(), Config.is_groceryAdmin);
    }

    public String converListStringId(ArrayList<ItemCategory> arrayList) {
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

    /*public void cleanCheckBox() {
     *//*clean check box*//*
        cbNotAdd.setChecked(false);
        tvCbNotAdd.setTextColor(getResources().getColor(R.color.bg_list_frag));
        cbAdd.setChecked(false);
        tvCbAdd.setTextColor(getResources().getColor(R.color.bg_list_frag));
    }*/

    public void cleanAdapter() {
        categoryAdapter.clear();
        /*clean title save*/
        tvSave.setEnabled(false);
        tvSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
        tvSave.setTextColor(getResources().getColor(R.color.text_save));
        tvNumber.setTextColor(getResources().getColor(R.color.text_save));
        tvSelect.setTextColor(getResources().getColor(R.color.text_save));
        imRound.setBackgroundResource(R.mipmap.icon_selected);
        tvNumber.setText(0 + "");
        CategoryAdapter.arrChoose = new ArrayList<ItemCategory>();
        CategoryAdapter.arrDelete = new ArrayList<ItemCategory>();
    }

    private void getAllCategory(int userID, String apiToken, int grocery_id, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        FragmentAllProducts.isProgessingLoadMore = true;

        Controller controller = new Controller();
        Call<GetCategoryResponse> call = controller.service.getAllCategory(userID, apiToken, grocery_id, is_groceryAdmin);
        call.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Response<GetCategoryResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                FragmentAllProducts.isProgessingLoadMore = false;
                if (response != null) {
                    GetCategoryResponse getCategoryResponse = response.body();
                    if (getCategoryResponse != null) {
                        if (getCategoryResponse.code == 200) {
                            categoryAdapter.addAll(getCategoryResponse.result);
                            Config.arrItemCategory = new ArrayList<>();
                            Config.arrItemCategory.addAll(getCategoryResponse.result);
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

    private void updateCategory(final Context context, UpdateCategoryRequest updateCategoryRequest) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateCategory(updateCategoryRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            cleanAdapter();
                            loadMore();
                        }
                        Toast.makeText(context, baseResponse.message.toString(), Toast.LENGTH_SHORT).show();
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
