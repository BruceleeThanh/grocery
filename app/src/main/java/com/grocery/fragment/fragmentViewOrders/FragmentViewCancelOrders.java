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
import com.google.android.gms.maps.MapFragment;
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
import com.grocery.common.DateTimeFormat;
import com.grocery.fragment.fragmentOrders.FragmentCanceledOrders;
import com.grocery.model.ItemOrders;
import com.grocery.model.ItemReasonReject;
import com.grocery.model.OrderDetails;
import com.grocery.utils.LoadImageUtils;
import com.grocery.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ThanhBeo on 10/10/2017.
 */

public class FragmentViewCancelOrders extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private MainViewOrders mainViewOrders;
    private OrderDetails orderDetails;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private CustomTextView tvOrderId;
    private CustomTextView tvNumberItem;
    private CustomTextView tvTotalMoney;
    private CustomTextView tvViewMap;
    private CustomTextView tvTimeDelivery;
    private CRecyclerView litsProducts;
    private CustomTextView tvReason;
    private CustomTextView titleNumberOfItem;
    private ViewProductsOrderAdapter viewProductsOrderAdapter;
    ArrayList<ItemReasonReject> arrSeason;
    private CustomTextView tvPaymentType;

    private CustomTextView tvPrev;
    private CustomTextView tvNext;

    private ImageView btnDown1;
    private ImageView btnDown2;
    private ImageView btnDown3;
    private ImageView btnUp1;
    private ImageView btnUp2;
    private ImageView btnUp3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_cancel_orders, container, false);
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
        float totalMoney = orderDetails.getOrder_info().getTotal_money() + orderDetails.getOrder_info().getTip_money() +
                orderDetails.getOrder_info().getService_fee();
        tvTotalMoney.setText(getResources().getString(R.string.aed1) + " " + totalMoney);
        tvTimeDelivery.setText(DateTimeFormat.formatTime6(orderDetails.getOrder_info().getDelivery_time().toString()));
        /**/
        arrSeason = new ArrayList<>();
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.cant_deliver_time), 1));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.no_delivery_boy), 2));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.no_item), 3));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.products_expired), 4));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.products_damaged), 5));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.notes_conditions), 6));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.incorrect), 7));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.customer_not_opening), 8));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.duplicate_order), 9));
        arrSeason.add(new ItemReasonReject(getResources().getString(R.string.late_delivery), 10));
        if (getReason(orderDetails.getOrder_info().getReject_type()) != -1) {
            tvReason.setText(arrSeason.get(getReason(orderDetails.getOrder_info().getReject_type())).getReason());
        } else {
            if (!StringUtil.isEmpty(orderDetails.getOrder_info().getReject_comment()))
                tvReason.setText(orderDetails.getOrder_info().getReject_comment() + "");
        }
        tvPaymentType.setSelected(true);
        if (orderDetails.getOrder_info().getPayment_type() == 0) {
            tvPaymentType.setText(getResources().getString(R.string.COD));
        } else {
            tvPaymentType.setText(getResources().getString(R.string.Bring_card_reader));
        }
    }

    public int getReason(int reasonType) {
        for (int i = 0; i < arrSeason.size(); i++) {
            if (reasonType == arrSeason.get(i).getId()) {
                return i;
            }
        }
        return -1;
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
        tvTimeDelivery = (CustomTextView) view.findViewById(R.id.tvTimeDelivery);
        tvReason = (CustomTextView) view.findViewById(R.id.tvReason);
        tvPaymentType = (CustomTextView) view.findViewById(R.id.tvPaymentType);
        tvPrev = (CustomTextView) view.findViewById(R.id.tvPrev);
        tvNext = (CustomTextView) view.findViewById(R.id.tvNext);
        titleNumberOfItem = view.findViewById(R.id.titleNumberOfItem);
        if (viewProductsOrderAdapter.getList() != null && viewProductsOrderAdapter.getList().size() > 1) {
            titleNumberOfItem.setText(getString(R.string.number_of_items));
        } else {
            titleNumberOfItem.setText(getString(R.string.number_of_item));
        }
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
            case R.id.tvPrev:
                int positonOrder = checkPositonOrder(orderDetails.getOrder_info().getId());
                if (positonOrder == 0) {
                    return;
                }
                ItemOrders itemOrders;
                itemOrders = FragmentCanceledOrders.cancelOrdersAdapter.getList().get(positonOrder - 1);
                mainViewOrders.initData(itemOrders.getId(), itemOrders.getOrder_type());
                break;
            case R.id.tvNext:
                int positonOrder1 = checkPositonOrder(orderDetails.getOrder_info().getId());
                if (positonOrder1 == viewProductsOrderAdapter.getList().size() - 1) {
                    return;
                }
                ItemOrders itemOrders1;
                itemOrders1 = FragmentCanceledOrders.cancelOrdersAdapter.getList().get(positonOrder1 + 1);
                mainViewOrders.initData(itemOrders1.getId(), itemOrders1.getOrder_type());
                break;
        }
    }

    public int checkPositonOrder(int id) {
        for (int i = 0; i < FragmentCanceledOrders.cancelOrdersAdapter.getList().size(); i++) {
            if (id == FragmentCanceledOrders.cancelOrdersAdapter.getList().get(i).getId()) {
                return i;
            }
        }
        return -1;
    }
}
