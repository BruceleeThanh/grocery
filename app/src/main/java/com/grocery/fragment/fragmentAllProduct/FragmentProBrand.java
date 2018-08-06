package com.grocery.fragment.fragmentAllProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.BrandAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomCheckBox;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DiaglogSave;
import com.grocery.model.ItemBrand;
import com.grocery.request.UpdateBrandRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.BrandResponse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 23/06/2017.
 */

public class FragmentProBrand extends Fragment implements View.OnClickListener {
    private LinearLayout btnSearch;
    private ImageView imClose;
    private RelativeLayout layoutSort;
    private CustomTextView tvSuggest;
    private CustomTextView tvSave;
    private RelativeLayout btnSort;
    private CustomTextView tvNumber;
    private CustomTextView tvSelect;
    private ImageView imRound;
    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomEditText edtSearch;
    private CRecyclerView listBrand;
    private BrandAdapter brandAdapter;
    private ArrayList<ItemBrand> arrBrand;
    private CustomCheckBox cbRecently;
    private CustomCheckBox cbNotAdd;
    private CustomCheckBox cbAdded;
    private ArrayList<CustomCheckBox> arrCb;
    private CustomTextView btnSubmit;
    private int sortType = 0;
    private CustomTextView tvRecently;
    private CustomTextView tvAdded;
    private CustomTextView tvNotAdd;
    private RelativeLayout layoutSuggest;
    private LinearLayout layoutSuggestBrand;
    private ImageView imgCloseLayoutSuggest;

