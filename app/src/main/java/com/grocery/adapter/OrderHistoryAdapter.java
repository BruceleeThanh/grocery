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

import java.text.ParseException;
import java.util.List;

/**
 * Created by ducth on 10/23/2017.
 */

public class OrderHistoryAdapter extends BaseRecyclerAdapter<ItemOrders, OrderHistoryAdapter.ViewHolder> {
    public OrderHistoryAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvId;
        private CustomTextviewBold tvFlat;
        private CustomTextView tvBuilding;
        private CustomTextView tvDeleviryTime;
        private CustomTextviewBold tvAed;
        private CustomTextView tvStatus;
        private LinearLayout layoutMain;
        private LinearLayout btnView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (CustomTextView) itemView.findViewById(R.id.tvId);
            tvFlat = (CustomTextviewBold) itemView.findViewById(R.id.tvFlat);
            tvBuilding = (CustomTextView) itemView.findViewById(R.id.tvBuilding);
            tvDeleviryTime = (CustomTextView) itemView.findViewById(R.id.tvDeleviryTime);
            tvAed = (CustomTextviewBold) itemView.findViewById(R.id.tvAed);
            tvStatus = (CustomTextView) itemView.findViewById(R.id.tvStatus);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            btnView = itemView.findViewById(R.id.btnView);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            tvId.setSelected(true);
            tvFlat.setSelected(true);
            tvBuilding.setSelected(true);
            tvDeleviryTime.setSelected(true);
            tvAed.setSelected(true);
            tvStatus.setSelected(true);
            tvId.setText(itemOrders.getId() + "");
            tvFlat.setText(itemOrders.getFlat_no() + "");
            if (itemOrders.getBuilding_name()!=null){
                tvBuilding.setText(itemOrders.getBuilding_name() + "");
            }
            tvAed.setText(itemOrders.getTotal_money() + "");
            if (itemOrders.getStatus() == 5) {
                tvStatus.setText(mContext.getResources().getString(R.string.canceled));
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.canceled_orders));
            } else {
                tvStatus.setText(mContext.getResources().getString(R.string.delivered_by) + " " + itemOrders.getDelivery_boy_name());
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_spiner));
            }
            try {
                String dateTime = DateTimeFormat.coverTimeGMTtoLocal(itemOrders.getDelivery_time()) + "";
                tvDeleviryTime.setText(DateTimeFormat.formatTime1(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (itemOrders.getOrder_type() == 4) {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.bg_bulk_order));
            } else {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            }

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
