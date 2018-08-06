package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;

import com.grocery.R;
import com.grocery.adapter.ProductSendNotificationAdapter;
import com.grocery.adapter.UserAdapter;
import com.grocery.common.CRecyclerView;
import com.grocery.common.CustomTextView;
import com.grocery.model.ProductInfor;
import com.grocery.model.UserModel;

import java.util.ArrayList;

/**
 * Created by ducth on 10/24/2017.
 */

public class DialogSendNotifiPurchase extends Dialog implements View.OnClickListener {
    private CustomTextView tvCancel;
    private CustomTextView tvSend;
    private CRecyclerView rcvProduct;
    private ProductSendNotificationAdapter productSendNotificationAdapter;
    private Context mContext;
    private ArrayList<ProductInfor> mListProduct;

    public DialogSendNotifiPurchase(@NonNull Context context, ArrayList<ProductInfor> listProduct,
                                    @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        mListProduct = listProduct;
        setContentView(R.layout.dialog_send_notifi_purchase);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        tvCancel.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    private void initData() {
        productSendNotificationAdapter = new ProductSendNotificationAdapter(mContext, mListProduct);
        rcvProduct.setAdapter(productSendNotificationAdapter);
    }

    private void initView() {
        tvCancel = findViewById(R.id.tvCancel);
        tvSend = findViewById(R.id.tvSend);
        rcvProduct = findViewById(R.id.lv_product);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                cancel();
                break;
            case R.id.tvSend:
                cancel();
                break;
        }
    }
}
