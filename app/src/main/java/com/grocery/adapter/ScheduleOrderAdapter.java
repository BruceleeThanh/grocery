package com.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.activity.MainViewOrders;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.common.DateTimeFormat;
import com.grocery.model.ItemOrders;

import java.util.List;

/**
 * Created by ThanhBeo on 07/10/2017.
 */

public class ScheduleOrderAdapter extends BaseRecyclerAdapter<ItemOrders, ScheduleOrderAdapter.ViewHolder> {

    private Context mContext;

    public ScheduleOrderAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(ScheduleOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ScheduleOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_schedule_order, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView ID;
        private CustomTextviewBold flat;
        private CustomTextView buildingName;
        private CustomTextView customName;
        private CustomTextviewBold Amount;
        private LinearLayout btnView;
        private CustomTextviewBold tvDate;
        private CustomTextviewBold tvTime;
        private CustomTextView tvReplaceConfirmed;
        private CustomTextView tvReplaceWaiting;

        public ViewHolder(View itemView) {
            super(itemView);
            ID = (CustomTextView) itemView.findViewById(R.id.tvOrder);
            flat = (CustomTextviewBold) itemView.findViewById(R.id.tvFlatNo);
            buildingName = (CustomTextView) itemView.findViewById(R.id.tvBuildingName);
            customName = (CustomTextView) itemView.findViewById(R.id.tvCustomName);
            Amount = (CustomTextviewBold) itemView.findViewById(R.id.tvAmount);
            btnView = (LinearLayout) itemView.findViewById(R.id.btnView);
            tvDate = (CustomTextviewBold) itemView.findViewById(R.id.tvDate);
            tvTime = (CustomTextviewBold) itemView.findViewById(R.id.tvTime);
            tvReplaceConfirmed = (CustomTextView) itemView.findViewById(R.id.tvReplaceConfirmed);
            tvReplaceWaiting = (CustomTextView) itemView.findViewById(R.id.tvReplaceWaiting);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            ID.setSelected(true);
            flat.setSelected(true);
            buildingName.setSelected(true);
            Amount.setSelected(true);
            customName.setSelected(true);
            tvTime.setSelected(true);
            tvDate.setSelected(true);

            ID.setText(itemOrders.getId() + "");
            flat.setText(itemOrders.getFlat_no() + "");
            buildingName.setText(itemOrders.getBuilding_name());
            Amount.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money() + "");
            if (itemOrders.getCustomer_name() == null) {
                customName.setText(mContext.getResources().getString(R.string.noName));
            } else {
                customName.setText(itemOrders.getCustomer_name().toString());
            }
            if (itemOrders.getStatus() == 8) {
                tvReplaceConfirmed.setVisibility(View.VISIBLE);
                tvReplaceWaiting.setVisibility(View.GONE);
            } else if (itemOrders.getStatus() == 7) {
                tvReplaceConfirmed.setVisibility(View.GONE);
                tvReplaceWaiting.setVisibility(View.VISIBLE);
            } else {
                tvReplaceConfirmed.setVisibility(View.GONE);
                tvReplaceWaiting.setVisibility(View.GONE);
            }
            tvTime.setText(DateTimeFormat.formatTime1(itemOrders.getSchedule_date()));
            tvDate.setText(DateTimeFormat.formatTime2(itemOrders.getSchedule_date()));
            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Config.ItemOrdersView = itemOrders;
                    Intent intent = new Intent(mContext, MainViewOrders.class);
                    intent.putExtra(Config.KEY_VIEW_ORDER, list.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
