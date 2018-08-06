package com.grocery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.grocery.R;
import com.grocery.common.CustomTextView;

/**
 * Created by ThanhBeo on 14/09/2017.
 */

public class MainCloseShop extends AppCompatActivity implements View.OnClickListener {
    private CustomTextView tvOpenShop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.close_shop2);
        initView();
        initListener();
    }

    private void initListener() {
        tvOpenShop.setOnClickListener(this);
    }

    private void initView() {
        tvOpenShop = (CustomTextView) findViewById(R.id.btn_open_again_shop);
    }

    @Override
    public void onClick(View v) {
        finish();
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
    }
}
