package com.grocery.fragment.fragmentAllProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.adapter.SubCategoryAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomCheckBox;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DiaglogSave;
import com.grocery.model.ItemSubCategory;
import com.grocery.request.UpdateSubCategoryRequest;
import com.grocery.response.BaseResponse;
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
 * Created by ThanhBeo on 23/06/2017.
 */

public class FragmentProSubCategory extends Fragment implements View.OnClickListener {
    private static int CATEGORY_ID = 0;
    private int sortType = 0;
    private ImageView imClose;
    private RelativeLayout layoutSort;
    private ImageView imSearch;
    private CustomTextView btnSave;
    private CustomTextView tvNumber;
    private CustomTextView tvSelect;
    private ImageView imRound;
    private ProgerssBarUntil progerssBarUntil;
    private SubCategoryAdapter subCategoryAdapter;
    private CRecyclerView listItem;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_number = 0;
    private boolean isCanNext = true;
    private boolean isProgessingLoadMore = true;
    private RelativeLayout btnSort;
    private Spinner spnChooseCategory;
    private Spinner spnSubCategory;
    private ArrayList<ItemSubCategory> arrAllSub;
    private boolean isClean = true;
    private boolean isLoad = true;
    private CustomCheckBox cbRecently;
    private CustomCheckBox cbNotAdd;
    private CustomCheckBox cbAdded;
    private ArrayList<CustomCheckBox> arrCb;
    private CustomTextView btnSubmit;
    private CustomTextView tvRecently;
    private CustomTextView tvAdded;
    private CustomTextView tvNotAdd;
    private ArrayList<CustomTextView> arrTvCb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_products_sub_category, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        initView(view);
        initData();
        initListener(view);
        return view;
    }

    private void initListener(View view) {
        btnSort.setOnClickListener(this);
        imClose.setOnClickListener(this);
        imSearch.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        listItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Utils.isReadyForPullEnd(recyclerView) && isCanNext && !isProgessingLoadMore) {
                    isClean = false;
                    loadMore(CATEGORY_ID);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cleanAdapter();
                swipeRefreshLayout.setRefreshing(false);
                subCategoryAdapter.clear();
                page_number = 0;
                spnChooseCategory.setSelection(0);
                spnSubCategory.setAdapter(null);
                SubCategoryAdapter.arrAdd = new ArrayList<ItemSubCategory>();
                SubCategoryAdapter.arrDelete = new ArrayList<ItemSubCategory>();
                isLoad = false;
                resetTypeSort();
                loadMore(CATEGORY_ID);
            }
        });

        spnChooseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idCategory = getIdToName(parent.getSelectedItem() + "");
                if (isLoad) {
                    page_number = 0;
                    loadMore(idCategory);
                }
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
                    for (int i = 0; i < arrCb.size(); i++) {
                        arrCb.get(i).setChecked(false);
                        arrTvCb.get(i).setTextColor(getResources().getColor(R.color.text_save));
                    }
                    arrCb.get(finalI).setChecked(true);
                    arrTvCb.get(finalI).setTextColor(getResources().getColor(R.color.bg_search_main));
                }
            });
        }
    }

    private void cleanAdapter() {
        subCategoryAdapter.clear();
        btnSave.setEnabled(false);
        btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
        btnSave.setTextColor(getResources().getColor(R.color.text_save));
        tvNumber.setTextColor(getResources().getColor(R.color.text_save));
        tvSelect.setTextColor(getResources().getColor(R.color.text_save));
        imRound.setBackgroundResource(R.mipmap.icon_selected);
        tvNumber.setText(0 + "");
    }

    public int getIdToName(String name) {
        for (int i = 0; i < Config.arrItemCategory.size(); i++) {
            if (Config.arrItemCategory.get(i).getName().toString().equals(name)) {
                return Config.arrItemCategory.get(i).getId();
            }
        }
        return 0;
    }

    private void loadMore(int idCategory) {
        if (idCategory == CATEGORY_ID) {
            getSubCategory(Config.AdminId, Config.apiToken, Config.AdminId, idCategory,
                    Config.PAGE_SIZE, ++page_number, sortType, Config.is_groceryAdmin);
        } else {
            getSubCategory(Config.AdminId, Config.apiToken, Config.AdminId, idCategory,
                    1000, 1, sortType, Config.is_groceryAdmin);
        }
    }

    private void initData() {
        /*list sub category*/
        listItem.setAdapter(subCategoryAdapter);
        /*spinner category*/
        ArrayList<String> arrNameCategory = new ArrayList<>();
        for (int i = 0; i < Config.arrItemCategory.size(); i++) {
            if (i == 0) {
                arrNameCategory.add("All");
            }
            arrNameCategory.add(Config.arrItemCategory.get(i).getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                R.layout.item_spinner_2, arrNameCategory);
        spnChooseCategory.setAdapter(adapter);

        /*spinner sub category*/
        ArrayList<String> arrNameSubCategory = new ArrayList<>();
        arrNameSubCategory.add("All");
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                R.layout.item_spinner_2, arrNameSubCategory);
        spnSubCategory.setAdapter(adapter1);
        /*init sort*/
        arrTvCb = new ArrayList<>();
        arrTvCb.add(tvAdded);
        arrTvCb.add(tvNotAdd);
        arrTvCb.add(tvRecently);
        arrCb = new ArrayList<>();
        arrCb.add(cbAdded);
        arrCb.add(cbNotAdd);
        arrCb.add(cbRecently);
    }

    private void initView(View view) {
        arrAllSub = new ArrayList<>();
        imSearch = (ImageView) view.findViewById(R.id.im_search_sub_category);
        layoutSort = (RelativeLayout) view.findViewById(R.id.layout_sort);
        imClose = (ImageView) view.findViewById(R.id.icon_close_sort_sub_category);
        btnSort = view.findViewById(R.id.layoutSort);
        btnSave = (CustomTextView) view.findViewById(R.id.btn_save_manager_category);
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
        /*init spinner*/
        spnChooseCategory = (Spinner) view.findViewById(R.id.spn_category);
        spnSubCategory = (Spinner) view.findViewById(R.id.spn_sub_category);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnChooseCategory);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spnSubCategory);

            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            popupWindow1.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(450);
            popupWindow1.setHeight(450);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        listItem = (CRecyclerView) view.findViewById(R.id.listSubCategory);
        subCategoryAdapter = new SubCategoryAdapter(getActivity(), new ArrayList<ItemSubCategory>(), this);
    }

    public void updateLayoutSelectes(int count) {
        if (count == 0) {
            btnSave.setEnabled(false);
            btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
            btnSave.setTextColor(getResources().getColor(R.color.text_save));
            tvNumber.setTextColor(getResources().getColor(R.color.text_save));
            tvSelect.setTextColor(getResources().getColor(R.color.text_save));
            imRound.setBackgroundResource(R.mipmap.icon_selected);
        } else {
            btnSave.setEnabled(true);
            btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            btnSave.setTextColor(Color.WHITE);
            tvNumber.setTextColor(getResources().getColor(R.color.colorApp));
            tvSelect.setTextColor(getResources().getColor(R.color.colorApp));
            imRound.setBackgroundResource(R.mipmap.icon_selected_2);
        }
        tvNumber.setText(count + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutSort:
                layoutSort.setVisibility(LinearLayout.VISIBLE);
                break;
            case R.id.icon_close_sort_sub_category:
                layoutSort.setVisibility(LinearLayout.GONE);
                break;
            case R.id.im_search_sub_category:
                ArrayList<ItemSubCategory> arr = new ArrayList<>();
                if (spnChooseCategory.getSelectedItem().toString().equals("All")) {
                    arr.addAll(arrAllSub);
                } else {
                    if (spnSubCategory.getSelectedItem().toString().equals("All")) {
                        arr.addAll(arrAllSub);
                    } else {
                        for (int i = 0; i < arrAllSub.size(); i++) {
                            if (arrAllSub.get(i).getName().toString().equals(spnSubCategory.getSelectedItem().toString())) {
                                arr.add(arrAllSub.get(i));
                            }
                        }
                    }
                }
                subCategoryAdapter.filter(arr);
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
                            UpdateSubCategoryRequest updateSubCategoryRequest = new UpdateSubCategoryRequest(Config.AdminId,
                                    Config.apiToken, converListStringId(SubCategoryAdapter.arrDelete),
                                    converListStringId(SubCategoryAdapter.arrAdd), Config.is_groceryAdmin);
                            updateSubCategory(getActivity(), updateSubCategoryRequest);
                        }
                        Config.checkUpdate = false;
                        Config.number_update = 0;
                    }
                });
                break;
            case R.id.btnSubmit:
                page_number = 0;
                cleanAdapter();
                sortType = getTypeSort();
                loadMore(CATEGORY_ID);
                break;
        }
    }

    public void resetTypeSort() {
        for (int i = 0; i < arrCb.size(); i++) {
            arrCb.get(i).setChecked(false);
            arrTvCb.get(i).setTextColor(getResources().getColor(R.color.text_save));
        }
        sortType = 0;
        layoutSort.setVisibility(View.GONE);
    }

    public int getTypeSort() {
        for (int i = 0; i < arrCb.size(); i++) {
            if (arrCb.get(i).isCheck()) {
                return i + 1;
            }
        }
        return 0;
    }

    public String converListStringId(ArrayList<ItemSubCategory> arrayList) {
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

    private void getSubCategory(int userID, String apiToken, int grocery_id, int category_id,
                                int page_size, int page_number, int sort_type, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<SubCategoryResponse> call = controller.service.getSubCategory(userID, apiToken,
                grocery_id, category_id, page_size, page_number, sort_type, is_groceryAdmin);
        call.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Response<SubCategoryResponse> response, Retrofit retrofit) {
                isProgessingLoadMore = false;
                progerssBarUntil.setVisibeLayoutMain();
                isClean = true;
                isLoad = true;
                if (response != null) {
                    SubCategoryResponse subCategoryResponse = response.body();
                    if (subCategoryResponse != null) {
                        if (subCategoryResponse.code == 200) {
                            if (isClean) {
                                cleanAdapter();
                            }
                            arrAllSub = new ArrayList<>();
                            arrAllSub.addAll(subCategoryResponse.result);
                            if (subCategoryResponse.result.size() == Config.PAGE_SIZE) {
                                isCanNext = true;
                            } else {
                                isCanNext = false;
                            }
                            subCategoryAdapter.addAll(subCategoryResponse.result);
                            ArrayList<String> arrNameSubCategory = new ArrayList<>();
                            arrNameSubCategory.add("All");
                            if (!spnChooseCategory.getSelectedItem().toString().equals("All")) {
                                for (int i = 0; i < arrAllSub.size(); i++) {
                                    arrNameSubCategory.add(arrAllSub.get(i).getName().toString());
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

    private void updateSubCategory(final Context context, UpdateSubCategoryRequest updateSubCategoryRequest) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateSubCategory(updateSubCategoryRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            cleanAdapter();
                            page_number = 0;
                            spnChooseCategory.setSelection(0);
                            spnSubCategory.setSelection(0);
                            loadMore(CATEGORY_ID);
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
