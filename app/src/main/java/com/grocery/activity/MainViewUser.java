package com.grocery.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.grocery.R;
import com.grocery.adapter.UserAdapter;
import com.grocery.fragment.fragmentViewUser.FragmentOrdersHis;
import com.grocery.fragment.fragmentViewUser.FragmentPurAnalytics;
import com.grocery.model.UserModel;
import com.grocery.utils.FragmentUtils;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 05/07/2017.
 */

public class MainViewUser extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNameMain;
    private TextView tvOrder;
    private TextView tvPurchase;
    private ImageView imBack;
    private ArrayList<TextView> arrTv;
    public static TextView tvTime;
    private View layoutLineOrderHis;
    private View layoutLineUserPur;
    private FragmentOrdersHis fragmentOrdersHis;
    private FragmentPurAnalytics fragmentPurAnalytics;
    private FragmentUtils fragmentUtils;
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.instanceMain = this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_view_user);
        userModel = (UserModel) getIntent().getBundleExtra(UserAdapter.KEY_PUT_INTENT).getSerializable(UserAdapter.KEY_BUNDLE);
        initView();
        initListener();
    }

    private void initListener() {
        tvOrder.setOnClickListener(this);
        tvPurchase.setOnClickListener(this);
        imBack.setOnClickListener(this);
    }

    private void initView() {
        tvTime = (TextView) findViewById(R.id.tv_main_time_view_user);
        imBack = (ImageView) findViewById(R.id.imMenuViewUser);
        tvOrder = (TextView) findViewById(R.id.tv_view_user_orders);
        tvPurchase = (TextView) findViewById(R.id.tv_view_user_purchase);
        tvNameMain = (TextView) findViewById(R.id.tv_main_name_view_users);
        arrTv = new ArrayList<>();
        arrTv.add(tvOrder);
        arrTv.add(tvPurchase);
        setBackGroundTv(R.id.tv_view_user_orders);
        tvNameMain.setText(userModel.getUser_name() + "");
        fragmentUtils = new FragmentUtils(getSupportFragmentManager(), R.id.list_fragment_view_user);
        fragmentOrdersHis = new FragmentOrdersHis();
        fragmentPurAnalytics = new FragmentPurAnalytics();
        fragmentUtils.addToFragment(fragmentOrdersHis);
        layoutLineOrderHis = findViewById(R.id.layout_line_order_his);
        layoutLineUserPur = findViewById(R.id.layout_line_usre_pur);
        setLayoutLine(R.id.layout_line_order_his);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void setLayoutLine(int id) {
        switch (id) {
            case R.id.layout_line_usre_pur:
                layoutLineUserPur.setVisibility(View.INVISIBLE);
                layoutLineOrderHis.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_line_order_his:
                layoutLineUserPur.setVisibility(View.VISIBLE);
                layoutLineOrderHis.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_view_user_orders:
                fragmentUtils.changeFragment(fragmentOrdersHis);
                setBackGroundTv(R.id.tv_view_user_orders);
                setLayoutLine(R.id.layout_line_order_his);
                break;
            case R.id.tv_view_user_purchase:
                fragmentUtils.changeFragment(fragmentPurAnalytics);
                setBackGroundTv(R.id.tv_view_user_purchase);
                setLayoutLine(R.id.layout_line_usre_pur);
                break;
            case R.id.imMenuViewUser:
                onBackPressed();
                break;
        }
    }

    public void setBackGroundTv(int id) {
        for (int i = 0; i < arrTv.size(); i++) {
            arrTv.get(i).setTextColor(Color.WHITE);
            arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_list_frag));
            if (arrTv.get(i).getId() == id) {
                arrTv.get(i).setTextColor(getResources().getColor(R.color.colorApp));
                arrTv.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_item));
            }
        }
    }
}
