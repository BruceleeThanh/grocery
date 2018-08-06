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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.SuggestProTableAdapter;
import com.grocery.adapter.SuggestProductAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.model.ItemProduct;
import com.grocery.model.ItemSuggestionProducts;
import com.grocery.request.SuggestProductsRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.SuggestionProductsResponse;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 23/06/2017.
 */

public class DialogSuggestPro extends Dialog implements View.OnClickListener {
    private ImageView imBack;
    private CustomTextView btnSuggestProduct;
    private ImageView imAdd;
    private CRecyclerView lvSuggestProduct;

    private SuggestProductAdapter suggestProductAdapter;
    private CustomEditText edtNamePro;
    private CustomEditText edtNameBrand;
    private CustomEditText edtNameDes;

    private CRecyclerView lvSuggestProTable;
    private SuggestProTableAdapter suggestProTableAdapter;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private ProgerssBarUntil progerssBarUntil;
    private View view;
    private ArrayList<ItemProduct> arrSuggestProductsAdd = new ArrayList<>();

    public DialogSuggestPro(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        view = View.inflate(context, R.layout.dialog_suggset_product, null);
        setContentView(view);
        if (context instanceof MainActivity) {
            setOwnerActivity((MainActivity) context);
        }
        progerssBarUntil = new ProgerssBarUntil(view);
        initView();
        initListener();
    }

    private void initListener() {
        imBack.setOnClickListener(this);
        btnSuggestProduct.setOnClickListener(this);
        imAdd.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                suggestProTableAdapter.clear();
                page_number = 0;
                loadMore();
            }
        });
        lvSuggestProTable.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    }

    private void initView() {
        edtNamePro = (CustomEditText) findViewById(R.id.edt_suggest_pro_name);
        edtNameBrand = (CustomEditText) findViewById(R.id.edt_suggest_brand_name);
        edtNameDes = (CustomEditText) findViewById(R.id.edt_suggest_des_name);

        imBack = (ImageView) findViewById(R.id.im_dialog_suggest_pro_back);
        btnSuggestProduct = (CustomTextView) findViewById(R.id.btn_suggest_products);
        imAdd = (ImageView) findViewById(R.id.im_suggest_product_add);

        lvSuggestProduct = (CRecyclerView) findViewById(R.id.lv_suggest_product);
        suggestProductAdapter = new SuggestProductAdapter(getContext(), this, arrSuggestProductsAdd);
        lvSuggestProduct.setAdapter(suggestProductAdapter);

        lvSuggestProTable = (CRecyclerView) findViewById(R.id.lv_suggest_product_table);
        suggestProTableAdapter = new SuggestProTableAdapter(getContext(), new ArrayList<ItemSuggestionProducts>());
        lvSuggestProTable.setAdapter(suggestProTableAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        loadMore();
        if (arrSuggestProductsAdd.isEmpty()) {
            checkStatusBtn(false);
        } else {
            checkStatusBtn(true);
        }
    }

    public void checkStatusBtn(boolean check) {
        if (!check) {
            btnSuggestProduct.setEnabled(false);
            btnSuggestProduct.setTextColor(getContext().getResources().getColor(R.color.text_save));
            btnSuggestProduct.setBackgroundResource(R.drawable.radius_list);
        } else {
            btnSuggestProduct.setEnabled(true);
            btnSuggestProduct.setTextColor(Color.WHITE);
            btnSuggestProduct.setBackgroundResource(R.drawable.btn_open_again_shop);
        }
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        switch (v.getId()) {
            case R.id.im_suggest_product_add:
                String namePro = edtNamePro.getText().toString();
                String nameBrand = edtNameBrand.getText().toString();
                String nameDes = edtNameDes.getText().toString();
                if (namePro.equals("") || nameDes.equals("") || nameBrand.equals("")) {
                    Toast.makeText(getOwnerActivity(), "Input data not empty", Toast.LENGTH_SHORT);
                    return;
                }
                arrSuggestProductsAdd.add(new ItemProduct(namePro, nameBrand, nameDes));
                suggestProductAdapter.notifyDataSetChanged();
                edtNameBrand.setText("");
                edtNameDes.setText("");
                edtNamePro.setText("");
                if (!arrSuggestProductsAdd.isEmpty()) {
                    checkStatusBtn(true);
                } else {
                    checkStatusBtn(false);
                }
                break;
            case R.id.im_dialog_suggest_pro_back:
                cancel();
                break;
            case R.id.btn_suggest_products:
                SuggestProductsRequest suggestProductsRequest = new SuggestProductsRequest(Config.AdminId,
                        Config.apiToken, arrSuggestProductsAdd, Config.is_groceryAdmin);
                suggestProducts(getContext(), suggestProductsRequest);
                break;
        }
    }

    public void loadMore() {
        getSuggestProduct(Config.AdminId, Config.apiToken, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void getSuggestProduct(int userID, String apiToken, int page_size, int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<SuggestionProductsResponse> call = controller.service.getSuggestProducts(userID, apiToken,
                page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<SuggestionProductsResponse>() {
            @Override
            public void onResponse(Response<SuggestionProductsResponse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    SuggestionProductsResponse suggestionProductsResponse = response.body();
                    if (suggestionProductsResponse != null) {
                        if (suggestionProductsResponse.code == 200) {
                            if (suggestionProductsResponse.result.size() == Config.PAGE_SIZE) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            suggestProTableAdapter.addAll(suggestionProductsResponse.result);
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

    private void suggestProducts(final Context context, SuggestProductsRequest suggestProductsRequest) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.suggestProducts(suggestProductsRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            arrSuggestProductsAdd = new ArrayList<>();
                            suggestProductAdapter.clear();
                            suggestProTableAdapter.clear();
                            loadMore();
                            checkStatusBtn(false);
                        }
                    }
                    Toast.makeText(context, baseResponse.message.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
