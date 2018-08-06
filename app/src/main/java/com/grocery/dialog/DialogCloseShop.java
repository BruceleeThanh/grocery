package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

import com.grocery.R;
import com.grocery.activity.MainCloseShop;
import com.grocery.common.CustomTextView;

/**
 * Created by ThanhBeo on 14/09/2017.
 */

public class DialogCloseShop extends Dialog implements View.OnClickListener {
    private CustomTextView tvYes;
    private CustomTextView tvNo;

    @Override
    protected void onStop() {
        super.onStop();
    }

    public DialogCloseShop(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.close_shop1);
        initView();
        binData();
        initListener();
    }

    private void initListener() {
        tvNo.setOnClickListener(this);
        tvYes.setOnClickListener(this);
    }

    private void binData() {

    }

    private void initView() {
        tvYes = (CustomTextView) findViewById(R.id.btn_Yes_close);
        tvNo = (CustomTextView) findViewById(R.id.btn_No_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Yes_close:
                Intent intent = new Intent(getContext(), MainCloseShop.class);
                getContext().startActivity(intent);
                break;
            case R.id.btn_No_close:
                break;
        }
        cancel();
    }
}
