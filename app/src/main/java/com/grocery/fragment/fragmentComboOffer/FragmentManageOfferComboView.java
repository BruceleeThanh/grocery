package com.grocery.fragment.fragmentComboOffer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.ManageOfferComboViewAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.controller.Controller;
import com.grocery.model.ItemComboOffer;
import com.grocery.response.ComboOfferResponse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class FragmentManageOfferComboView extends Fragment implements View.OnClickListener {
    private CRecyclerView lvViewCombo;
    private ManageOfferComboViewAdapter manageOfferComboViewAdapter;
    private MainActivity mainActivity;
    private ProgerssBarUntil progerssBarUntil;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private CustomEditText edtSearch;
    private String textSearch = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_offer_combo_view, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainActivity = (MainActivity) getActivity();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        loadMore();
    }

    private void initListener() {
        lvViewCombo.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                cleanAdpater();
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                loadMore();
            }
        });
    }

    public void cleanAdpater() {
        manageOfferComboViewAdapter.clear();
        edtSearch.setText("");
        textSearch = "";
        page_number = 0;
    }

    public void loadMore() {
        getListComboOffer(Config.AdminId, Config.apiToken, textSearch, Config.PAGE_SIZE_10,
                ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        edtSearch = (CustomEditText) view.findViewById(R.id.textSearch);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lvViewCombo = (CRecyclerView) view.findViewById(R.id.lv_view_a_combo);
        manageOfferComboViewAdapter = new ManageOfferComboViewAdapter(getActivity(),
                new ArrayList<ItemComboOffer>(), this);
        lvViewCombo.setAdapter(manageOfferComboViewAdapter);
    }

    @Override
    public void onClick(View v) {
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.im_search_combo:
                if (edtSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    textSearch = edtSearch.getText().toString();
                }
                manageOfferComboViewAdapter.clear();
                page_number = 0;
                loadMore();
                break;
        }
    }

    private void getListComboOffer(int userID, String apiToken, String text_search, final int page_size,
                                   int page_number, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<ComboOfferResponse> call = controller.service.getListComboOffer(userID, apiToken, text_search,
                page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<ComboOfferResponse>() {
            @Override
            public void onResponse(Response<ComboOfferResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                isProgessingLoadMore = false;
                if (response != null) {
                    ComboOfferResponse comboOfferResponse = response.body();
                    if (comboOfferResponse != null) {
                        if (comboOfferResponse.code == 200) {
                            if (comboOfferResponse.result.size() == page_size) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            manageOfferComboViewAdapter.addAll(comboOfferResponse.result);
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
