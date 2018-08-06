package com.grocery.fragment.fragmentViewOrders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
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
import com.grocery.fragment.fragmentOrders.FragmentDeliveredOrders;
import com.grocery.fragment.fragmentOrders.FragmentDespatchedOrders;
import com.grocery.model.ItemOrders;
import com.grocery.model.OrderDetails;
import com.grocery.utils.LoadImageUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ThanhBeo on 10/10/2017.
 */

public class FragmentViewDespatchDeliveredOrder extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private MainViewOrders mainViewOrders;
    private OrderDetails orderDetails;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private CustomTextView tvOrderId;
    private CustomTextView tvNumberItem;
    private CustomTextView tvRedeemendAmt;
    private CustomTextView tvDiscount;
    private CustomTextView tvAmount;
    private CustomTextView tvTotalMoney;
    private CustomTextView tvViewMap;
    private CustomTextView tvTimeDelivery;
    private CustomTextView tvDeliveryBoyName;
    private CustomTextviewBold tvOrderName;
    private CRecyclerView litsProducts;
    private ViewProductsOrderAdapter viewProductsOrderAdapter;
    private CustomTextView tvPaymentType;
    private CustomTextView tvPrev;
    private CustomTextView tvNext;
    private ImageView btnDown1;
    private ImageView btnDown2;
    private ImageView btnDown3;
    private ImageView btnUp1;
    private ImageView btnUp2;
    private ImageView btnUp3;
    public static OnClickListener onClickListener1;

    public interface OnClickListener {
        void onItemClicked2();
    }

    public static void setOnItemClickListener(OnClickListener listener) {
        onClickListener1 = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_despatched_delivered_orders, container, false);
        mainViewOrders = (MainViewOrders) getActivity();
        mainViewOrders.dismissDialog();
        orderDetails = mainViewOrders.getOrderDetails();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        tvOrderId.setText(orderDetails.getOrder_info().getId() + "");
        // Total money
        float totalMoney = orderDetails.getOrder_info().getTotal_money() + orderDetails.getOrder_info().getTip_money() +
                orderDetails.getOrder_info().getService_fee();
        tvTotalMoney.setText(getResources().getString(R.string.aed1) + " " + totalMoney);
        // RedeemAmt
        float redeemenAmt = orderDetails.getOrder_info().getCash_back_money() + orderDetails.getOrder_info().getReward_money();
        if (orderDetails.getOrder_info().getIs_redeem() == 1) {
            tvRedeemendAmt.setText(getResources().getString(R.string.aed1) + " " + redeemenAmt);
        } else {
            tvRedeemendAmt.setText(getResources().getString(R.string.aed1) + " " + 0.0);
        }
        // Discount
        tvDiscount.setText(getResources().getString(R.string.aed1) + " " + getDiscount());

        float amount = totalMoney - redeemenAmt - getDiscount();
        tvAmount.setText(getResources().getString(R.string.aed1) + amount);

        if (Config.statusTypeOrder == 4) {
            tvOrderName.setText(getResources().getString(R.string.order_despatched));
            tvOrderName.setTextColor(getResources().getColor(R.color.bulk_orders));
            tvTimeDelivery.setTextColor(getResources().getColor(R.color.bulk_orders));
        } else if (Config.statusTypeOrder == 5) {
            tvOrderName.setText(getResources().getString(R.string.order_delivered));
            tvOrderName.setTextColor(getResources().getColor(R.color.bg_search_main));
            tvTimeDelivery.setTextColor(getResources().getColor(R.color.bg_search_main));
        }

        tvTimeDelivery.setText(DateTimeFormat.formatTime6(orderDetails.getOrder_info().getDelivery_time().toString()));

        tvDeliveryBoyName.setSelected(true);
        tvDeliveryBoyName.setText(getDeliveryBoyName());

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
        tvTotalMoney = (CustomTextView) view.findViewById(R.id.tvTotalAmount);
        tvTotalMoney.setSelected(true);
        tvViewMap = (CustomTextView) view.findViewById(R.id.viewMap);
        tvNumberItem = (CustomTextView) view.findViewById(R.id.tvNumberItem);
        tvNumberItem.setText(viewProductsOrderAdapter.getList().size() + "");
        tvRedeemendAmt = (CustomTextView) view.findViewById(R.id.redeemendAmt);
        tvRedeemendAmt.setSelected(true);
        tvDiscount = (CustomTextView) view.findViewById(R.id.discount);
        tvDiscount.setSelected(true);
        tvAmount = (CustomTextView) view.findViewById(R.id.Amount);
        tvAmount.setSelected(true);
        tvTimeDelivery = (CustomTextView) view.findViewById(R.id.tvTimeDelivery);
        tvDeliveryBoyName = (CustomTextView) view.findViewById(R.id.tvDeliveryBoyName);
        tvOrderName = (CustomTextviewBold) view.findViewById(R.id.tvOrderName);
        tvPaymentType = (CustomTextView) view.findViewById(R.id.tvPaymentType);
        tvPrev = (CustomTextView) view.findViewById(R.id.tvPrev);
        tvNext = (CustomTextView) view.findViewById(R.id.tvNext);
        /**/
        btnUp1 = (ImageView) view.findViewById(R.id.btnUp1);
        btnUp2 = (ImageView) view.findViewById(R.id.btnUp2);
        btnUp3 = (ImageView) view.findViewById(R.id.btnUp3);
        btnDown1 = (ImageView) view.findViewById(R.id.btnDown1);
        btnDown2 = (ImageView) view.findViewById(R.id.btnDown2);
        btnDown3 = (ImageView) view.findViewById(R.id.btnDown3);
    }

    private void initListener() {
        tvViewMap.setOnClickListener(this);
        tvPrev.setOnClickListener(this);
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
                ItemOrders itemOrders;
                if (Config.statusTypeOrder == 4) {
                    itemOrders = FragmentDespatchedOrders.despatchOrderAdapter.getList().get(positonOrder - 1);
                } else {
                    itemOrders = FragmentDeliveredOrders.deliveredOrderAdapter.getList().get(positonOrder - 1);
                }
                mainViewOrders.initData(itemOrders.getId(), itemOrders.getOrder_type());
                break;
            case R.id.tvNext:
                int positonOrder1 = checkPositonOrder(orderDetails.getOrder_info().getId());
                ItemOrders itemOrders1;
                if (Config.statusTypeOrder == 4) {
                    if (positonOrder1 == FragmentDespatchedOrders.despatchOrderAdapter.getList().size() - 1) {
                        onClickListener1.onItemClicked2();
                    }
                    try {
                        itemOrders1 = FragmentDespatchedOrders.despatchOrderAdapter.getList().get(positonOrder1 + 1);
                    } catch (Exception e) {
                        return;
                    }
                } else {
                    if (positonOrder1 == FragmentDeliveredOrders.deliveredOrderAdapter.getList().size() - 2) {
                        onClickListener1.onItemClicked2();
                    }
                    try {
                        itemOrders1 = FragmentDeliveredOrders.deliveredOrderAdapter.getList().get(positonOrder1 + 1);
                    } catch (Exception e) {
                        return;
                    }
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
        if (Config.statusTypeOrder == 4) {
            for (int i = 0; i < FragmentDespatchedOrders.despatchOrderAdapter.getList().size(); i++) {
                if (id == FragmentDespatchedOrders.despatchOrderAdapter.getList().get(i).getId()) {
                    return i;
                }
            }
            return -1;
        } else {
            for (int i = 0; i < FragmentDeliveredOrders.deliveredOrderAdapter.getList().size(); i++) {
                if (id == FragmentDeliveredOrders.deliveredOrderAdapter.getList().get(i).getId()) {
                    return i;
                }
            }
            return -1;
        }
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
}
