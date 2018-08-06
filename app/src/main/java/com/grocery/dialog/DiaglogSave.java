package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;

/**
 * Created by ThanhBeo on 03/07/2017.
 */

public class DiaglogSave extends Dialog implements View.OnClickListener {
    private CustomTextView tvNumber;
    private CustomTextView btnNo;
    private CustomTextView btnSave;
    public static String title = "";

    public DiaglogSave(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_update_manage_category);
        initView();
        initListener();
    }

    private void initListener() {
        btnSave.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    private void initView() {
        tvNumber = (CustomTextView) findViewById(R.id.tv_number_update_category);
        btnNo = (CustomTextView) findViewById(R.id.btn_no_update_category);
        btnSave = (CustomTextView) findViewById(R.id.btn_save_update_category);

        tvNumber.setText(Config.number_update + " " + title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_update_category:
                Config.checkUpdate = true;
                break;
            case R.id.btn_no_update_category:
                Config.checkUpdate = false;
                break;
        }
        cancel();
    }
}
