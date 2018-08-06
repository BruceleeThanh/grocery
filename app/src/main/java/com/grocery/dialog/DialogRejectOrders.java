package com.grocery.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainViewOrders;
import com.grocery.adapter.ReasonRejectAdpater;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentOrders.FragmentBulkOrders;
import com.grocery.fragment.fragmentOrders.FragmentScheduleOrders;
import com.grocery.model.ItemOrderInfor;
import com.grocery.model.ItemReasonReject;
import com.grocery.request.UpdateNormalOrderRequets;
import com.grocery.response.BaseResponse;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 03/10/2017.
 */

public class DialogRejectOrders extends Dialog implements View.OnClickListener {
    private static final int REJECT_ORERD = 2;
    private CustomTextView tvCancel;
    private CustomTextView tvSubmit;
    private CRecyclerView listReason;
    private ReasonRejectAdpater reasonRejectAdpater;
    private ArrayList<ItemReasonReject> arrSeason;
    private LinearLayout layoutRdSeason;
    private LinearLayout rdSeason;
    private CustomTextView tvSeason;
    private CustomEditText edtSomeSeason;
    private CustomTextView tvTitle;
    private ItemOrderInfor itemOrderInfor;

    public ItemOrderInfor getItemOrderInfor() {
        return itemOrderInfor;
    }

    public void setItemOrderInfor(ItemOrderInfor itemOrderInfor) {
        this.itemOrderInfor = itemOrderInfor;
    }

    public DialogRejectOrders(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_reject_orders);
        if (context instanceof MainViewOrders) {
            setOwnerActivity((MainViewOrders) context);
        }
        initView();
        initData();
        initListener();
    }

    private void initData() {
        arrSeason = new ArrayList<>();
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.cant_deliver_time), 1));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.no_delivery_boy), 2));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.no_item), 3));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.products_expired), 4));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.products_damaged), 5));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.notes_conditions), 6));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.incorrect), 7));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.customer_not_opening), 8));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.duplicate_order), 9));
        arrSeason.add(new ItemReasonReject(getContext().getResources().getString(R.string.late_delivery), 10));
        reasonRejectAdpater.addAll(arrSeason);
    }

    private void initView() {
        edtSomeSeason = (CustomEditText) findViewById(R.id.edtSomeSeason);
        rdSeason = (LinearLayout) findViewById(R.id.radioBtn);
        tvSeason = (CustomTextView) findViewById(R.id.tvSeason);
        layoutRdSeason = (LinearLayout) findViewById(R.id.layoutRdSeason);
        tvCancel = (CustomTextView) findViewById(R.id.tvCancel);
        tvSubmit = (CustomTextView) findViewById(R.id.tvSubmit);
        listReason = (CRecyclerView) findViewById(R.id.listReason);
        tvTitle = (CustomTextView) findViewById(R.id.tvTitle);
        Spannable wordtoSpan = new SpannableString(tvTitle.getText().toString());
        wordtoSpan.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.canceled_orders)), 30, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTitle.setText(wordtoSpan);

        reasonRejectAdpater = new ReasonRejectAdpater(getContext(), new ArrayList<ItemReasonReject>(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        listReason.setLayoutManager(gridLayoutManager);
        listReason.setAdapter(reasonRejectAdpater);
    }

    private void initListener() {
        tvCancel.setOnClickListener(this);
        layoutRdSeason.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    public void setStatusRdReason() {
        rdSeason.setSelected(false);
        tvSeason.setTextColor(getContext().getResources().getColor(R.color.text_spiner));
        edtSomeSeason.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                cancel();
                break;
            case R.id.tvSubmit:
                String reason = "";
                if (getRejectId() == 0) {
                    Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.choose_season), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getRejectId() == 11) {
                    if (edtSomeSeason.getText().toString().isEmpty()) {
                        Toast.makeText(getOwnerActivity(), getContext().getResources().getString(R.string.enter_season), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    reason = edtSomeSeason.getText().toString();
                }
                UpdateNormalOrderRequets updateNormalOrderRequets = new UpdateNormalOrderRequets(Config.AdminId,
                        Config.apiToken, getItemOrderInfor().getId(), REJECT_ORERD, 0,
                        "", getRejectId(), reason, Config.is_groceryAdmin);
                rejectOrder(getOwnerActivity(), updateNormalOrderRequets);
                break;
            case R.id.layoutRdSeason:
                reasonRejectAdpater.cleanItemChoose();
                tvSeason.setTextColor(getContext().getResources().getColor(R.color.despatched_orders));
                rdSeason.setSelected(true);
                edtSomeSeason.setEnabled(true);
                break;
        }
    }

    public int getRejectId() {
        if (rdSeason.isSelected()) {
            return 11;
        }
        for (int i = 0; i < reasonRejectAdpater.getList().size(); i++) {
            if (reasonRejectAdpater.getList().get(i).isChoose()) {
                return reasonRejectAdpater.getList().get(i).getId();
            }
        }
        return 0;
    }

    private void rejectOrder(final Context context, UpdateNormalOrderRequets updateNormalOrderRequets) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting.....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateNormalOrder(updateNormalOrderRequets);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            Toast.makeText(context, context.getResources().getString(R.string.reject_successfully),
                                    Toast.LENGTH_SHORT).show();
                            Config.isUpdateOrder = true;
                            if (itemOrderInfor.getOrder_type() == 3) {
                                Config.showScheduleOrder = true;
                                FragmentScheduleOrders.isReLoadSchedule = true;
                            } else if (itemOrderInfor.getOrder_type() == 4) {
                                Config.showBulkOrder = true;
                                FragmentBulkOrders.isReLoadBulk = true;
                            }
                            getOwnerActivity().finish();
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.reject_fail),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
