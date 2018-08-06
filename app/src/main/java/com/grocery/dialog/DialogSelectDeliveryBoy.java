package com.grocery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ItemDeliveryBoy;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

/**
 * Created by DangTin on 04/07/2018.
 */

public class DialogSelectDeliveryBoy extends Dialog implements View.OnClickListener {
    private CustomTextView tvContinue;
    private CustomTextView tvCancel;
    private ArrayList<ItemDeliveryBoy> mListDeliveryBoy;
    private IClickSelectDeliveryBoy iClickSelectDeliveryBoy;
    private FlowLayout flowlayoutDeliveryBoy;
    private int mDeliveryBoyId = -1;

    public interface IClickSelectDeliveryBoy {
        void onClickContinue(int deliveryBoyId);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public DialogSelectDeliveryBoy(@NonNull Context context, @StyleRes int themeResId,
                                   ArrayList<ItemDeliveryBoy> listDeliveryBoy,
                                   IClickSelectDeliveryBoy listener) {
        super(context, themeResId);
        this.mListDeliveryBoy = listDeliveryBoy;
        this.iClickSelectDeliveryBoy = listener;
        setContentView(R.layout.dialog_select_delivery_boy);
        initView();
        updateListDeliveryBoy(mDeliveryBoyId);
        initListener();
    }

    private void initListener() {
        tvContinue.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void updateListDeliveryBoy(int id) {
        flowlayoutDeliveryBoy.removeAllViews();
        if (mListDeliveryBoy != null && mListDeliveryBoy.size() > 0) {
            for (int i = 0; i < mListDeliveryBoy.size(); i++) {
                FlowLayout.LayoutParams params =
                        new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                                FlowLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 10, 0);
                TextView textView = new TextView(getContext());
                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(35, (int) getContext().getResources().getDimension(R.dimen.size10),
                        35, (int) getContext().getResources().getDimension(R.dimen.size10));
                textView.setTextSize(((int) getContext().getResources().getDimension(R.dimen.size_text_17) /
                        getContext().getResources().getDisplayMetrics().density));
                textView.setId(mListDeliveryBoy.get(i).getId());
                textView.setText(mListDeliveryBoy.get(i).getDelivery_boy_name());

                if (mListDeliveryBoy.get(i).getId() == id) {
                    textView.setBackgroundResource(R.drawable.btn_yes);
                    textView.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
                } else {
                    textView.setBackgroundResource(R.drawable.radius_edt);
                    textView.setTextColor(getContext().getResources().getColor(R.color.colorBlackTrans40));
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i1 = 0; i1 < mListDeliveryBoy.size(); i1++) {
                            if (v.getId() == mListDeliveryBoy.get(i1).getId()) {
                                mDeliveryBoyId = mListDeliveryBoy.get(i1).getId();
                                updateListDeliveryBoy(mDeliveryBoyId);
                                break;
                            }
                        }
                    }
                });
                flowlayoutDeliveryBoy.addView(textView);
            }
        }
    }

    private void initView() {
        tvContinue = findViewById(R.id.btn_continue);
        tvCancel = findViewById(R.id.btn_cancel);
        flowlayoutDeliveryBoy = findViewById(R.id.flowlayout_delivery_boy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:
                if (mDeliveryBoyId > 0) {
                    iClickSelectDeliveryBoy.onClickContinue(mDeliveryBoyId);
                    cancel();
                } else {
                    Toast.makeText(getContext(), getContext().getString(R.string.please_select_delivery_boy), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }
    }
}
