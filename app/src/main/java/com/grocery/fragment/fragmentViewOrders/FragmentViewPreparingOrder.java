package com.grocery.fragment.fragmentViewOrders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grocery.R;
import com.grocery.activity.MainViewOrders;
import com.grocery.adapter.ViewProductsOrderAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.common.DateTimeFormat;
import com.grocery.fragment.fragmentOrders.FragmentPreparingOrders;
import com.grocery.model.ItemAlternavite;
import com.grocery.model.ItemOrders;
import com.grocery.model.ItemProductsOrders;
import com.grocery.model.OrderDetails;
import com.grocery.utils.LoadImageUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ThanhBeo on 10/10/2017.
 */

public class FragmentViewPreparingOrder extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private MainViewOrders mainViewOrders;
    private OrderDetails orderDetails;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private CustomTextView tvOrderId;
    private CustomTextView pointSec;
    private CustomTextView pointDay;
    private CustomTextView pointHour;
    private LinearLayout layoutDay;
    private LinearLayout layoutHour;
    private LinearLayout layoutSec;
    private CustomTextView tvMin;
    private CustomTextView tvSec;
    private CustomTextView tvHour;
    private CustomTextView tvDay;
    private CustomTextView tvNumberItem;
    private CustomTextView tvRedeemendAmt;
    private CustomTextView tvDiscount;
    private CustomTextView tvAmount;
    private CustomTextView tvTotalMoney;
    private CustomTextView tvViewMap;
    private CustomTextView tvOrderTime;
    private CustomTextView tvDateDelivery;
    private CustomTextviewBold tvTimeDelivery;
    private CustomTextView tvDeliveryBoyName;
    private CRecyclerView litsProducts;
    private ViewProductsOrderAdapter viewProductsOrderAdapter;
    private LinearLayout layoutTime;
    private boolean isMin5 = false;
    private ImageView imOrder;
    private CustomTextView tvPaymentType;
    private CustomTextView tvPerv;
    private CustomTextView tvNext;
    private ImageView btnDown1;
    private ImageView btnDown2;
    private ImageView btnDown3;
    private ImageView btnUp1;
    private ImageView btnUp2;
    private ImageView btnUp3;
    private UpdateTimeRemaingAsyncTask updateTimeRemaingAsyncTask;
    public static OnClickListener onClickListener;

    public interface OnClickListener {
        void onItemClicked2();
    }

    public static void setOnItemClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_preparing_orders, container, false);
        mainViewOrders = (MainViewOrders) getActivity();
        mainViewOrders.dismissDialog();
        orderDetails = mainViewOrders.getOrderDetails();
        initView(view);
        initData();
        initListener();
        return view;
    }

    public void initData() {
        if (updateTimeRemaingAsyncTask != null) {
            updateTimeRemaingAsyncTask.cancel(true);
        }
        updateTimeRemaingAsyncTask = new UpdateTimeRemaingAsyncTask(orderDetails.getOrder_info().getRemaining_time());
        if (Build.VERSION.SDK_INT >= 11) {
            updateTimeRemaingAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            updateTimeRemaingAsyncTask.execute();
        }
        tvOrderId.setText(orderDetails.getOrder_info().getId() + "");
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
        tvOrderTime.setText(DateTimeFormat.formatTime1(orderDetails.getOrder_info().getCreated_at()));
        String timeConver = DateTimeFormat.formatTimeDate(orderDetails.getOrder_info().getDelivery_time());
        String getdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        if (getdate.equals(timeConver)) {
            tvDateDelivery.setText(getResources().getString(R.string.today));
        } else {
            tvDateDelivery.setText(DateTimeFormat.formatTime2(orderDetails.getOrder_info().getDelivery_time()) + "");
        }
        tvTimeDelivery.setText(DateTimeFormat.formatTime1(orderDetails.getOrder_info().getDelivery_time()));
        tvDeliveryBoyName.setText(getDeliveryBoyName() + "");
        tvDeliveryBoyName.setSelected(true);

        if (orderDetails.getOrder_info().getOrder_type() == 4) {
            imOrder.setImageResource(R.mipmap.icon_bulk_full);
        } else if (orderDetails.getOrder_info().getOrder_type() == 3) {
            imOrder.setImageResource(R.mipmap.icon_schedule);
        } else {
            imOrder.setImageResource(0);
        }
        tvPaymentType.setSelected(true);
        if (orderDetails.getOrder_info().getPayment_type() == 0) {
            tvPaymentType.setText(getResources().getString(R.string.COD));
        } else {
            tvPaymentType.setText(getResources().getString(R.string.Bring_card_reader));
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
        litsProducts = (CRecyclerView) view.findViewById(R.id.listProducts);
        viewProductsOrderAdapter = new ViewProductsOrderAdapter(getActivity(), orderDetails.getProducts(), -1);
        litsProducts.setAdapter(viewProductsOrderAdapter);
        /*inti map*/
        mMapFragment = new SupportMapFragment();
        this.getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_map, mMapFragment).commit();
        mMapFragment.getMapAsync(this);
        /**/
        imOrder = (ImageView) view.findViewById(R.id.icon_orders);
        tvTotalMoney = (CustomTextView) view.findViewById(R.id.tvTotalAmount);
        tvTotalMoney.setSelected(true);
        tvViewMap = (CustomTextView) view.findViewById(R.id.viewMap);
        tvNumberItem = (CustomTextView) view.findViewById(R.id.tvNumberItem);
        tvNumberItem.setText(viewProductsOrderAdapter.getList().size() + "");
        layoutTime = (LinearLayout) view.findViewById(R.id.layoutTime);
        tvRedeemendAmt = (CustomTextView) view.findViewById(R.id.redeemendAmt);
        tvRedeemendAmt.setSelected(true);
        tvDiscount = (CustomTextView) view.findViewById(R.id.discount);
        tvDiscount.setSelected(true);
        tvAmount = (CustomTextView) view.findViewById(R.id.Amount);
        tvAmount.setSelected(true);
        tvOrderTime = (CustomTextView) view.findViewById(R.id.tvOrderTime);
        tvDateDelivery = (CustomTextView) view.findViewById(R.id.tvDateDelivery);
        tvTimeDelivery = (CustomTextviewBold) view.findViewById(R.id.tvTimeDelivery);
        tvDeliveryBoyName = (CustomTextView) view.findViewById(R.id.tvDeliveryBoyName);
        tvMin = (CustomTextView) view.findViewById(R.id.tvMin);
        tvSec = (CustomTextView) view.findViewById(R.id.tvSec);
        tvDay = (CustomTextView) view.findViewById(R.id.tvDay);
        tvHour = (CustomTextView) view.findViewById(R.id.tvHour);
        layoutSec = (LinearLayout) view.findViewById(R.id.layoutSec);
        layoutDay = (LinearLayout) view.findViewById(R.id.layoutDay);
        layoutHour = (LinearLayout) view.findViewById(R.id.layoutHour);
        pointDay = (CustomTextView) view.findViewById(R.id.pointDay);
        pointHour = (CustomTextView) view.findViewById(R.id.pointHour);
        pointSec = (CustomTextView) view.findViewById(R.id.pointSec);
        tvPaymentType = (CustomTextView) view.findViewById(R.id.tvPaymentType);
        tvPerv = (CustomTextView) view.findViewById(R.id.tvPrev);
        tvNext = (CustomTextView) view.findViewById(R.id.tvNext);
        /**/
        btnUp1 = view.findViewById(R.id.btnUp1);
        btnUp2 = view.findViewById(R.id.btnUp2);
        btnUp3 = view.findViewById(R.id.btnUp3);
        btnDown1 = view.findViewById(R.id.btnDown1);
        btnDown2 = view.findViewById(R.id.btnDown2);
        btnDown3 = view.findViewById(R.id.btnDown3);
    }

    private void initListener() {
        tvViewMap.setOnClickListener(this);
        tvPerv.setOnClickListener(this);
        tvNext.setOnClickListener(this);
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
        mMap.clear();
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
            case R.id.tvPrev:
                int positonOrder = checkPositonOrder(orderDetails.getOrder_info().getId());
                if (positonOrder == 0) {
                    return;
                }
                ItemOrders itemOrders = FragmentPreparingOrders.preparingOrderAdapter.getList().get(positonOrder - 1);
                mainViewOrders.initData(itemOrders.getId(), itemOrders.getOrder_type());
                break;
            case R.id.tvNext:
                int positonOrder1 = checkPositonOrder(orderDetails.getOrder_info().getId());
                ItemOrders itemOrders1;
                if (positonOrder1 == FragmentPreparingOrders.preparingOrderAdapter.getList().size() - 1) {
                    onClickListener.onItemClicked2();
                }
                try {
                    itemOrders1 = FragmentPreparingOrders.preparingOrderAdapter.getList().get(positonOrder1 + 1);
                } catch (Exception e) {
                    return;
                }
                mainViewOrders.initData(itemOrders1.getId(), itemOrders1.getOrder_type());
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

    public int checkPositonOrder(int id) {
        for (int i = 0; i < FragmentPreparingOrders.preparingOrderAdapter.getList().size(); i++) {
            if (id == FragmentPreparingOrders.preparingOrderAdapter.getList().get(i).getId()) {
                return i;
            }
        }
        return -1;
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

    public String getDeliveryBoyName() {
        try {
            for (int i = 0; i < orderDetails.getDelivery_boys().size(); i++) {
                if (orderDetails.getDelivery_boys().get(i).getId() == orderDetails.getOrder_info().getDelivery_boy_id()) {
                    return orderDetails.getDelivery_boys().get(i).getDelivery_boy_name();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
}
