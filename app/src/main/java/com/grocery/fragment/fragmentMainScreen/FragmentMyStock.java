package com.grocery.fragment.fragmentMainScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.MyStockAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomCheckBox;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.model.ItemBrand;
import com.grocery.model.ItemCategory;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.model.ItemSubCategory;
import com.grocery.response.BrandResponse;
import com.grocery.response.GetCategoryResponse;
import com.grocery.response.ManagerProductsToUpdatResqonse;
import com.grocery.response.SubCategoryResponse;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class FragmentMyStock extends Fragment {
    private CRecyclerView lvMyStock;
    private MyStockAdapter myStockAdapter;
    private static final int PAGE_SIZE = 15;
    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView imSearch;
    private Spinner spnCategory;
    private Spinner spnSubCategory;
    private Spinner spnBrand;
    private CustomEditText edtName;
    private ArrayList<ItemCategory> arrCategory = new ArrayList<>();
    private boolean isLoadCategory = false;
    private int idCategory = 0;
    private int idSubCategory = 0;
    private int idBrand = 0;
    private String textSearch = "";
    private ArrayList<ItemSubCategory> arrSubCategory = new ArrayList<>();
    private boolean isLoadSub = false;
    private ArrayList<ItemBrand> arrBrand = new ArrayList<>();
    private boolean isLoadBrand = false;
    private CustomCheckBox cbHigest;
    private CustomCheckBox cbAvaiable;
    private CustomCheckBox cbOutStock;
    private ArrayList<CustomCheckBox> arrCb;
    private CustomTextView tvHigest;
    private CustomTextView tvAvaiable;
    private CustomTextView tvOutStock;
    private ArrayList<CustomTextView> arrTvCb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mystock, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener(view);
        return view;
    }

    private void initData() {
        loadSpnCategory();
        loadDataSpnBrand();
        loadMore();
        arrCb = new ArrayList<>();
        arrCb.add(cbHigest);
        arrCb.add(cbAvaiable);
        arrCb.add(cbOutStock);
        arrTvCb = new ArrayList<>();
        arrTvCb.add(tvHigest);
        arrTvCb.add(tvAvaiable);
        arrTvCb.add(tvOutStock);
    }

    public int getIdToNameCategory(String name) {
        for (int i = 0; i < arrCategory.size(); i++) {
            if (arrCategory.get(i).getName().toString().equals(name)) {
                return arrCategory.get(i).getId();
            }
        }
        return 0;
    }

    public int getIdToNameSubCtegory(String name) {
        for (int i = 0; i < arrSubCategory.size(); i++) {
            if (arrSubCategory.get(i).getName().toString().equals(name)) {
                return arrSubCategory.get(i).getId();
            }
        }
        return 0;
    }

    public int getIdToNameBrand(String name) {
        for (int i = 0; i < arrBrand.size(); i++) {
            if (arrBrand.get(i).getName().toString().equals(name)) {
                return arrBrand.get(i).getId();
            }
        }
        return 0;
    }


    private void initListener(View view) {
        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    textSearch = edtName.getText().toString();
                }
                loadMore();
            }
        });
        lvMyStock.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                cleanAdapter();
                cleanSpinner();
                loadMore();
            }
        });

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLoadCategory) {
                    isLoadCategory = true;
                    return;
                }
                if (parent.getSelectedItem().toString().equals("All")) {
                    idCategory = 0;
                } else {
                    idCategory = getIdToNameCategory(parent.getSelectedItem() + "");
                }
                isLoadSub = false;
                idSubCategory = 0;
                cleanAdapter();
                loadDateSpnSubCategory();
                loadMore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLoadSub) {
                    isLoadSub = true;
                    return;
                }
                if (parent.getSelectedItem().toString().equals("All")) {
                    idSubCategory = 0;
                } else {
                    idSubCategory = getIdToNameSubCtegory(parent.getSelectedItem() + "");
                }
                cleanAdapter();
                loadMore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLoadBrand) {
                    isLoadBrand = true;
                    return;
                }
                if (parent.getSelectedItem().toString().equals("All")) {
                    idBrand = 0;
                } else {
                    idBrand = getIdToNameBrand(parent.getSelectedItem() + "");
                }
                cleanAdapter();
                loadMore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < arrCb.size(); i++) {
            final int finalI = i;
            arrCb.get(i).setOncheckListener(new CustomCheckBox.OnCheckListener() {
                @Override
                public void onCheck(CustomCheckBox view, boolean check) {
                    if (!arrCb.get(finalI).isCheck()) {
                        for (int i = 0; i < arrCb.size(); i++) {
                            arrCb.get(i).setChecked(false);
                            arrTvCb.get(i).setTextColor(getResources().getColor(R.color.text_save));
                        }
                    } else {
                        for (int i = 0; i < arrCb.size(); i++) {
                            arrCb.get(i).setChecked(false);
                            arrTvCb.get(i).setTextColor(getResources().getColor(R.color.text_save));
                        }
                        arrCb.get(finalI).setChecked(true);
                        arrTvCb.get(finalI).setTextColor(getResources().getColor(R.color.bg_search_main));
                    }
                }
            });
        }
    }

    private void initView(View view) {
        edtName = (CustomEditText) view.findViewById(R.id.edtName);
        imSearch = (ImageView) view.findViewById(R.id.imSearch);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lvMyStock = (CRecyclerView) view.findViewById(R.id.lv_my_stock);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5,
                GridLayoutManager.VERTICAL, false);
        lvMyStock.setLayoutManager(gridLayoutManager);
        myStockAdapter = new MyStockAdapter(getActivity(), new ArrayList<ItemManagerProductsUpdate>(), this);
        lvMyStock.setAdapter(myStockAdapter);
        spnCategory = (Spinner) view.findViewById(R.id.spnCategory);
        spnSubCategory = (Spinner) view.findViewById(R.id.spnSubCategory);
        spnBrand = (Spinner) view.findViewById(R.id.spnBrand);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnCategory);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spnSubCategory);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(spnBrand);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
            popupWindow1.setHeight(ListPopupWindow.WRAP_CONTENT);
            popupWindow2.setHeight(ListPopupWindow.WRAP_CONTENT);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        cbHigest = (CustomCheckBox) view.findViewById(R.id.cbHigest);
        cbAvaiable = (CustomCheckBox) view.findViewById(R.id.cbAvaiable);
        cbOutStock = (CustomCheckBox) view.findViewById(R.id.cbOutStock);
        tvAvaiable = (CustomTextView) view.findViewById(R.id.tvAvaiable);
        tvHigest = (CustomTextView) view.findViewById(R.id.tvHighest);
        tvOutStock = (CustomTextView) view.findViewById(R.id.tvOutStock);
    }


    public void cleanSpinner() {
        spnCategory.setSelection(0);
        spnSubCategory.setSelection(0);
        spnBrand.setSelection(0);
        isLoadCategory = false;
        isLoadSub = false;
        isLoadBrand = false;
        idBrand = 0;
        idSubCategory = 0;
        idCategory = 0;
    }

    public void cleanAdapter() {
        textSearch = "";
        edtName.setText("");
        myStockAdapter.clear();
        page_number = 0;
    }

    public void loadSpnCategory() {
        getAllCategory(Config.AdminId, Config.apiToken, Config.AdminId, Config.is_groceryAdmin);
    }

    public void loadDataSpnBrand() {
        getAllBrand(Config.AdminId, Config.apiToken, Config.AdminId, Config.is_groceryAdmin);
    }

    public void loadDateSpnSubCategory() {
        getSubCategory(Config.AdminId, Config.apiToken, Config.AdminId, idCategory, 1000,
                1, Config.is_groceryAdmin);
    }

    public void loadMore() {
        getListManageProductsToUpdate(Config.AdminId, Config.apiToken, idCategory, idSubCategory,
                idBrand, textSearch, PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
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
                            myStockAdapter.addAll(managerProductsToUpdatResqonse.result);
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
                            spnCategory.setAdapter(adapter);
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

    private void getSubCategory(int userID, String apiToken, int grocery_id,
                                int category_id, int page_size, int page_number, int is_groceryAdmin) {
        Controller controller = new Controller();
        Call<SubCategoryResponse> call = controller.service.getSubCategory(userID, apiToken, grocery_id, category_id,
                page_size, page_number, 0, is_groceryAdmin);
        call.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Response<SubCategoryResponse> response, Retrofit retrofit) {
                if (response != null) {
                    SubCategoryResponse subCategoryResponse = response.body();
                    if (subCategoryResponse != null) {
                        if (subCategoryResponse.code == 200) {
                            arrSubCategory = new ArrayList<>();
                            arrSubCategory.addAll(subCategoryResponse.result);
                            ArrayList<String> arrNameSubCategory = new ArrayList<>();
                            arrNameSubCategory.add("All");
                            if (!spnCategory.getSelectedItem().toString().equals("All")) {
                                for (int i = 0; i < subCategoryResponse.result.size(); i++) {
                                    arrNameSubCategory.add(subCategoryResponse.result.get(i).getName().toString());
                                }
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                                    android.R.layout.simple_dropdown_item_1line, arrNameSubCategory);
                            spnSubCategory.setAdapter(adapter);
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

    private void getAllBrand(int userID, String apiToken, int grocery_id, int is_groceryAdmin) {
        Controller controller = new Controller();
        Call<BrandResponse> call = controller.service.getAllBrand(userID, apiToken, grocery_id, 0, is_groceryAdmin);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Response<BrandResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BrandResponse brandResponse = response.body();
                    if (brandResponse != null) {
                        if (brandResponse.code == 200) {
                            arrBrand = new ArrayList<>();
                            arrBrand.addAll(brandResponse.result);
                            ArrayList<String> arrNameBrand = new ArrayList<>();
                            arrNameBrand.add("All");
                            for (int i = 0; i < brandResponse.result.size(); i++) {
                                arrNameBrand.add(brandResponse.result.get(i).getName().toString());
                            }
                            ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                                    R.layout.item_spinner, arrNameBrand);
                            spnBrand.setAdapter(adapter);
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
