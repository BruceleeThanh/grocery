package com.grocery.fragment.fragmentComboOffer;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.ManageOfferComboAdd1Adapter;
import com.grocery.adapter.ManageOfferComboAdd2Apdater;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.common.DateTimeFormat;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentManageProducts.FragmentAddProducts;
import com.grocery.fragment.fragmentManagerOffer.FragmentManagerOfferCombo;
import com.grocery.model.ItemCategory;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.request.CreatComboRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.GetCategoryResponse;
import com.grocery.response.ManagerProductsToUpdatResqonse;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class FragmentManageOfferComboCreat extends Fragment implements View.OnClickListener {
    private ManageOfferComboAdd1Adapter manageOffersAdapter;
    private CRecyclerView lvMaganeCreatCombo;

    private ManageOfferComboAdd2Apdater manageOfferComboAdd2Apdater;
    private CRecyclerView lvShowAddItemCombo;

    private CustomEditText edtPriceCombo;
    private CustomEditText edtDescription;
    private CustomTextView priceTotal;
    private CustomTextView tvCreatComboOffer;
    private CustomEditText edtSearch;
    private CustomTextView tvFromDate;
    private ImageView imSearch;
    private View viewPerUser;
    private View viewPerOrder;

    private ProgerssBarUntil progerssBarUntil;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private int page_number = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spnCategory;
    private boolean isLoadCategory = false;
    private ArrayList<ItemCategory> arrCategory;
    private int id_category = 0;
    private String text_search = "";
    private boolean isClick = false;
    private String datePick = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_offer_combo_creat, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        datePick = new SimpleDateFormat("yyyy-MM-dd").format(tomorrow);
        tvFromDate.setText(DateTimeFormat.formatTime3(datePick));
        loadDataSpinner();
        loadMore();
    }

    private void initListener() {
        imSearch.setOnClickListener(this);
        tvCreatComboOffer.setOnClickListener(this);
        tvFromDate.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                manageOffersAdapter.clear();
                page_number = 0;
                id_category = 0;
                text_search = "";
                edtSearch.setText("");
                isLoadCategory = false;
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                loadDataSpinner();
                loadMore();
            }
        });

        lvMaganeCreatCombo.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isLoadCategory) {
                    isLoadCategory = true;
                    return;
                }
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
                manageOffersAdapter.clear();
                page_number = 0;
                text_search = "";
                edtSearch.setText("");
                id_category = getIdToNameCategory(parent.getSelectedItem().toString());
                loadMore();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
            }
        });
    }

    public int getIdToNameCategory(String name) {
        for (int i = 0; i < arrCategory.size(); i++) {
            if (arrCategory.get(i).getName().toString().equals(name)) {
                return arrCategory.get(i).getId();
            }
        }
        return 0;
    }

    private void loadMore() {
        getListManageProductsToUpdate(Config.AdminId, Config.apiToken, id_category, FragmentAddProducts.ID_SUB_CATEGORY,
                FragmentAddProducts.ID_BRAND, text_search, Config.PAGE_SIZE, ++page_number, Config.is_groceryAdmin);
    }

    private void initView(View view) {
        tvFromDate = (CustomTextView) view.findViewById(R.id.fromDate);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        spnCategory = (Spinner) view.findViewById(R.id.spn_category);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnCategory);

            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(450);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        tvFromDate = (CustomTextView) view.findViewById(R.id.fromDate);
        edtSearch = (CustomEditText) view.findViewById(R.id.edtSearch);
        edtPriceCombo = (CustomEditText) view.findViewById(R.id.priceCombo);
        edtDescription = (CustomEditText) view.findViewById(R.id.description);
        priceTotal = (CustomTextView) view.findViewById(R.id.priceTotal);
        tvCreatComboOffer = (CustomTextView) view.findViewById(R.id.tv_creat_a_combo_offer);
        imSearch = (ImageView) view.findViewById(R.id.im_search_creat_combo);
        lvMaganeCreatCombo = (CRecyclerView) view.findViewById(R.id.lv_creat_combo);
        viewPerUser = view.findViewById(R.id.view_per_user);
        viewPerOrder = view.findViewById(R.id.view_per_order);

        manageOffersAdapter = new ManageOfferComboAdd1Adapter(getActivity(),
                new ArrayList<ItemManagerProductsUpdate>(), this);
        lvMaganeCreatCombo.setAdapter(manageOffersAdapter);

        lvShowAddItemCombo = (CRecyclerView) view.findViewById(R.id.lv_show_item_creat_combo);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                1, GridLayoutManager.HORIZONTAL, false);
        lvShowAddItemCombo.setLayoutManager(gridLayoutManager);
        manageOfferComboAdd2Apdater = new ManageOfferComboAdd2Apdater(getActivity(),
                new ArrayList<ItemManagerProductsUpdate>(), this);
        lvShowAddItemCombo.setAdapter(manageOfferComboAdd2Apdater);
    }

    public void updateListAdd(ItemManagerProductsUpdate itemManagerProductsUpdate) {
        if (manageOfferComboAdd2Apdater.getList().size() == 3) {
            Toast.makeText(getActivity(), getResources().getString(R.string.not_add_than_three), Toast.LENGTH_SHORT).show();
            return;
        }
        manageOfferComboAdd2Apdater.add(itemManagerProductsUpdate);
        unpdatePriceTotal((ArrayList<ItemManagerProductsUpdate>) manageOfferComboAdd2Apdater.getList());
    }

    public void unpdatePriceTotal(ArrayList<ItemManagerProductsUpdate> arr) {
        float total = 0;
        for (int i = 0; i < arr.size(); i++) {
            total += arr.get(i).getPrice();
        }
        priceTotal.setText(total + "");
    }

    public boolean checkProductsAdd(ItemManagerProductsUpdate itemAdd) {
        for (int i = 0; i < manageOfferComboAdd2Apdater.getList().size(); i++) {
            if (itemAdd.getId() == manageOfferComboAdd2Apdater.getList().get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    public void loadDataSpinner() {
        getAllCategory(Config.AdminId, Config.apiToken, Config.AdminId, Config.is_groceryAdmin);
    }

    public boolean checkOkCreat() {
        if (manageOfferComboAdd2Apdater.getList().size() < 2) {
            Toast.makeText(getActivity(), getResources().getString(R.string.least_two_producst), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtDescription.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_description1), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPriceCombo.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_comboPrice), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (Float.parseFloat(edtPriceCombo.getText().toString()) > Float.parseFloat(priceTotal.getText().toString())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.smaller_than), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (!isClick) {
            return;
        }
        HideSoftKeyBroad.hideSoftKeyboard(getActivity(), getView());
        switch (v.getId()) {
            case R.id.tv_creat_a_combo_offer:
                if (!checkOkCreat()) {
                    return;
                }
                String listProducts = "";
                for (int i = 0; i < manageOfferComboAdd2Apdater.getList().size(); i++) {
                    if (listProducts.toString().isEmpty()) {
                        listProducts = manageOfferComboAdd2Apdater.getList().get(i).getId() + "";
                    } else {
                        listProducts += "," + manageOfferComboAdd2Apdater.getList().get(i).getId() + "";
                    }
                }
                CreatComboRequest creatComboRequest = new CreatComboRequest(Config.AdminId, Config.apiToken, datePick, Float.parseFloat(edtPriceCombo.getText().toString()),
                        edtDescription.getText().toString(), listProducts, Config.is_groceryAdmin);
                creatCombo(getActivity(), creatComboRequest);
                break;
            case R.id.im_search_creat_combo:
                if (edtSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.enter_search), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    text_search = edtSearch.getText().toString();
                }
                manageOffersAdapter.clear();
                page_number = 0;
                loadMore();
                break;
            case R.id.fromDate:
                final Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                datePick = year + "-" + new DecimalFormat("00").format(monthOfYear + 1) + "-" +
                                        new DecimalFormat("00").format(dayOfMonth);
                                tvFromDate.setText(DateTimeFormat.formatTime3(datePick));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH) + 1);
                datePickerDialog.setMinDate(now);
                datePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
        }
    }

    private void getListManageProductsToUpdate(int userID, String apiToken,
                                               int category_id, int sub_category_id, int brand_id,
                                               String text_search, final int page_size, int page_number,
                                               int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();
        isClick = false;

        Controller controller = new Controller();
        Call<ManagerProductsToUpdatResqonse> call = controller.service.getListManageProductsToUpdate(userID, apiToken,
                category_id, sub_category_id, brand_id, text_search, page_size, page_number, is_groceryAdmin);
        call.enqueue(new Callback<ManagerProductsToUpdatResqonse>() {
            @Override
            public void onResponse(Response<ManagerProductsToUpdatResqonse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                progerssBarUntil.setVisibeLayoutMain();
                isClick = true;
                if (response != null) {
                    ManagerProductsToUpdatResqonse managerProductsToUpdatResqonse = response.body();
                    if (managerProductsToUpdatResqonse != null) {
                        if (managerProductsToUpdatResqonse.code == 200) {
                            if (managerProductsToUpdatResqonse.result.size() == page_size) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            manageOffersAdapter.addAll(managerProductsToUpdatResqonse.result);
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

    private void creatCombo(final Context context, CreatComboRequest creatComboRequest) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting......");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.creatComboOffer(creatComboRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            manageOfferComboAdd2Apdater.clear();
                            edtDescription.setText("");
                            edtPriceCombo.setText("");
                            priceTotal.setText("");
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DAY_OF_YEAR, 1);
                            Date tomorrow = calendar.getTime();
                            datePick = new SimpleDateFormat("yyyy-MM-dd").format(tomorrow);
                            tvFromDate.setText(DateTimeFormat.formatTime3(datePick));
                        }
                        FragmentManagerOfferCombo.RELOAD_FRM = true;
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
                            arrCategory = new ArrayList<>();
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
}
