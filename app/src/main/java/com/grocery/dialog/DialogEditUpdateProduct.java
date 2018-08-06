package com.grocery.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.adapter.UnitAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.common.DateTimeFormat;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.common.SwitchView;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentManageProducts.FragmentUpdateProducts;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.model.UnitModel;
import com.grocery.request.UpdateDetailRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.ProductsDetailResponse;
import com.grocery.utils.LoadImageUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 10/07/2017.
 */

public class DialogEditUpdateProduct extends Dialog implements View.OnClickListener {
    private CustomTextView tvCancel;
    private CustomTextView tvUpdate;
    private ProgressBar progressBar;
    private CustomTextView tvNameProduct;
    private RoundRectCornerImageView picture;
    private CustomEditText tvPrice;
    private CustomEditText inStock;
    private CustomEditText minStock;
    private CustomTextView tvFromDate;
    private CustomTextView tvToDate;
    private CustomEditText edtOfferPrice;
    private CustomEditText edtLimitNumber;
    private SwitchView switchInstock;
    private SwitchView switchStatus;
    private CRecyclerView listUnit;
    private Spinner spnVat;
    private UnitAdapter unitAdapter;
    private String dateStart;
    private String dateEnd;
    private ArrayList<String> arrDateStartChoose = new ArrayList<>();
    private Date start;
    private Date end;
    public static boolean isLoad = false;

