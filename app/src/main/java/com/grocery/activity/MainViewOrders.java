package com.grocery.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentViewOrders.FragmentViewBulkSchduleOrder;
import com.grocery.fragment.fragmentViewOrders.FragmentViewCancelOrders;
import com.grocery.fragment.fragmentViewOrders.FragmentViewDespatchDeliveredOrder;
import com.grocery.fragment.fragmentViewOrders.FragmentViewNormalOrder;
import com.grocery.fragment.fragmentViewOrders.FragmentViewPreparingOrder;
import com.grocery.model.AdminModel;
import com.grocery.model.ItemOrders;
import com.grocery.model.OrderDetails;
import com.grocery.response.OrderDetailResponse;
import com.grocery.utils.FragmentUtils;
import com.grocery.utils.LoadImageUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 02/10/2017.
 */

public class MainViewOrders extends AppCompatActivity implements View.OnClickListener {

    private FragmentUtils fragmentUtils;
    private LinearLayout layoutBack;
    public static CustomTextView tvTime;
    private OrderDetails orderDetails = new OrderDetails();
    private ItemOrders itemOrders;
    private CustomTextView tvCustomName;
    private CustomTextView tvFlatNo;
    private CircleImageView imAvata;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private AdminModel adminModel;

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.instanceMain = this;
    }

    public ItemOrders getItemOrders() {
        return itemOrders;
    }

    public void setItemOrders(ItemOrders itemOrders) {
        this.itemOrders = itemOrders;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_orders);
        itemOrders = (ItemOrders) getIntent().getExtras().getSerializable(Config.KEY_VIEW_ORDER);
        intiView();
        initData(itemOrders.getId(), itemOrders.getOrder_type());
        initListener();
    }

    public void initData(int itemOrdersId, int itemOrdersType) {
        sharedPreferences = getSharedPreferences(Config.Pref, MODE_PRIVATE);
        String profile = sharedPreferences.getString(Config.KEY_ADMIN_PROFILE, "");
        Gson gson = new Gson();
        adminModel = gson.fromJson(profile, AdminModel.class);
        Config.AdminId = adminModel.getId();
        Config.apiToken = adminModel.getApiToken();

        getOrdersDetail(Config.AdminId, Config.apiToken, itemOrdersId, itemOrdersType, Config.is_groceryAdmin);
    }

    private void initListener() {
        layoutBack.setOnClickListener(this);
    }

    private void intiView() {
        fragmentUtils = new FragmentUtils(getSupportFragmentManager(), R.id.layoutViewOrder);
        layoutBack = (LinearLayout) findViewById(R.id.layoutBack);
        tvTime = (CustomTextView) findViewById(R.id.tvTime);
        tvFlatNo = (CustomTextView) findViewById(R.id.tvFlatNo);
        tvCustomName = (CustomTextView) findViewById(R.id.tvCustomName);
        imAvata = (CircleImageView) findViewById(R.id.imAvata);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutBack:
                finish();
                break;
        }
    }

    public void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public void showFragment() {
        if (Config.statusTypeOrder == 0 || Config.statusTypeOrder == 2 || Config.statusTypeOrder == 3) {
            switch (itemOrders.getOrder_type()) {
                case 0:
                    fragmentUtils.addToFragment(new FragmentViewNormalOrder());
                    break;
                case 1:
                    fragmentUtils.addToFragment(new FragmentViewNormalOrder());
                    break;
                case 2:
                    fragmentUtils.addToFragment(new FragmentViewNormalOrder());
                    break;
                case 3:
                    fragmentUtils.addFragment(new FragmentViewBulkSchduleOrder());
                    break;
                case 4:
                    fragmentUtils.addFragment(new FragmentViewBulkSchduleOrder());
                    break;
            }
        } else if (Config.statusTypeOrder == 1) {
            fragmentUtils.addToFragment(new FragmentViewPreparingOrder());
        } else if (Config.statusTypeOrder == 4 || Config.statusTypeOrder == 5) {
            fragmentUtils.addToFragment(new FragmentViewDespatchDeliveredOrder());
        } else if (Config.statusTypeOrder == 6) {
            fragmentUtils.addToFragment(new FragmentViewCancelOrders());
        }
    }

    private void getOrdersDetail(int userID, String apiToken, int order_id, int order_type, int is_groceryAdmin) {
        showDialog();
        Controller controller = new Controller();
        Call<OrderDetailResponse> call = controller.service.getOrderDetail(userID, apiToken, order_id, order_type, is_groceryAdmin);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Response<OrderDetailResponse> response, Retrofit retrofit) {
                if (response != null) {
                    OrderDetailResponse orderDetailResponse = response.body();
                    if (orderDetailResponse.code == 200) {
                        orderDetails = orderDetailResponse.result;
                        if (orderDetails.getOrder_info().getUser_name() == null) {
                            tvCustomName.setText("No Name");
                        } else {
                            tvCustomName.setText(orderDetails.getOrder_info().getUser_name().toString());
                        }
                        if (orderDetails.getOrder_info().getUser_photo() == null) {
                            Picasso.with(MainViewOrders.this).load(R.mipmap.icon_users).into(imAvata);
                        } else {
                            LoadImageUtils loadImageUtils = new LoadImageUtils(MainViewOrders.this,
                                    orderDetails.getOrder_info().getUser_photo().toString(), imAvata);
                            loadImageUtils.loadImageWithPicasoUSer();
                        }
                        String flatNo = "";
                        String buildingName = "";
                        if (orderDetails.getOrder_info().getFlat_no() != null) {
                            flatNo = orderDetails.getOrder_info().getFlat_no().toString();
                        }
                        if (orderDetails.getOrder_info().getBuilding_name() != null) {
                            buildingName = orderDetails.getOrder_info().getBuilding_name().toString();
                        }
                        tvFlatNo.setText(flatNo + " , " + buildingName);
                    }
                }
                showFragment();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