    private ArrayList<CustomTextView> arrTvCb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_manage_brands, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener(view);
        return view;
    }

    private void initData() {
        arrTvCb = new ArrayList<>();
        arrTvCb.add(tvAdded);
        arrTvCb.add(tvNotAdd);
        arrTvCb.add(tvRecently);
        arrCb = new ArrayList<>();
        arrCb.add(cbAdded);
        arrCb.add(cbNotAdd);
        arrCb.add(cbRecently);
    }

    private void initListener(View view) {
        imClose.setOnClickListener(this);
        btnSort.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        tvSuggest.setOnClickListener(this);
        imgCloseLayoutSuggest.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cleanAdapter();
                swipeRefreshLayout.setRefreshing(false);
                brandAdapter.clear();
                resetTypeSort();
                loadMore();
            }
        });
        for (int i = 0; i < arrCb.size(); i++) {
            final int finalI = i;
            arrCb.get(i).setOncheckListener(new CustomCheckBox.OnCheckListener() {
                @Override
                public void onCheck(CustomCheckBox view, boolean check) {
                    for (int i = 0; i < arrCb.size(); i++) {
                        arrCb.get(i).setChecked(false);
                        arrTvCb.get(finalI).setTextColor(getResources().getColor(R.color.text_save));
                    }
                    arrCb.get(finalI).setChecked(true);
                    arrTvCb.get(finalI).setTextColor(getResources().getColor(R.color.bg_search_main));
                }
            });
        }
    }

    private void cleanAdapter() {
        brandAdapter.clear();
        tvSave.setEnabled(false);
        edtSearch.setText("");
        tvSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
        tvSave.setTextColor(getResources().getColor(R.color.text_save));
        tvNumber.setTextColor(getResources().getColor(R.color.text_save));
        tvSelect.setTextColor(getResources().getColor(R.color.text_save));
        imRound.setBackgroundResource(R.mipmap.icon_selected);
        tvNumber.setText(0 + "");
        BrandAdapter.arrDelete = new ArrayList<>();
        BrandAdapter.arrAdd = new ArrayList<>();
    }

    private void searchBrand(String s) {
        ArrayList<ItemBrand> arr = new ArrayList<>();
        if (s.toString().isEmpty()) {
            arr.addAll(arrBrand);
        } else {
            for (int i = 0; i < arrBrand.size(); i++) {
                if (arrBrand.get(i).getName().toString().toLowerCase().trim().contains(s)) {
                    arr.add(arrBrand.get(i));
                }
            }
        }
        brandAdapter.filter(arr);
    }

    public void resetTypeSort() {
        for (int i = 0; i < arrCb.size(); i++) {
            arrCb.get(i).setChecked(false);
            arrTvCb.get(i).setTextColor(getResources().getColor(R.color.text_save));
        }
        sortType = 0;
        layoutSort.setVisibility(View.GONE);
    }

    private void initView(View view) {
        edtSearch = (CustomEditText) view.findViewById(R.id.edt_search_brand);
        btnSort = view.findViewById(R.id.layout_sort_manager_brand);
        btnSearch = (LinearLayout) view.findViewById(R.id.btn_search);
        imClose = (ImageView) view.findViewById(R.id.icon_close_sort_manage_brand);
        layoutSort = (RelativeLayout) view.findViewById(R.id.layout_sort_brand);
        tvSuggest = (CustomTextView) view.findViewById(R.id.btn_suggest_a_brand);
        tvSave = (CustomTextView) view.findViewById(R.id.btn_save_manager_category);
        tvNumber = (CustomTextView) view.findViewById(R.id.tv_number_select_add_products);
        tvSelect = (CustomTextView) view.findViewById(R.id.tvSelect);
        imRound = (ImageView) view.findViewById(R.id.im_round_selected);
        cbRecently = (CustomCheckBox) view.findViewById(R.id.cbRecently);
        cbAdded = (CustomCheckBox) view.findViewById(R.id.cbAdded);
        cbNotAdd = (CustomCheckBox) view.findViewById(R.id.cbNotAdd);
        btnSubmit = (CustomTextView) view.findViewById(R.id.btnSubmit);
        tvRecently = (CustomTextView) view.findViewById(R.id.tvRecently);
        tvAdded = (CustomTextView) view.findViewById(R.id.tvAdded);
        tvNotAdd = (CustomTextView) view.findViewById(R.id.tvNotAdd);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        listBrand = (CRecyclerView) view.findViewById(R.id.listBrand);
        layoutSuggest = view.findViewById(R.id.layout_suggest);
        layoutSuggestBrand = view.findViewById(R.id.layout_suggest_brand);
        imgCloseLayoutSuggest = view.findViewById(R.id.img_close_layout_suggest);
        brandAdapter = new BrandAdapter(getActivity(), new ArrayList<ItemBrand>(), this);
        listBrand.setAdapter(brandAdapter);
        loadMore();
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.layout_sort_manager_brand:
                layoutSort.setVisibility(LinearLayout.VISIBLE);
                break;
            case R.id.icon_close_sort_manage_brand:
                layoutSort.setVisibility(LinearLayout.GONE);
                break;
            case R.id.btn_search:
                if (edtSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                }
                searchBrand(edtSearch.getText().toString());
                break;
            case R.id.btn_save_manager_category:
                Config.number_update = Integer.parseInt(String.valueOf(tvNumber.getText()));
                DiaglogSave.title = getString(R.string.sub_category_update);
                DiaglogSave diaglogSave = new DiaglogSave(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                diaglogSave.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                diaglogSave.show();
                diaglogSave.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (Config.checkUpdate) {
                            UpdateBrandRequest updateBrandRequest = new UpdateBrandRequest(Config.AdminId,
                                    Config.apiToken, converListStringId(BrandAdapter.arrDelete),
                                    converListStringId(BrandAdapter.arrAdd), Config.is_groceryAdmin);
                            updateBrand(getActivity(), updateBrandRequest);
                        }
                        Config.checkUpdate = false;
                        Config.number_update = 0;
                    }
                });
                break;
            case R.id.btnSubmit:
                cleanAdapter();
                sortType = getTypeSort();
                loadMore();

            case R.id.btn_suggest_a_brand:
                layoutSuggestBrand.setVisibility(View.VISIBLE);
                layoutSuggest.setBackgroundColor(getResources().getColor(R.color.despatched_orders));
                tvSuggest.setBackgroundResource(R.drawable.radius_edt);
                tvSuggest.setTextColor(getResources().getColor(R.color.despatched_orders));
                tvSuggest.setText(getString(R.string.suggest_brand_2));
                break;

            case R.id.img_close_layout_suggest:
                layoutSuggestBrand.setVisibility(View.GONE);
                layoutSuggest.setBackgroundColor(getResources().getColor(R.color.back_list));
                tvSuggest.setBackgroundResource(R.drawable.btn_open_again_shop);
                tvSuggest.setTextColor(getResources().getColor(R.color.colorWhite));
                tvSuggest.setText(getString(R.string.suggest_brand));
                break;
        }
    }

    public int getTypeSort() {
        for (int i = 0; i < arrCb.size(); i++) {
            if (arrCb.get(i).isCheck()) {
                return i + 1;
            }
        }
        return 0;
    }

    public String converListStringId(ArrayList<ItemBrand> arrayList) {
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

    public void loadMore() {
        getAllBrand(Config.AdminId, Config.apiToken, Config.AdminId, sortType, Config.is_groceryAdmin);
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

    private void getAllBrand(int userID, String apiToken, int grocery_id, int sort_type, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<BrandResponse> call = controller.service.getAllBrand(userID, apiToken, grocery_id, sort_type, is_groceryAdmin);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Response<BrandResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    BrandResponse brandResponse = response.body();
                    if (brandResponse != null) {
                        if (brandResponse.code == 200) {
                            brandAdapter.addAll(brandResponse.result);
                            arrBrand = new ArrayList<>();
                            arrBrand.addAll(brandResponse.result);
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

    private void updateBrand(final Context context, UpdateBrandRequest updateBrandRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateBrand(updateBrandRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
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
