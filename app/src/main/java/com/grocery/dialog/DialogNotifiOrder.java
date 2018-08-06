package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.activity.MainViewOrders;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.model.ItemOrderInfor;
import com.grocery.model.ItemOrders;

/**
 * Created by ducth on 10/30/2017.
 */

public class DialogNotifiOrder extends Dialog {
    private CustomTextviewBold tvOrderNo;
    private ImageView imClose;
    private CustomTextView tvViewOrder;
    public static int orderId = 0;
    public static int orderType = 0;

    public DialogNotifiOrder(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_notifi_order);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tvOrderNo = (CustomTextviewBold) findViewById(R.id.tvOrderNo);
        imClose = (ImageView) findViewById(R.id.imClose);
        tvViewOrder = (CustomTextView) findViewById(R.id.tvViewOrder);
    }

    private void initData() {
        tvOrderNo.setSelected(true);
        tvOrderNo.setText(orderId + "");
    }

    private void initListener() {
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        tvViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (orderType){
                    case 4:
                        Config.ItemOrdersView = new ItemOrders(orderId,3);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra(Config.KEY_VIEW_ORDER, Config.ItemOrdersView);
                        getContext().startActivity(intent);
                        break;
                }
                cancel();
            }
        });
    }


}
