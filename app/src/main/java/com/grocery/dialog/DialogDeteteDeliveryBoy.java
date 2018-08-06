package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

import com.grocery.R;
import com.grocery.common.CustomTextView;

/**
 * Created by DangTin on 04/07/2018.
 */

public class DialogDeteteDeliveryBoy extends Dialog implements View.OnClickListener {

    private CustomTextView tvDelete;
    private CustomTextView tvCancel;
    private IClickDeleteDeliveryBoy iClickDeleteDeliveryBoy;
    private int mDeliveryBoyId;

    public interface IClickDeleteDeliveryBoy {
        void onClickDeleteDeliveryBoy(int deliveryBoyId);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public DialogDeteteDeliveryBoy(@NonNull Context context, @StyleRes int themeResId, int deliveryBoyId,
                                   IClickDeleteDeliveryBoy listener) {
        super(context, themeResId);
        this.mDeliveryBoyId = deliveryBoyId;
        this.iClickDeleteDeliveryBoy = listener;
        setContentView(R.layout.dialog_confirm_delete_delivery_boy);
        initView();
        initListener();
    }

    private void initListener() {
        tvDelete.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initView() {
        tvDelete = findViewById(R.id.btn_delete);
        tvCancel = findViewById(R.id.btn_cancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                iClickDeleteDeliveryBoy.onClickDeleteDeliveryBoy(mDeliveryBoyId);
                cancel();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }
    }
}
