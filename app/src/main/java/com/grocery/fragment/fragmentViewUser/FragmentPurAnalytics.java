package com.grocery.fragment.fragmentViewUser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grocery.R;
import com.grocery.activity.MainViewUser;
import com.grocery.adapter.OtherProductUserAdapter;
import com.grocery.adapter.OtherProductUserSelectedAdapter;
import com.grocery.adapter.ProductUserAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogSendNotifiPurchase;
import com.grocery.model.ProductInfor;
import com.grocery.model.UserInfor;
import com.grocery.model.UserModel;
import com.grocery.response.UserPurchaseReponse;
import com.grocery.utils.LoadImageUtils;
import com.grocery.utils.ProgerssBarUntil;
import com.grocery.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 20/07/2017.
 */

public class FragmentPurAnalytics extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private PieChart pieChart;
    private float[] yData = new float[7];
    private int[] xColos = new int[7];
    private ArrayList<Integer> colors = new ArrayList<>();
    private LinearLayout RdSelected;
    private LinearLayout RdOther;
    private CustomTextView tvSelectedPro;
    private CustomTextView tvOtherPro;
    private LinearLayout layoutRdOther;
    private LinearLayout layoutRdSelectd;
    private CRecyclerView listProduct;
    private ProductUserAdapter productUserAdapter;
    private OtherProductUserAdapter otherProductUserAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgerssBarUntil progerssBarUntil;
    private MainViewUser mainViewUser;
    private UserModel userModel;
    private RoundRectCornerImageView imAvata;
    private CustomTextView tvFlat;
    private CustomTextView tvBuiling;
    private MapFragment mMapFragment;
    private GoogleMap mMap;
    private CustomTextView tvViewMap;
    private UserInfor userInfor;
    private ImageView btnDown1;
    private ImageView btnDown2;
    private ImageView btnDown3;
    private ImageView btnUp1;
    private ImageView btnUp2;
    private ImageView btnUp3;
    private ArrayList<ProductInfor> mListProductSendNotification;

    private LinearLayout layoutOtherProduct;
    private CustomEditText edtSearchOtherProduct;
    private ProgressBar progressBarMain2;
    private CRecyclerView rcvOtherProduct;
    private ArrayList<ProductInfor> mListOtherProduct;
    // layout bottom
    private RelativeLayout layoutProductSelected;
    private RelativeLayout layoutProductSelected2;
    private CustomTextView btnSave;
    private CustomTextView btnSave2;
    private RelativeLayout layoutNumber;
    private RelativeLayout layoutNumber2;
    private CustomTextView tvSelect;
    private CustomTextView tvSelect2;
    private CRecyclerView rcvOtherProductSelected;
    public static ArrayList<ProductInfor> arrProductSelected = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_pur_analytics, container, false);
        progerssBarUntil = new ProgerssBarUntil(view);
        mainViewUser = (MainViewUser) getActivity();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        userModel = mainViewUser.getUserModel();
        loadMore();
    }

    private void loadMore() {
        getUserPurchase(getActivity(), Config.AdminId, Config.apiToken,
                userModel.getUser_id(), Config.is_groceryAdmin);
    }

    private void initListener() {
        layoutRdSelectd.setOnClickListener(this);
        layoutRdOther.setOnClickListener(this);
        tvViewMap.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnSave2.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                productUserAdapter.clear();
                ProductUserAdapter.arrAdd = new ArrayList<ProductInfor>();
                updateLayoutSelectes(new ArrayList<ProductInfor>());
                loadMore();
            }
        });
        btnDown1.setOnClickListener(this);
        btnDown2.setOnClickListener(this);
        btnDown3.setOnClickListener(this);
        btnUp1.setOnClickListener(this);
        btnUp2.setOnClickListener(this);
        btnUp3.setOnClickListener(this);

        edtSearchOtherProduct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchOtherProduct(edtSearchOtherProduct.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private void initView(View view) {
        mListProductSendNotification = new ArrayList<>();
        mListOtherProduct = new ArrayList<>();
        layoutRdOther = (LinearLayout) view.findViewById(R.id.layout_rd_other);
        layoutRdSelectd = (LinearLayout) view.findViewById(R.id.layout_rd_selectd);
        RdSelected = (LinearLayout) view.findViewById(R.id.btn_radio_btn_view_user_selected);
        RdOther = (LinearLayout) view.findViewById(R.id.btn_radio_btn_view_user_other);
        tvSelectedPro = (CustomTextView) view.findViewById(R.id.tv_selected_products);
        tvOtherPro = (CustomTextView) view.findViewById(R.id.tv_other_products);
        RdSelected.setSelected(true);
        tvSelectedPro.setTextColor(getResources().getColor(R.color.color_Sat));

        listProduct = (CRecyclerView) view.findViewById(R.id.lv_product_purchase);
        productUserAdapter = new ProductUserAdapter(getActivity(), new ArrayList<ProductInfor>(), this);
        listProduct.setAdapter(productUserAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        imAvata = (RoundRectCornerImageView) view.findViewById(R.id.imAvata);
        tvFlat = (CustomTextView) view.findViewById(R.id.tvFlat);
        tvBuiling = (CustomTextView) view.findViewById(R.id.tvBuilding);

        mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragment_map);
        mMapFragment.getMapAsync(this);
        tvViewMap = (CustomTextView) view.findViewById(R.id.viewMap);

        btnDown1 = view.findViewById(R.id.btnDown1);
        btnDown2 = view.findViewById(R.id.btnDown2);
        btnDown3 = view.findViewById(R.id.btnDown3);
        btnUp1 = view.findViewById(R.id.btnUp1);
        btnUp2 = view.findViewById(R.id.btnUp2);
        btnUp3 = view.findViewById(R.id.btnUp3);

        layoutOtherProduct = view.findViewById(R.id.layout_other_product);
        edtSearchOtherProduct = view.findViewById(R.id.edt_search_other_product);
        progressBarMain2 = view.findViewById(R.id.progressBarMain2);
        rcvOtherProduct = view.findViewById(R.id.rcv_other_product);
        otherProductUserAdapter = new OtherProductUserAdapter(getActivity(), new ArrayList<ProductInfor>(), this);
        rcvOtherProduct.setAdapter(otherProductUserAdapter);
        // bottom
        layoutProductSelected = view.findViewById(R.id.layout_product_selected);
        layoutProductSelected2 = view.findViewById(R.id.layout_product_selected_2);
        btnSave = (CustomTextView) view.findViewById(R.id.btn_save_manager_category);
        btnSave2 = (CustomTextView) view.findViewById(R.id.btn_save_manager_category_2);
        layoutNumber = view.findViewById(R.id.layout_number);
        layoutNumber2 = view.findViewById(R.id.layout_number_2);
        rcvOtherProductSelected = view.findViewById(R.id.rcv_other_product_selected);
        tvSelect = (CustomTextView) view.findViewById(R.id.tvSelect);
        tvSelect2 = (CustomTextView) view.findViewById(R.id.tvSelect2);
    }

    public void initPie() {
        pieChart = (PieChart) getView().findViewById(R.id.pieChar_view_user);
        /*Ban kinh vong tron trung tam*/
        pieChart.setHoleRadius(10f);
        /*phan tram*/
        pieChart.setUsePercentValues(true);
        /*vien mo ben ngoai vong tron turng tam*/
        pieChart.setTransparentCircleRadius(0);

        /*Xoa cac chu thich*/
        Legend l = pieChart.getLegend();
        l.setEnabled(false);

        /*Xoa lable*/
        Description des = pieChart.getDescription();
        des.setEnabled(false);
        xColos[0] = getResources().getColor(R.color.color_Sat);
        xColos[1] = getResources().getColor(R.color.color_Tus);
        xColos[2] = getResources().getColor(R.color.color_Wed);
        xColos[3] = getResources().getColor(R.color.color_Thur);
        xColos[4] = getResources().getColor(R.color.color_Mon);
        xColos[5] = getResources().getColor(R.color.color_Sun);
        xColos[6] = getResources().getColor(R.color.color_Fri);

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            if (yData[i] != 0) {
                pieEntries.add(new PieEntry(yData[i]));
                colors.add(xColos[i]);
            }
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setValueTextColor(Color.WHITE);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_rd_selectd:
                RdSelected.setSelected(true);
                RdOther.setSelected(false);
                tvOtherPro.setTextColor(getResources().getColor(R.color.text_spiner));
                tvSelectedPro.setTextColor(getResources().getColor(R.color.color_Sat));
                layoutOtherProduct.setVisibility(View.GONE);
                layoutProductSelected.setVisibility(View.VISIBLE);
                layoutProductSelected2.setVisibility(View.GONE);
                break;
            case R.id.layout_rd_other:
                RdSelected.setSelected(false);
                RdOther.setSelected(true);
                tvOtherPro.setTextColor(getResources().getColor(R.color.color_Sat));
                tvSelectedPro.setTextColor(getResources().getColor(R.color.text_spiner));
                layoutOtherProduct.setVisibility(View.VISIBLE);
                layoutProductSelected.setVisibility(View.GONE);
                layoutProductSelected2.setVisibility(View.VISIBLE);
                break;
            case R.id.viewMap:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(
                        "geo:" + userInfor.getLatitude() +
                                "," + userInfor.getLongitude() +
                                "?q=" + userInfor.getLatitude() +
                                "," + userInfor.getLongitude() +
                                "(" + userInfor.getBuilding_name() + ")"));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                break;
            case R.id.btn_save_manager_category:
                DialogSendNotifiPurchase dialogSendNotifiPurchase = new DialogSendNotifiPurchase(getActivity(),
                        mListProductSendNotification, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogSendNotifiPurchase.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogSendNotifiPurchase.show();
                break;
            case R.id.btn_save_manager_category_2:
                DialogSendNotifiPurchase dialogSendNotifiPurchase2 = new DialogSendNotifiPurchase(getActivity(),
                        mListOtherProduct, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                dialogSendNotifiPurchase2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogSendNotifiPurchase2.show();
                break;
            case R.id.btnDown1:
                listProduct.smoothScrollBy(0, Config.scrollItem);
                break;
            case R.id.btnDown2:
                listProduct.smoothScrollBy(0, 2 * Config.scrollItem);
                break;
            case R.id.btnDown3:
                listProduct.smoothScrollBy(0, 3 * Config.scrollItem);
                break;
            case R.id.btnUp1:
                listProduct.smoothScrollBy(0, -Config.scrollItem);
                break;
            case R.id.btnUp2:
                listProduct.smoothScrollBy(0, -2 * Config.scrollItem);
                break;
            case R.id.btnUp3:
                listProduct.smoothScrollBy(0, -3 * Config.scrollItem);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void drawMarker(GoogleMap googleMap, float lattitude, float longtitude, String photo) {
        LatLng latLng = new LatLng(lattitude, longtitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        View viewMarker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_maker, null);
        CircleImageView imMarker = (CircleImageView) viewMarker.findViewById(R.id.image_marker);
        if (photo == null) {
            Picasso.with(getActivity()).load(R.drawable.icon_users).into(imMarker);
        } else {
            LoadImageUtils loadImageUtils = new LoadImageUtils(getActivity(), photo, imMarker);
            loadImageUtils.loadImageWithPicasoUSer();
        }
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).
                icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), viewMarker))));
    }

    public void updateLayoutSelectes(ArrayList<ProductInfor> listProduct) {
        mListProductSendNotification = listProduct;
        if (listProduct.size() == 0) {
            btnSave.setEnabled(false);
            btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
            btnSave.setTextColor(getResources().getColor(R.color.text_save));
            layoutNumber.setVisibility(View.VISIBLE);
            tvSelect.setTextColor(getResources().getColor(R.color.text_save));
            tvSelect.setText(getString(R.string.selected));
        } else {
            btnSave.setEnabled(true);
            btnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            btnSave.setTextColor(Color.WHITE);
            layoutNumber.setVisibility(View.GONE);
            tvSelect.setTextColor(getResources().getColor(R.color.colorApp));

            String strProduct = "";
            for (int i = 0; i < listProduct.size(); i++) {
                if (i == (listProduct.size() - 1)) {
                    strProduct = strProduct + listProduct.get(i).getProduct_name();
                } else {
                    strProduct = strProduct + listProduct.get(i).getProduct_name() + ", ";
                }
            }
            tvSelect.setText(strProduct);
        }
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

    private void getUserPurchase(final Context context, int userID, String apiToken, int user_id, int is_groceryAdmin) {
        progerssBarUntil.setInvisibeLayoutMain();

        Controller controller = new Controller();
        Call<UserPurchaseReponse> call = controller.service.getUserPurchase(userID, apiToken, user_id, is_groceryAdmin);
        call.enqueue(new Callback<UserPurchaseReponse>() {
            @Override
            public void onResponse(Response<UserPurchaseReponse> response, Retrofit retrofit) {
                progerssBarUntil.setVisibeLayoutMain();
                if (response != null) {
                    UserPurchaseReponse userPurchaseReponse = response.body();
                    if (userPurchaseReponse != null) {
                        if (userPurchaseReponse.code == 200) {
                            productUserAdapter.addAll(userPurchaseReponse.result.product_infor);
                            if (userPurchaseReponse.result.user_infor.getPhoto() == null) {
                                Picasso.with(context).load(R.mipmap.icon_no_image).into(imAvata);
                            } else {
                                LoadImageUtils loadImageUtils = new LoadImageUtils(context,
                                        userPurchaseReponse.result.user_infor.getPhoto() + "", imAvata);
                                loadImageUtils.loadImageWithPicasoUSer2();
                            }
                            userInfor = userPurchaseReponse.result.user_infor;
                            tvFlat.setText(getActivity().getResources().getString(R.string.flat_no_1) + " " +
                                    userPurchaseReponse.result.user_infor.getFlat_no());
                            tvBuiling.setText(userPurchaseReponse.result.user_infor.getBuilding_name());
                            yData[0] = userPurchaseReponse.result.product_in_week.getSaturday();
                            yData[1] = userPurchaseReponse.result.product_in_week.getTuesday();
                            yData[2] = userPurchaseReponse.result.product_in_week.getWednesday();
                            yData[3] = userPurchaseReponse.result.product_in_week.getThusday();
                            yData[4] = userPurchaseReponse.result.product_in_week.getMonday();
                            yData[5] = userPurchaseReponse.result.product_in_week.getSunday();
                            yData[6] = userPurchaseReponse.result.product_in_week.getFriday();
                            initPie();
                            drawMarker(mMap, userInfor.getLatitude(), userInfor.getLongitude(), userInfor.getPhoto());
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


    private void searchOtherProduct(String keyword) {
        // Todo call API search other product
        Utils.hideSoftKeyboard(getActivity(), edtSearchOtherProduct);

        progressBarMain2.setVisibility(View.VISIBLE);
        Controller controller = new Controller();
        Call<UserPurchaseReponse> call = controller.service.getUserPurchase(Config.AdminId, Config.apiToken,
                userModel.getUser_id(), Config.is_groceryAdmin);
        call.enqueue(new Callback<UserPurchaseReponse>() {
            @Override
            public void onResponse(Response<UserPurchaseReponse> response, Retrofit retrofit) {
                progressBarMain2.setVisibility(View.GONE);
                if (response != null) {
                    UserPurchaseReponse userPurchaseReponse = response.body();
                    if (userPurchaseReponse != null) {
                        if (userPurchaseReponse.code == 200) {
                            otherProductUserAdapter.addAll(userPurchaseReponse.result.product_infor);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressBarMain2.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    public void updateLayoutOtherProductSelected(final ArrayList<ProductInfor> listProduct) {
        mListOtherProduct = listProduct;
        if (listProduct.size() == 0) {
            btnSave2.setEnabled(false);
            btnSave2.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save));
            btnSave2.setTextColor(getResources().getColor(R.color.text_save));
            layoutNumber2.setVisibility(View.VISIBLE);
            tvSelect2.setVisibility(View.VISIBLE);
            rcvOtherProductSelected.setVisibility(View.GONE);
        } else {
            btnSave2.setEnabled(true);
            btnSave2.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_open_again_shop));
            btnSave2.setTextColor(Color.WHITE);
            layoutNumber2.setVisibility(View.GONE);
            tvSelect2.setVisibility(View.GONE);
            rcvOtherProductSelected.setVisibility(View.VISIBLE);

            ArrayList<ProductInfor> listProductSelect = new ArrayList<>();
            if (listProduct != null && listProduct.size() > 0) {
                for (int i = 0; i < listProduct.size(); i++) {
                    listProductSelect.add(listProduct.get(i));
                }
            }
            OtherProductUserSelectedAdapter otherProductUserSelectedAdapter = new OtherProductUserSelectedAdapter(getActivity(),
                    new ArrayList<ProductInfor>(), new OtherProductUserSelectedAdapter.IListenerDeleteItem() {
                @Override
                public void onClickDeleteItem(int position) {
                    ProductInfor productInfor = listProduct.get(position);
                    if (arrProductSelected != null && arrProductSelected.size() > 0) {
                        for (int i = 0; i < arrProductSelected.size(); i++) {
                            if (productInfor.getProduct_id() == arrProductSelected.get(i).getProduct_id()) {
                                arrProductSelected.remove(i);
                                break;
                            }
                        }
                    }
                    otherProductUserAdapter.notifyDataSetChanged();
                }
            });
            rcvOtherProductSelected.setAdapter(otherProductUserSelectedAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            rcvOtherProductSelected.setLayoutManager(layoutManager);
            otherProductUserSelectedAdapter.addAll(listProductSelect);
        }
    }
}
