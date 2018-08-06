package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.dialog.DialogRejectOrders;
import com.grocery.model.ItemReasonReject;

import java.util.List;

/**
 * Created by ThanhBeo on 03/10/2017.
 */

public class ReasonRejectAdpater extends BaseRecyclerAdapter<ItemReasonReject, ReasonRejectAdpater.ViewHolder> {
    private DialogRejectOrders dialogRejectOrders;

    public ReasonRejectAdpater(Context context, List<ItemReasonReject> list, DialogRejectOrders dialogRejectOrders) {
        super(context, list);
        this.dialogRejectOrders = dialogRejectOrders;
    }

    @Override
    public void onBindViewHolder(ReasonRejectAdpater.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ReasonRejectAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_reason_reject, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rdadioBtn;
        private CustomTextView tvSeason;
        private LinearLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            rdadioBtn = (LinearLayout) itemView.findViewById(R.id.rdadioBtn);
            tvSeason = (CustomTextView) itemView.findViewById(R.id.reason);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
        }

        public void binData(ItemReasonReject itemReasonReject, final int position) {
            tvSeason.setText(itemReasonReject.getReason().toString());
            if (itemReasonReject.isChoose()) {
                rdadioBtn.setSelected(true);
                tvSeason.setTextColor(mContext.getResources().getColor(R.color.despatched_orders));
            } else {
                tvSeason.setTextColor(mContext.getResources().getColor(R.color.text_spiner));
                rdadioBtn.setSelected(false);
            }
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogRejectOrders.setStatusRdReason();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoose(false);
                    }
                    list.get(position).setChoose(true);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void cleanItemChoose() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChoose(false);
        }
        notifyDataSetChanged();
    }
}
