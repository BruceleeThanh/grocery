package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.DateTimeFormat;

import java.text.ParseException;

/**
 * Created by ducth on 10/31/2017.
 */

public class DialogPingShop extends Dialog {
    private ImageView imClose;
    private CustomTextView tvFlatNo;
    private CustomTextView tvOrderNo;
    private CustomTextView tvDeliveryTime;
    public static String flatNo = "";
    public static int orderNo = 0;
    public static String deliveryTime = "";

    public DialogPingShop(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_ping_shop);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        imClose = (ImageView) findViewById(R.id.imClose);
        tvFlatNo = (CustomTextView) findViewById(R.id.tvFlatNo);
        tvOrderNo = (CustomTextView) findViewById(R.id.tvOrderNo);
        tvDeliveryTime = (CustomTextView) findViewById(R.id.tvDelivery);
    }

    private void initData() {
        tvFlatNo.setSelected(true);
        tvOrderNo.setSelected(true);
        tvDeliveryTime.setSelected(true);
        tvFlatNo.setText(flatNo + "");
        tvOrderNo.setText(orderNo + "");
        try {
            String dateTime = DateTimeFormat.coverTimeGMTtoLocal(deliveryTime + "") + "";
            tvDeliveryTime.setText(DateTimeFormat.formatTime1(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
}