    public DialogEditUpdateProduct(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_edit_udapte_product_info);
        if (context instanceof MainActivity) {
            setOwnerActivity((MainActivity) context);
        }
        initView();
        initData();
        initListener();
    }

    private void initData() {
        loadMore();
        if (switchStatus.isChecked()) {
            enableEDT(false);
        } else {
            enableEDT(true);
        }
    }

    private void loadMore() {
        getProductDetail(getContext(), Config.AdminId,
                Config.apiToken, FragmentUpdateProducts.productsId, Config.is_groceryAdmin);
    }

    private void initListener() {
        tvCancel.setOnClickListener(this);
        switchStatus.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                setStatusTvUpdate(true);
                if (isChecked) {
                    enableEDT(true);
                } else {
                    enableEDT(false);
                }
            }
        });
        switchInstock.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                setStatusTvUpdate(true);
            }
        });
        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);

        edtLimitNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setStatusTvUpdate(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOfferPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setStatusTvUpdate(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setStatusTvUpdate(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        minStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setStatusTvUpdate(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        tvCancel = (CustomTextView) findViewById(R.id.tv_cancel_edit_update_product);
        tvUpdate = (CustomTextView) findViewById(R.id.tv_update_edit_update_product);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvNameProduct = (CustomTextView) findViewById(R.id.nameProduct);
        picture = (RoundRectCornerImageView) findViewById(R.id.imPicture);
        tvPrice = (CustomEditText) findViewById(R.id.price);
        inStock = (CustomEditText) findViewById(R.id.inStock);
        minStock = (CustomEditText) findViewById(R.id.minStock);
        tvFromDate = (CustomTextView) findViewById(R.id.fromDate);
        tvToDate = (CustomTextView) findViewById(R.id.toDate);
        edtOfferPrice = (CustomEditText) findViewById(R.id.offerPrice);
        edtLimitNumber = (CustomEditText) findViewById(R.id.limitProduct);
        switchInstock = (SwitchView) findViewById(R.id.switch_instock);
        switchStatus = (SwitchView) findViewById(R.id.switch_status);
        listUnit = (CRecyclerView) findViewById(R.id.list_units);
        spnVat = (findViewById(R.id.spn_vat));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        unitAdapter = new UnitAdapter(getContext(), new ArrayList<UnitModel>(), this);
        listUnit.setLayoutManager(gridLayoutManager);
        listUnit.setAdapter(unitAdapter);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // set popup Select Country
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnVat);
            popupWindow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.radius_edt));
            popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapterVAT = new ArrayAdapter(getContext(), R.layout.item_spinner_2, getListVAT());
        spnVat.setAdapter(adapterVAT);
    }

    public void setStatusTvUpdate(boolean check) {
        if (check) {
            tvUpdate.setEnabled(true);
            tvUpdate.setTextColor(Color.WHITE);
            tvUpdate.setBackgroundResource(R.drawable.render_search_2);
        } else {
            tvUpdate.setEnabled(false);
            tvUpdate.setTextColor(getContext().getResources().getColor(R.color.text_save));
            tvUpdate.setBackgroundResource(R.drawable.radius_list);
        }
    }

    public void enableEDT(boolean check) {
        if (check) {
            edtLimitNumber.setEnabled(true);
            edtOfferPrice.setEnabled(true);
            tvFromDate.setEnabled(true);
            tvToDate.setEnabled(true);
        } else {
            edtLimitNumber.setEnabled(false);
            edtOfferPrice.setEnabled(false);
            tvFromDate.setEnabled(false);
            tvToDate.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel_edit_update_product:
                cancel();
                break;
            case R.id.fromDate:
                final Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String datePick = year + "-" + new DecimalFormat("00").format(monthOfYear + 1) + "-" + dayOfMonth;
                                dateStart = DateTimeFormat.formatTime3(datePick);
                                tvFromDate.setText(dateStart);
                                arrDateStartChoose = converDate(tvFromDate.getText().toString());
                                start = new Date();
                                start.setMonth(monthOfYear);
                                start.setDate(dayOfMonth);
                                start.setYear(year);
                                setStatusTvUpdate(true);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setMinDate(now);
                datePickerDialog.show(getOwnerActivity().getFragmentManager(), "");
                break;
            case R.id.toDate:
                final Calendar now1 = Calendar.getInstance();
                DatePickerDialog datePickerDialog1 = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String datePick = year + "-" + new DecimalFormat("00").format(monthOfYear + 1) + "-" + dayOfMonth;
                                dateEnd = DateTimeFormat.formatTime3(datePick);
                                tvToDate.setText(dateEnd);
                                end = new Date();
                                end.setMonth(monthOfYear);
                                end.setDate(dayOfMonth);
                                end.setYear(year);
                                setStatusTvUpdate(true);
                            }
                        },
                        now1.get(Calendar.YEAR),
                        now1.get(Calendar.MONTH),
                        now1.get(Calendar.DAY_OF_MONTH)
                );
                now1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDateStartChoose.get(0)));
                now1.set(Calendar.MONTH, Integer.parseInt(DateTimeFormat.formatDate3(arrDateStartChoose.get(1))) - 1);
                now1.set(Calendar.YEAR, Integer.parseInt(arrDateStartChoose.get(2)));
                datePickerDialog1.setMinDate(now1);
                datePickerDialog1.show(getOwnerActivity().getFragmentManager(), "");
                break;
            case R.id.tv_update_edit_update_product:
                int instock_status = 0;
                int offer_status = 0;
                if (switchStatus.isChecked()) {
                    offer_status = 1;
                }
                if (switchInstock.isChecked()) {
                    instock_status = 1;
                }
                if (checkOK()) {
                    UpdateDetailRequest updateDetailRequest = new UpdateDetailRequest(Config.AdminId, Config.apiToken, FragmentUpdateProducts.productsId,
                            Integer.parseInt(inStock.getText().toString()), Integer.parseInt(minStock.getText().toString()), instock_status,
                            offer_status, DateTimeFormat.formatDateTime1(dateStart.toString()), DateTimeFormat.formatDateTime1(dateEnd.toString()), Integer.parseInt(edtLimitNumber.getText().toString()),
                            Float.parseFloat(edtOfferPrice.getText().toString()), Config.is_groceryAdmin, (ArrayList<UnitModel>) unitAdapter.getList());
                    updateDetails(getOwnerActivity(), updateDetailRequest);
                }
                break;
        }
    }

    public boolean checkOK() {
        if (inStock.getText().toString().isEmpty()) {
            Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.enter_AvailableStock), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (minStock.getText().toString().isEmpty()) {
            Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.enter_MinStock), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (switchStatus.isChecked()) {
            if (edtLimitNumber.getText().toString().isEmpty()) {
                Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.enter_LimitProducts), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (edtOfferPrice.getText().toString().isEmpty()) {
                Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.enter_offerPrice), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (start != null && end != null) {
                if (start.compareTo(end) > 0) {
                    Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.compareto_date), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<String> converDate(String inputDate) {
        ArrayList<String> outPut = new ArrayList<>();
        String[] arr;
        arr = inputDate.split("-");
        for (int i = 0; i < arr.length; i++) {
            outPut.add(arr[i]);
        }
        return outPut;
    }

    private void getProductDetail(final Context context, int userID, String apiToken, int product_id, int is_groceryAdmin) {
        Controller controller = new Controller();
        Call<ProductsDetailResponse> call = controller.service.getproductsDetails(userID, apiToken, product_id, is_groceryAdmin);
        call.enqueue(new Callback<ProductsDetailResponse>() {
            @Override
            public void onResponse(Response<ProductsDetailResponse> response, Retrofit retrofit) {
                if (response != null) {
                    ProductsDetailResponse productsDetailResponse = response.body();
                    if (productsDetailResponse != null) {
                        if (productsDetailResponse.code == 200) {
                            unitAdapter.addAll(productsDetailResponse.result.getUnit());
                            ItemManagerProductsUpdate productsDetailModel = productsDetailResponse.result.getProduct();
                            LoadImageUtils loadImageUtils = new LoadImageUtils(context, productsDetailModel.getPhoto(), picture, progressBar);
                            loadImageUtils.loadImageWithPicaso();
                            tvNameProduct.setText(productsDetailModel.getName().toString());
                            tvPrice.setText(productsDetailModel.getPrice() + "");
                            inStock.setText(productsDetailModel.getInstock() + "");
                            minStock.setText(productsDetailModel.getMin_stock() + "");
                            tvFromDate.setText(DateTimeFormat.formatTime2(productsDetailModel.getFrom_date()));
                            tvToDate.setText(DateTimeFormat.formatTime2(productsDetailModel.getEnd_date()));
                            arrDateStartChoose = converDate(tvFromDate.getText().toString());
                            dateStart = tvFromDate.getText().toString();
                            dateEnd = tvFromDate.getText().toString();
                            edtOfferPrice.setText(productsDetailModel.getOffer_price() + "");
                            edtLimitNumber.setText(productsDetailModel.getLimit_number() + "");
                            if (productsDetailModel.getInstock_status() == 1) {
                                switchInstock.setChecked(true);
                            } else {
                                switchInstock.setChecked(false);
                            }
                            if (productsDetailModel.getStatus() == 1) {
                                switchStatus.setChecked(true);
                                enableEDT(true);
                            } else {
                                switchStatus.setChecked(false);
                                enableEDT(false);
                            }
                            setStatusTvUpdate(false);
                        } else {
                            Toast.makeText(context, productsDetailResponse.message, Toast.LENGTH_SHORT).show();
                            dismiss();
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

    private void updateDetails(final Context context, UpdateDetailRequest updateDetailRequest) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting.....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateDetails(updateDetailRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            isLoad = true;
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

    private List<String> getListVAT() {
        List<String> list = new ArrayList<>();
        list.add("5");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");

        return list;
    }
}
