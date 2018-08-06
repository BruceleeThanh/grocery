package com.grocery.fragment.fragmentManagerOffer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.ManageOffersLiveAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.controller.Controller;
import com.grocery.model.ItemCategory;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.response.GetCategoryResponse;
import com.grocery.response.ManagerProductsToUpdatResqonse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 05/07/2017.
 */

public class FragmentManagerOffersRegular extends Fragment implements View.OnClickListener {
    private ManageOffersLiveAdapter manageOffersLiveAdapter;
    private CRecyclerView lvMaganeRegular;
    private LinearLayout btnSearch;
    private ProgerssBarUntil progerssBarUntil;
    private int page_number = 0;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String textSearch = "";
    private int idCategory = 0;
    private Spinner spnChooseCategory;
    private ArrayList<ItemCategory> arrCategory = new ArrayList<>();
    private CustomEditText tvSearch;
    private LinearLayout btnDown1;
    private LinearLayout btnDown2;
    private LinearLayout btnDown3;
    private LinearLayout btnUp1;
    private LinearLayout btnUp2;
    private LinearLayout btnUp3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_offers_regular, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListner();
        return view;
    }

    private void initData() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                R.layout.item_spinner, new ArrayList());
        spnChooseCategory.setAdapter(adapter);
        loadSpn();
    }

    private void initListner() {
        btnSearch.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                cleanAdapter();
                tvSearch.setText("");
                spnChooseCategory.setSelection(0);
                loadMore();
            }
        });

        lvMaganeRegular.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        spnChooseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvSearch.setText("");
                if (parent.getSelectedItem().toString().equals("All")) {
                    idCategory = 0;
                } else {
                    idCategory = getIdToName(parent.getSelectedItem() + "");
                }
                cleanAdapter();
                loadMore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDown1.setOnClickListener(this);
        btnDown2.setOnClickListener(this);
        btnDown3.setOnClickListener(this);
        btnUp1.setOnClickListener(this);
        btnUp2.setOnClickListener(this);
        btnUp3.setOnClickListener(this);
    }

    public void cleanAdapter() {
        page_number = 0;
        manageOffersLiveAdapter.clear();
    }

    private void initView(View view) {
        tvSearch = (CustomEditText) view.findViewById(R.id.tvSearch);
        btnSearch = (LinearLayout) view.findViewById(R.id.btn_search_regular_offer);
        lvMaganeRegular = (CRecyclerView) view.findViewById(R.id.lv_manage_offers_regular);
        manageOffersLiveAdapter = new ManageOffersLiveAdapter(getActivity(), new ArrayList<ItemManagerProductsUpdate>(), this);
        lvMaganeRegular.setAdapter(manageOffersLiveAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        spnChooseCategory = (Spinner) view.findViewById(R.id.spn_category);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnChooseCategory);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(450);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        btnDown1 = (LinearLayout) view.findViewById(R.id.btnDown1);
        btnDown2 = (LinearLayout) view.findViewById(R.id.btnDown2);
        btnDown3 = (LinearLayout) view.findViewById(R.id.btnDown3);
        btnUp1 = (LinearLayout) view.findViewById(R.id.btnUp1);
        btnUp2 = (LinearLayout) view.findViewById(R.id.btnUp2);
        btnUp3 = (LinearLayout) view.findViewById(R.id.btnUp3);
    }

    public int getIdToName(String name) {
        for (int i = 0; i < arrCategory.size(); i++) {
            if (arrCategory.get(i).getName().toString().equals(name)) {
                return arrCategory.get(i).getId();
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_regular_offer:
                if (tvSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                }
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                cleanAdapter();
                loadMore();
                break;
            case R.id.btnDown1:
                lvMaganeRegular.smoothScrollBy(0, Config.scrollItem);
                break;
            case R.id.btnDown2:
                lvMaganeRegular.smoothScrollBy(0, 2 * Config.scrollItem);
                break;
            case R.id.btnDown3:
                lvMaganeRegular.smoothScrollBy(0, 3 * Config.scrollItem);
                break;
            case R.id.btnUp1:
                lvMaganeRegular.smoothScrollBy(0, -Config.scrollItem);
                break;
            case R.id.btnUp2:
                lvMaganeRegular.smoothScrollBy(0, -2 * Config.scrollItem);
                break;
            case R.id.btnUp3:
                lvMaganeRegular.smoothScrollBy(0, -3 * Config.scrollItem);
                break;

        }
    }

    public void loadMore() {
        if (tvSearch.getText().toString().isEmpty()) {
            textSearch = "";
        } else {
            textSearch = tvSearch.getText().toString();
        }
        getListManageProductsToUpdate(Config.AdminId, Config.apiToken, idCategory, 0,
                0, textSearch, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }


    public void loadSpn() {
        getAllCategory(Config.AdminId, Config.apiToken, Config.AdminId, Config.is_groceryAdmin);
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
                            if (managerProductsToUpdatResqonse.result.size() == Config.PAGE_SIZE) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            manageOffersLiveAdapter.addAll(managerProductsToUpdatResqonse.result);
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

    private void getAllCategory(int userID, String apiToken, int grocery_id, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<GetCategoryResponse> call = controller.service.getAllCategory(userID, apiToken, grocery_id, is_groceryAdmin);
        call.enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Response<GetCategoryResponse> response, Retrofit retrofit) {
                if (response != null) {
                    GetCategoryResponse getCategoryResponse = response.body();
                    if (getCategoryResponse != null) {
                        if (getCategoryResponse.code == 200) {
                            arrCategory.addAll(getCategoryResponse.result);
                            ArrayList<String> arrNameCategory = new ArrayList<>();
                            for (int i = 0; i < arrCategory.size(); i++) {
                                if (i == 0) {
                                    arrNameCategory.add("All");
                                }
                                arrNameCategory.add(arrCategory.get(i).getName());
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                                    R.layout.item_spinner, arrNameCategory);
                            spnChooseCategory.setAdapter(adapter);
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
