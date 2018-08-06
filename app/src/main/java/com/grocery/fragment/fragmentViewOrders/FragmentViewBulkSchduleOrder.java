package com.grocery.fragment.fragmentViewOrders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grocery.R;
import com.grocery.activity.MainViewOrders;
import com.grocery.adapter.DeleveryBoyOrderAdapter;
import com.grocery.adapter.ViewProductsOrderAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.common.DateTimeFormat;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogRejectOrders;
import com.grocery.fragment.fragmentOrders.FragmentScheduleOrders;
import com.grocery.model.ItemAlternavite;
import com.grocery.model.ItemProductsOrders;
import com.grocery.model.OrderDetails;
import com.grocery.model.UnitModel;
import com.grocery.request.SendOrderConfirmRequest;
import com.grocery.request.SendReplacementRequest;
import com.grocery.request.UpdateNormalOrderRequets;
import com.grocery.response.BaseResponse;
import com.grocery.utils.LoadImageUtils;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 09/10/2017.
 */

public class FragmentViewBulkSchduleOrder extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    private MainViewOrders mainViewOrders;
    private OrderDetails orderDetails;
    private MapFragment mMapFragment;
    private GoogleMap mMap;
    private DeleveryBoyOrderAdapter deleveryBoyOrderAdapter;
    private CustomTextView tvOrderId;
    private CustomTextView pointDay;
    private CustomTextView pointHour;
    private LinearLayout layoutDay;
    private LinearLayout layoutHour;
    private CustomTextView tvMin;
    private CustomTextView tvSec;
    private CustomTextView tvHour;
    private CustomTextView tvDay;
    private CustomTextviewBold tvInComing;
    private CustomTextviewBold tvExpectedTime;
    private CustomTextviewBold tvExpectedDate;
    private CustomTextView tvReject;
    private CustomTextView tvAccept;
    private CustomTextView tvNumberItem;
    private CustomTextView tvNumberItemDelete;
    private CustomTextView tvRedeemendAmt;
    private CustomTextView tvDiscount;
    private CustomTextView tvAmount;
    private CustomTextView tvTotalMoney;
    private CustomTextView tvViewMap;
    private ImageView imOrder;
    private CRecyclerView litsProducts;
    public static ViewProductsOrderAdapter viewProductsOrderAdapter;
    private LinearLayout layoutTime;
    private boolean isMin5 = false;
    private LinearLayout layouSchedule, layoutBulk;
    private CustomTextView tvAcceptLateShedule;
    private Spinner spnSelectDeliveryBoy;
    private CustomTextView tvDeliveredDate;
    private CustomTextviewBold tvDeliveryTime;
    private CustomTextView tvDeliveryTimeSelect;
    private CustomTextView tvAcceptShedule;
    private CustomTextView btnSendConfim;
    private CustomTextView tvAcceptBulk;
    private CustomTextView tvPaymentType;
    public static ArrayList<ItemAlternavite>[] arrRepleace;
    private CustomTextView tvSendRepleace;
    private ImageView btnDown1;
    private ImageView btnDown2;
    private ImageView btnDown3;
    private ImageView btnUp1;
    private ImageView btnUp2;
    private ImageView btnUp3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_special_orders, container, false);
        mainViewOrders = (MainViewOrders) getActivity();
        mainViewOrders.dismissDialog();
        orderDetails = mainViewOrders.getOrderDetails();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        initArrPleace();
        UpdateTimeRemaingAsyncTask updateTimeRemaingAsyncTask = new UpdateTimeRemaingAsyncTask(orderDetails.getOrder_info().getRemaining_time());
        if (Build.VERSION.SDK_INT >= 11) {
            updateTimeRemaingAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            updateTimeRemaingAsyncTask.execute();
        }
        tvOrderId.setText(orderDetails.getOrder_info().getId() + "");
        tvInComing.setText(DateTimeFormat.formatTime1(orderDetails.getOrder_info().getCreated_at()));
        tvExpectedTime.setText(DateTimeFormat.formatTime1(orderDetails.getOrder_info().getDelivery_time()));
        tvExpectedDate.setText(DateTimeFormat.formatTime2(orderDetails.getOrder_info().getDelivery_time()));
        float totalMoney = orderDetails.getOrder_info().getTotal_money() + orderDetails.getOrder_info().getTip_money() +
                orderDetails.getOrder_info().getService_fee();
        tvTotalMoney.setText(getResources().getString(R.string.aed1) + " " + totalMoney);
        float redeemenAmt = orderDetails.getOrder_info().getCash_back_money() + orderDetails.getOrder_info().getReward_money();
        if (orderDetails.getOrder_info().getIs_redeem() == 1) {
            tvRedeemendAmt.setText(getResources().getString(R.string.aed1) + " " + redeemenAmt);
        } else {
            tvRedeemendAmt.setText(getResources().getString(R.string.aed1) + " " + 0.0);
        }
        tvDiscount.setText(getResources().getString(R.string.aed1) + " " + getDiscount());
        float amount = totalMoney - redeemenAmt - getDiscount();
        tvAmount.setText(getResources().getString(R.string.aed1) + amount);
        if (orderDetails.getOrder_info().getOrder_type() == 4) {
            imOrder.setImageResource(R.mipmap.icon_bulk_full);
            layouSchedule.setVisibility(View.GONE);
            layoutBulk.setVisibility(View.VISIBLE);
        } else {
            layoutBulk.setVisibility(View.GONE);
            layouSchedule.setVisibility(View.VISIBLE);
            imOrder.setImageResource(R.mipmap.icon_schedule);
        }
        if (Config.statusTypeOrder == 3) {
            tvAcceptBulk.setVisibility(View.VISIBLE);
            tvAcceptBulk.setEnabled(false);
            tvAcceptBulk.setTextColor(getResources().getColor(R.color.text_save));
            tvAcceptBulk.setBackgroundResource(R.drawable.render_gray);
        }
        if (Config.statusTypeOrder == 2) {
            tvAcceptLateShedule.setEnabled(true);
            tvAcceptLateShedule.setTextColor(getResources().getColor(R.color.text_save));
            tvAcceptLateShedule.setBackgroundResource(R.drawable.render_gray);
        }
        if (orderDetails.getOrder_info().getIs_confirm() == 0) {
            btnSendConfim.setText(getResources().getString(R.string.send_for_order));
            btnSendConfim.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSendConfim.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));

        } else if (orderDetails.getOrder_info().getIs_confirm() == 1) {
            btnSendConfim.setEnabled(false);
            btnSendConfim.setText(getResources().getString(R.string.waiting_confim));
            btnSendConfim.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSendConfim.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bulk_orders));
            tvAcceptBulk.setVisibility(View.VISIBLE);
            tvAcceptBulk.setEnabled(false);
            tvAcceptBulk.setTextColor(getResources().getColor(R.color.text_save));
            tvAcceptBulk.setBackgroundResource(R.drawable.render_gray);
        }
        if (orderDetails.getOrder_info().getIs_confirm() == 2) {
            btnSendConfim.setEnabled(false);
            btnSendConfim.setText(getResources().getString(R.string.order_confirm));
            btnSendConfim.setTextColor(getResources().getColor(R.color.bg_search_main));
            btnSendConfim.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            btnSendConfim.setEnabled(false);
            tvAcceptBulk.setVisibility(View.VISIBLE);
            tvAcceptBulk.setEnabled(true);
            tvAcceptBulk.setTextColor(getResources().getColor(R.color.colorWhite));
            tvAcceptBulk.setBackgroundResource(R.drawable.btn_open_again_shop);
        }

        ArrayList<String> arrName = new ArrayList<>();
        for (int i = 0; i < orderDetails.getDelivery_boys().size(); i++) {
            arrName.add(orderDetails.getDelivery_boys().get(i).getDelivery_boy_name().toString());
        }
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),
                R.layout.item_spn_delivery_boy, arrName);
        spnSelectDeliveryBoy.setAdapter(adapter1);
        tvDeliveryTime.setText(DateTimeFormat.formatTime1(orderDetails.getOrder_info().getDelivery_time()));
        tvDeliveredDate.setText(DateTimeFormat.formatTime2(orderDetails.getOrder_info().getDelivery_time()));
        tvDeliveryTimeSelect.setText(tvDeliveryTime.getText().toString());
        tvPaymentType.setSelected(true);
        if (orderDetails.getOrder_info().getPayment_type() == 0) {
            tvPaymentType.setText(getResources().getString(R.string.COD));
        } else {
            tvPaymentType.setText(getResources().getString(R.string.Bring_card_reader));
        }
    }

    public void setStatusTvSendRepleace(boolean show) {
        if (show) {
            tvSendRepleace.setVisibility(View.VISIBLE);
        } else {
            tvSendRepleace.setVisibility(View.INVISIBLE);
        }
    }

    public float getDiscount() {
        float discount = 0;
        for (int i = 0; i < orderDetails.getProducts().size(); i++) {
            discount += (orderDetails.getProducts().get(i).getPrice() - orderDetails.getProducts().get(i).getPrice_discount()) * orderDetails.getProducts().get(i).getQuantity();
        }
        return discount;
    }

    private void initView(View view) {
        if (mainViewOrders.getOrderDetails() == null) {
            return;
        }
        tvOrderId = view.findViewById(R.id.tvOrderId);
        /*init listView*/
        deleveryBoyOrderAdapter = new DeleveryBoyOrderAdapter(getActivity(), orderDetails.getDelivery_boys());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        litsProducts = (CRecyclerView) view.findViewById(R.id.listProducts);
        viewProductsOrderAdapter = new ViewProductsOrderAdapter(getActivity(), orderDetails.getProducts(), this, orderDetails.getOrder_info().getOrder_type(),
                orderDetails.getOrder_info().getIs_confirm());
        litsProducts.setAdapter(viewProductsOrderAdapter);
        /*inti map*/
        mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragment_map);
        mMapFragment.getMapAsync(this);

        /*init spnner*/
        spnSelectDeliveryBoy = (Spinner) view.findViewById(R.id.spnSelectDeliveryBoy);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spnSelectDeliveryBoy);

            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_edt));
            // Set popupWindow height to 500px
            popupWindow.setHeight(450);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        /**/
        tvMin = (CustomTextView) view.findViewById(R.id.tvMin);
        tvSec = (CustomTextView) view.findViewById(R.id.tvSec);
        tvDay = (CustomTextView) view.findViewById(R.id.tvDay);
        tvHour = (CustomTextView) view.findViewById(R.id.tvHour);
        tvInComing = (CustomTextviewBold) view.findViewById(R.id.tvInComing);
        tvExpectedTime = (CustomTextviewBold) view.findViewById(R.id.tvExpectedTime);
        tvExpectedDate = (CustomTextviewBold) view.findViewById(R.id.tvExpectedDate);
        tvTotalMoney = (CustomTextView) view.findViewById(R.id.tvTotalAmount);
        tvTotalMoney.setSelected(true);
        tvViewMap = (CustomTextView) view.findViewById(R.id.viewMap);
        tvNumberItem = (CustomTextView) view.findViewById(R.id.tvNumberItem);
        tvNumberItemDelete = (CustomTextView) view.findViewById(R.id.tvNumberItemDelete);
        tvNumberItem.setText(viewProductsOrderAdapter.getList().size() + "");
        tvReject = (CustomTextView) view.findViewById(R.id.tvReject);
        tvAccept = (CustomTextView) view.findViewById(R.id.tvAccept);
        layoutTime = (LinearLayout) view.findViewById(R.id.layoutTime);
        tvRedeemendAmt = (CustomTextView) view.findViewById(R.id.redeemendAmt);
        tvRedeemendAmt.setSelected(true);
        tvDiscount = (CustomTextView) view.findViewById(R.id.discount);
        tvDiscount.setSelected(true);
        tvAmount = (CustomTextView) view.findViewById(R.id.Amount);
        tvAmount.setSelected(true);
        imOrder = (ImageView) view.findViewById(R.id.icon_orders);
        layouSchedule = (LinearLayout) view.findViewById(R.id.layouSchedule);
        layoutBulk = view.findViewById(R.id.layoutBulk);
        tvAcceptLateShedule = (CustomTextView) view.findViewById(R.id.tvAcceptLateShedule);
        tvDeliveredDate = (CustomTextView) view.findViewById(R.id.tvDeliveredDate);
        tvDeliveryTime = (CustomTextviewBold) view.findViewById(R.id.tvDeleviryTime);
        tvDeliveryTimeSelect = (CustomTextView) view.findViewById(R.id.tvDeliveryTimeSelect);
        tvAcceptShedule = (CustomTextView) view.findViewById(R.id.tvAcceptShedule);
        btnSendConfim = (CustomTextView) view.findViewById(R.id.btnSendConfim);
        ;
        layoutDay = (LinearLayout) view.findViewById(R.id.layoutDay);
        layoutHour = (LinearLayout) view.findViewById(R.id.layoutHour);
        pointDay = (CustomTextView) view.findViewById(R.id.pointDay);
        pointHour = (CustomTextView) view.findViewById(R.id.pointHour);
        tvAcceptBulk = (CustomTextView) view.findViewById(R.id.tvAcceptBulk);
        tvPaymentType = (CustomTextView) view.findViewById(R.id.tvPaymentType);
        tvSendRepleace = (CustomTextView) view.findViewById(R.id.tvSendRepleace);
        /**/
        btnUp1 = view.findViewById(R.id.btnUp1);
        btnUp2 = view.findViewById(R.id.btnUp2);
        btnUp3 = view.findViewById(R.id.btnUp3);
        btnDown1 = view.findViewById(R.id.btnDown1);
        btnDown2 = view.findViewById(R.id.btnDown2);
        btnDown3 = view.findViewById(R.id.btnDown3);
    }

    public void initArrPleace() {
        arrRepleace = new ArrayList[orderDetails.getProducts().size()];
        for (int i = 0; i < orderDetails.getProducts().size(); i++) {
            arrRepleace[i] = new ArrayList<ItemAlternavite>();
        }
    }

    public void updateNumberItemDelete(int numberDelete) {
        tvNumberItemDelete.setText(numberDelete + "");
    }

    private void initListener() {
        tvViewMap.setOnClickListener(this);
        tvReject.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        tvAcceptLateShedule.setOnClickListener(this);
        tvDeliveredDate.setOnClickListener(this);
        tvDeliveryTimeSelect.setOnClickListener(this);
        tvAcceptShedule.setOnClickListener(this);
        tvAcceptBulk.setOnClickListener(this);
        btnSendConfim.setOnClickListener(this);
        tvSendRepleace.setOnClickListener(this);
        btnUp1.setOnClickListener(this);
        btnUp2.setOnClickListener(this);
        btnUp3.setOnClickListener(this);
        btnDown1.setOnClickListener(this);
        btnDown2.setOnClickListener(this);
        btnDown3.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawMarker(googleMap);
    }

    public void drawMarker(GoogleMap googleMap) {
        LatLng latLng = new LatLng(orderDetails.getOrder_info().getBuilding_latitude(), orderDetails.getOrder_info().getBuilding_longitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        View viewMarker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_maker, null);
        CircleImageView imMarker = (CircleImageView) viewMarker.findViewById(R.id.image_marker);
        if (orderDetails.getOrder_info().getUser_photo() == null) {
            Picasso.with(getActivity()).load(R.drawable.icon_users).into(imMarker);
        } else {
            LoadImageUtils loadImageUtils = new LoadImageUtils(getActivity(), orderDetails.getOrder_info().getUser_photo(), imMarker);
            loadImageUtils.loadImageWithPicasoUSer();
        }
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).
                icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), viewMarker))));
    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewMap:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(
                        "geo:" + orderDetails.getOrder_info().getBuilding_latitude() +
                                "," + orderDetails.getOrder_info().getBuilding_longitude() +
                                "?q=" + orderDetails.getOrder_info().getBuilding_latitude() +
                                "," + orderDetails.getOrder_info().getBuilding_longitude() +
                                "(" + orderDetails.getOrder_info().getBuilding_name() + ")"));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                break;
            case R.id.tvReject:
                DialogRejectOrders dialogRejectOrders = new DialogRejectOrders(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogRejectOrders.setItemOrderInfor(orderDetails.getOrder_info());
                dialogRejectOrders.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogRejectOrders.show();
                break;
            case R.id.tvAccept:
                Config.showBulkOrder = true;
                mainViewOrders.finish();
                break;
            case R.id.tvAcceptLateShedule:
                Config.showScheduleOrder = true;
                mainViewOrders.finish();
                break;
            case R.id.tvDeliveredDate:
                final Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String datePick = year + "-" + new DecimalFormat("00").format(monthOfYear + 1) + "-" + dayOfMonth;
                                tvDeliveredDate.setText(DateTimeFormat.formatTime3(datePick));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                Date d1 = null;
                try {
                    d1 = df1.parse(tvExpectedDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(d1);
                datePickerDialog.setMinDate(cal1);
                datePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvDeliveryTimeSelect:
                final Calendar now1 = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
                                tvDeliveryTime.setText(DateTimeFormat.formatTime(time));
                                tvDeliveryTimeSelect.setText(DateTimeFormat.formatTime(time));
                            }

//                            @Override
//                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//                                String time = hourOfDay + ":" + new DecimalFormat("00").format(minute);
//                                tvDeliveryTime.setText(DateTimeFormat.formatTime(time));
//                                tvDeliveryTimeSelect.setText(DateTimeFormat.formatTime(time));
//                            }
                        },
                        now1.get(Calendar.HOUR_OF_DAY),
                        now1.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.show(getActivity().getFragmentManager(), "");
                break;
            case R.id.tvAcceptBulk:
            case R.id.tvAcceptShedule:
                String timePush = DateTimeFormat.formatDateTime1(tvDeliveredDate.getText().toString()) + " " +
                        DateTimeFormat.formatTime5(tvDeliveryTime.getText().toString());
                if (!checkDateTime(timePush)) {
                    return;
                }
                UpdateNormalOrderRequets updateNormalOrderRequets = new UpdateNormalOrderRequets(Config.AdminId, Config.apiToken,
                        orderDetails.getOrder_info().getId(), Config.ACCEPT_ORDER, getDeliveryBoyId(), timePush, 0, "", Config.is_groceryAdmin);
                accpetOrder(getActivity(), updateNormalOrderRequets);
                break;
            case R.id.btnSendConfim:
                SendOrderConfirmRequest sendOrderConfirmRequest = new SendOrderConfirmRequest(Config.AdminId, Config.apiToken,
                        orderDetails.getOrder_info().getId(), Config.is_groceryAdmin);
                sendOrderConfirm(getActivity(), sendOrderConfirmRequest, this);
                break;
            case R.id.tvSendRepleace:
                ArrayList<ItemProductsOrders> itemAlternative = new ArrayList<>();
                for (int i = 0; i < arrRepleace.length; i++) {
                    ItemProductsOrders itemReplace = viewProductsOrderAdapter.getList().get(i);
                    if (arrRepleace[i].size() > 0) {
                        itemAlternative.add(new ItemProductsOrders(itemReplace.getProduct_id(),
                                itemReplace.getQuantity(), itemReplace.getUnit_product_id(), 1, itemReplace.getPrice(),
                                itemReplace.getPrice() - itemReplace.getPrice_discount(), itemReplace.getType(), 0));
                        for (int j = 0; j < arrRepleace[i].size(); j++) {
                            itemAlternative.add(new ItemProductsOrders(arrRepleace[i].get(j).getProduct_id(),
                                    arrRepleace[i].get(j).getQuantity(), getUnitProductId(arrRepleace[i].get(j).getUnit()), 0, arrRepleace[i].get(j).getPrice(),
                                    arrRepleace[i].get(j).getPrice() - arrRepleace[i].get(j).getDiscount_price(), arrRepleace[i].get(j).getType(), itemReplace.getProduct_id()));
                        }
                    } else {
                        itemAlternative.add(new ItemProductsOrders(itemReplace.getProduct_id(),
                                itemReplace.getQuantity(), itemReplace.getUnit_product_id(), 0, itemReplace.getPrice(),
                                itemReplace.getPrice() - itemReplace.getPrice_discount(), itemReplace.getType(), 0));
                    }
                }
                SendReplacementRequest sendReplacementRequest = new SendReplacementRequest(Config.AdminId, Config.apiToken,
                        orderDetails.getOrder_info().getId(), Config.is_groceryAdmin, itemAlternative);
                sendRepleacement(getActivity(), sendReplacementRequest);
                break;
            case R.id.btnUp1:
                litsProducts.smoothScrollBy(0, -2 * Config.scrollItem);
                break;
            case R.id.btnUp2:
                litsProducts.smoothScrollBy(0, -4 * Config.scrollItem);
                break;
            case R.id.btnUp3:
                litsProducts.smoothScrollBy(0, -6 * Config.scrollItem);
                break;
            case R.id.btnDown1:
                litsProducts.smoothScrollBy(0, 2 * Config.scrollItem);
                break;
            case R.id.btnDown2:
                litsProducts.smoothScrollBy(0, 4 * Config.scrollItem);
                break;
            case R.id.btnDown3:
                litsProducts.smoothScrollBy(0, 6 * Config.scrollItem);
                break;
        }
    }

    public int getUnitProductId(ArrayList<UnitModel> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getMain_unit() == 1) {
                return arr.get(i).getUnit_product_id();
            }
        }
        return -1;
    }

    public boolean checkDateTime(String timePush) {
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        Date d1 = null;
        try {
            d1 = df1.parse(timePush.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date d2 = null;
        String timeDelivery = DateTimeFormat.formatDateTime1(tvExpectedDate.getText().toString()) + " " +
                DateTimeFormat.formatTime5(tvExpectedTime.getText().toString());
        try {
            d2 = df1.parse(timeDelivery.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d1 != null && d2 != null) {
            if (d1.compareTo(d2) < 0) {
                Toast.makeText(getActivity(), getContext().getResources().getString(R.string.time_fail), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public int getDeliveryBoyId() {
        for (int i = 0; i < deleveryBoyOrderAdapter.getList().size(); i++) {
            if (deleveryBoyOrderAdapter.getList().get(i).getDelivery_boy_name().equals(spnSelectDeliveryBoy.getSelectedItem().toString())) {
                return deleveryBoyOrderAdapter.getList().get(i).getId();
            }
        }
        return 0;
    }

    public ArrayList<Integer> coverTimeInPut(int minInput) {
        ArrayList<Integer> arr = new ArrayList<>();
        int day = 0;
        int hour = 0;
        int min = 0;
        day = minInput / (24 * 60);
        hour = (minInput - (day * 24 * 60)) / 60;
        min = (minInput - (day * 24 * 60)) % 60;
        arr.add(day);
        arr.add(hour);
        arr.add(min);
        return arr;
    }

    class UpdateTimeRemaingAsyncTask extends AsyncTask<Integer, ArrayList<Integer>, ArrayList<String>> {
        private int minInput;

        public UpdateTimeRemaingAsyncTask(int min) {
            this.minInput = min;
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            ArrayList<Integer> arrInput = new ArrayList<>();
            arrInput = coverTimeInPut(minInput);
            int day = arrInput.get(0);
            int hour = arrInput.get(1);
            int min = arrInput.get(2);
            int sec = 59;
            while (sec >= 0) {
                sec--;
                if (sec == 0) {
                    min--;
                    if (day > 0 && hour > 0 && min > 0) {
                        if (min == 0) {
                            hour--;
                            if (hour == 0) {
                                day--;
                                hour = 24;
                            }
                            min = 60;
                        }
                        sec = 59;
                    }
                    if (day == 0 && hour == 0 && min == 0) {
                        min = -1;
                        sec = 59;
                    } else {
                        if (min == -60) {
                            hour--;
                            if (hour == -24) {
                                day--;
                                hour = 0;
                            }
                            min = 0;
                        }
                        sec = 59;
                    }
                }
                if (day <= 0 && hour <= 0) {
                    if (min < 5) {
                        isMin5 = true;
                    } else {
                        isMin5 = false;
                    }
                }
                ArrayList<Integer> time = new ArrayList<>();
                time.add(day);
                time.add(hour);
                time.add(min);
                time.add(sec);
                SystemClock.sleep(1000);
                publishProgress(time);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ArrayList<Integer>... values) {
            super.onProgressUpdate(values);
            tvDay.setText(values[0].get(0) + "");
            tvHour.setText(values[0].get(1) + "");
            tvMin.setText(values[0].get(2) + "");
            tvSec.setText(values[0].get(3) + "");
            if (values[0].get(0) == 0) {
                layoutDay.setVisibility(View.GONE);
                pointDay.setVisibility(View.GONE);
                if (values[0].get(1) == 0) {
                    layoutHour.setVisibility(View.GONE);
                    pointHour.setVisibility(View.GONE);
                } else {
                    layoutHour.setVisibility(View.VISIBLE);
                    pointHour.setVisibility(View.VISIBLE);
                }
            } else {
                layoutDay.setVisibility(View.VISIBLE);
                pointDay.setVisibility(View.VISIBLE);
            }
            if (isMin5) {
                layoutTime.setBackgroundResource(R.drawable.bg_canceled_orders);
            }
        }
    }

    private void accpetOrder(final Context context, UpdateNormalOrderRequets updateNormalOrderRequets) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateNormalOrder(updateNormalOrderRequets);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            Toast.makeText(context, context.getResources().getString(R.string.Accept_successfully),
                                    Toast.LENGTH_SHORT).show();
                            Config.isUpdateOrder = true;
                            FragmentScheduleOrders.isReLoadSchedule = true;
                            mainViewOrders.finish();
                        }
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.Accpet_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void sendOrderConfirm(final Context context, SendOrderConfirmRequest sendOrderConfirmRequest,
                                  final FragmentViewBulkSchduleOrder fragmentViewBulkSchduleOrder) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.sendOrderConfirm(sendOrderConfirmRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            btnSendConfim.setEnabled(false);
                            btnSendConfim.setText(getResources().getString(R.string.waiting_confim));
                            btnSendConfim.setTextColor(getResources().getColor(R.color.colorWhite));
                            btnSendConfim.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bulk_orders));
                            tvAcceptBulk.setVisibility(View.VISIBLE);
                            tvAcceptBulk.setEnabled(false);
                            tvAcceptBulk.setTextColor(getResources().getColor(R.color.text_save));
                            tvAcceptBulk.setBackgroundResource(R.drawable.render_gray);
                            viewProductsOrderAdapter = new ViewProductsOrderAdapter(context, orderDetails.getProducts(),
                                    fragmentViewBulkSchduleOrder, orderDetails.getOrder_info().getOrder_type(), 1);
                            litsProducts.setAdapter(viewProductsOrderAdapter);
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

    private void sendRepleacement(final Context context, SendReplacementRequest sendReplacementRequest) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.sendReplacementAlternavite(sendReplacementRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            if (Config.statusTypeOrder == 0) {
                                Config.isUpdateOrder = true;
                            } else if (Config.statusTypeOrder == 2) {
                                Config.isUpdateOrder = true;
                                FragmentScheduleOrders.isReLoadSchedule = true;
                            }
                            mainViewOrders.finish();
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
