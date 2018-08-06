package com.grocery.fragment.fragmentAllProduct;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogSuggestPro;
import com.grocery.fragment.fragmentManageProducts.FragmentAddProducts;
import com.grocery.fragment.fragmentManageProducts.FragmentUpdateProducts;
import com.grocery.model.ItemBrand;
import com.grocery.model.ItemSubCategory;
import com.grocery.response.BrandResponse;
import com.grocery.response.SubCategoryResponse;
import com.grocery.utils.FragmentUtils;
import com.grocery.utils.HideSoftKeyBroad;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 23/06/2017.
 */

public class FragmentProManageProduct extends Fragment implements View.OnClickListener {

    private CustomTextView tvAdd;
    private CustomTextView tvUpdate;
    private CustomTextView tvSuggest;
    private ImageView imSearch;
    private FragmentUtils fragmentUtils;
    private FragmentAddProducts fragmentAddProducts;
    private FragmentUpdateProducts fragmentUpdateProducts;
    private static Spinner spnCategory;
    private static Spinner spnSubCategory;
    private static Spinner spnBrand;
    private ArrayList<ItemSubCategory> arrSubCategory;
    private ArrayList<ItemBrand> arrBrand;
    private static CustomEditText edtProductsName;

    private boolean isloadCategory = false;
    private boolean isLoadSubCategory = false;
    private boolean isLoadBrand = false;
    public static boolean isProgessing = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_product_manage_pro, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        /*init spinner category*/
        ArrayList<String> arrNameCategory = new ArrayList<>();
        for (int i = 0; i < Config.arrItemCategory.size(); i++) {
            if (i == 0) {
                arrNameCategory.add("All");
            }
            arrNameCategory.add(Config.arrItemCategory.get(i).getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                R.layout.item_spinner_2, arrNameCategory);
        spnCategory.setAdapter(adapter);

        /*data spinner brand*/
        loadDataSpnBrand();

        /*data sub category*/
        ArrayList<String> arrNameSubCategory = new ArrayList<>();
        arrNameSubCategory.add("All");
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                R.layout.item_spinner_2, arrNameSubCategory);
        spnSubCategory.setAdapter(adapter1);
    }

    private void initView(View view) {
        tvAdd = (CustomTextView) view.findViewById(R.id.tv_manage_pro_add);
        tvUpdate = (CustomTextView) view.findViewById(R.id.tv_manage_pro_update);
        tvSuggest = (CustomTextView) view.findViewById(R.id.tv_suggest_product);
        imSearch = (ImageView) view.findViewById(R.id.im_search_manage_pro);
        edtProductsName = (CustomEditText) view.findViewById(R.id.edt_products_name);
        /*init Fragemnt*/
        fragmentUtils = new FragmentUtils(getChildFragmentManager(), R.id.layoutFragment);
        fragmentAddProducts = new FragmentAddProducts();
        fragmentUpdateProducts = new FragmentUpdateProducts();
        fragmentUtils.addToFragment(fragmentAddProducts);
        setBackgroundTvTitle(R.id.tv_manage_pro_add);

        /*init spinner*/
        spnCategory = (Spinner) view.findViewById(R.id.spn_choose_category_my_stock);
        spnSubCategory = (Spinner) view.findViewById(R.id.spn_choose_sub_category_my_stock);
        spnBrand = (Spinner) view.findViewById(R.id.spn_choose_brand_my_stock);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnCategory);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spnSubCategory);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(spnBrand);

            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow2.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(450);
            popupWindow1.setHeight(450);
            popupWindow2.setHeight(450);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

    }

    private void initListener() {
        tvAdd.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        tvSuggest.setOnClickListener(this);
        imSearch.setOnClickListener(this);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isloadCategory) {
                    isloadCategory = true;
                    return;
                }
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                FragmentAddProducts.ID_CATEGORY = getIdToNameCategory(parent.getSelectedItem() + "");
                FragmentAddProducts.ID_SUB_CATEGORY = 0;
                FragmentAddProducts.TEXT_SEARCH = edtProductsName.getText().toString();
                if (!fragmentAddProducts.isHidden()) {
                    fragmentAddProducts.cleanAdapter();
                    fragmentAddProducts.loadMore();
                } else {
                    fragmentUpdateProducts.cleanAdapter();
                    fragmentUpdateProducts.loadMore();
                }
                loadDateSpnSubCategory();
                isLoadSubCategory = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
            }
        });

        spnSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLoadSubCategory) {
                    isLoadSubCategory = true;
                    return;
                }
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                FragmentAddProducts.ID_SUB_CATEGORY = getIdToNameSubCategory(parent.getSelectedItem() + "");
                FragmentAddProducts.TEXT_SEARCH = edtProductsName.getText().toString();
                if (!fragmentAddProducts.isHidden()) {
                    fragmentAddProducts.cleanAdapter();
                    fragmentAddProducts.loadMore();
                } else {
                    fragmentUpdateProducts.cleanAdapter();
                    fragmentUpdateProducts.loadMore();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
            }
        });

        spnBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLoadBrand) {
                    isLoadBrand = true;
                    return;
                }
                FragmentAddProducts.ID_BRAND = getIdToNameBrand(parent.getSelectedItem() + "");
                FragmentAddProducts.TEXT_SEARCH = edtProductsName.getText().toString();
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                if (!fragmentAddProducts.isHidden()) {
                    fragmentAddProducts.cleanAdapter();
                    fragmentAddProducts.loadMore();
                } else {
                    fragmentUpdateProducts.cleanAdapter();
                    fragmentUpdateProducts.loadMore();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
            }
        });
    }


    public void loadDateSpnSubCategory() {
        getSubCategory(Config.AdminId, Config.apiToken, Config.AdminId,
                FragmentAddProducts.ID_CATEGORY, 1000, 1, Config.is_groceryAdmin);
    }

    public void loadDataSpnBrand() {
        getAllBrand(Config.AdminId, Config.apiToken, Config.AdminId, Config.is_groceryAdmin);
    }

    public int getIdToNameCategory(String name) {
        for (int i = 0; i < Config.arrItemCategory.size(); i++) {
            if (Config.arrItemCategory.get(i).getName().toString().equals(name)) {
                return Config.arrItemCategory.get(i).getId();
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

    public int getIdToNameSubCategory(String name) {
        for (int i = 0; i < arrSubCategory.size(); i++) {
            if (arrSubCategory.get(i).getName().toString().equals(name)) {
                return arrSubCategory.get(i).getId();
            }
        }
        return 0;
    }

    public static void cleanSearch() {
        spnCategory.setSelection(0);
        spnSubCategory.setSelection(0);
        spnBrand.setSelection(0);
        edtProductsName.setText("");
    }

    public void setBackgroundTvTitle(int id) {
        switch (id) {
            case R.id.tv_manage_pro_add:
                tvUpdate.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
                tvAdd.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
                break;
            case R.id.tv_manage_pro_update:
                tvAdd.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
                tvUpdate.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (FragmentProManageProduct.isProgessing) {
            return;
        }
        setBackgroundTvTitle(v.getId());
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.tv_manage_pro_add:
                cleanSearch();
                fragmentUtils.changeFragment(fragmentAddProducts);
                break;
            case R.id.tv_manage_pro_update:
                cleanSearch();
                fragmentUtils.changeFragment(fragmentUpdateProducts);
                break;
            case R.id.tv_suggest_product:
                DialogSuggestPro dialogSuggestPro = new DialogSuggestPro(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogSuggestPro.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogSuggestPro.show();
                break;
            case R.id.im_search_manage_pro:
                if (edtProductsName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                }
                FragmentAddProducts.TEXT_SEARCH = edtProductsName.getText().toString();
                FragmentAddProducts.page_number = 0;
                fragmentAddProducts.cleanAdapter();
                fragmentAddProducts.loadMore();
                break;
        }
    }

    private void getSubCategory(int userID, String apiToken, int grocery_id, int category_id,
                                int page_size, int page_number, int is_groceryAdmin) {
        isProgessing = true;
        Controller controller = new Controller();
        Call<SubCategoryResponse> call = controller.service.getSubCategory(userID, apiToken, grocery_id,
                category_id, page_size, page_number, 0, is_groceryAdmin);
        call.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Response<SubCategoryResponse> response, Retrofit retrofit) {
                isProgessing = false;
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
                                    R.layout.item_spinner_2, arrNameSubCategory);
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
        isProgessing = true;
        Controller controller = new Controller();
        Call<BrandResponse> call = controller.service.getAllBrand(userID, apiToken, grocery_id, 0, is_groceryAdmin);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Response<BrandResponse> response, Retrofit retrofit) {
                isProgessing = false;
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
                                    R.layout.item_spinner_2, arrNameBrand);
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
