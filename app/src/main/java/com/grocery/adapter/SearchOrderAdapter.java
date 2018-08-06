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
 * Created by ducth on 10/19/2017.
 */

public class SearchOrderAdapter extends BaseRecyclerAdapter<ItemOrders, SearchOrderAdapter.ViewHolder> {
    private Context mContext;

    public SearchOrderAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(SearchOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public SearchOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_search_order, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvId;
        private CustomTextviewBold tvFlat;
        private CustomTextView tvBuildingName;
        private CustomTextviewBold tvStatus;
        private CustomTextView tvOrderTime;
        private CustomTextView tvRemaingTime;
        private CustomTextviewBold tvDeliveryBoy;
        private CustomTextView tvCustomName;
        private CustomTextviewBold tvAed;
        private LinearLayout layoutMain;
        private LinearLayout btnView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (CustomTextView) itemView.findViewById(R.id.tvId);
            tvFlat = (CustomTextviewBold) itemView.findViewById(R.id.tvFlat);
            tvBuildingName = (CustomTextView) itemView.findViewById(R.id.tvBuildingName);
            tvStatus = (CustomTextviewBold) itemView.findViewById(R.id.tvStatus);
            tvOrderTime = (CustomTextView) itemView.findViewById(R.id.tvOrderTime);
            tvRemaingTime = (CustomTextView) itemView.findViewById(R.id.tvRemaingTime);
            tvDeliveryBoy = (CustomTextviewBold) itemView.findViewById(R.id.tvDeliveryBoy);
            tvCustomName = (CustomTextView) itemView.findViewById(R.id.tvCustomName);
            tvAed = (CustomTextviewBold) itemView.findViewById(R.id.tvAed);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            btnView = itemView.findViewById(R.id.btn_view_item_new_order);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            tvId.setSelected(true);
            tvFlat.setSelected(true);
            tvBuildingName.setSelected(true);
            tvStatus.setSelected(true);
            tvOrderTime.setSelected(true);
            tvRemaingTime.setSelected(true);
            tvDeliveryBoy.setSelected(true);
            tvCustomName.setSelected(true);
            tvAed.setSelected(true);

            if (itemOrders.getOrder_type() == 4) {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.bg_bulk_order));
            } else {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            }
            tvId.setText(itemOrders.getId() + "");
            tvFlat.setText(itemOrders.getFlat_no() + "");
            tvBuildingName.setText(itemOrders.getBuilding_name() + "");
            if (itemOrders.getCustomer_name() == null) {
                tvCustomName.setText("");
            } else {
                tvCustomName.setText(itemOrders.getCustomer_name().toString());
            }
            tvAed.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money());
            try {
                String dateTime = DateTimeFormat.coverTimeGMTtoLocal(itemOrders.getDelivery_time()) + "";
                tvOrderTime.setText(DateTimeFormat.formatTime1(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                String dateTime = DateTimeFormat.coverTimeGMTtoLocal(itemOrders.getDeliver_before()) + "";
                tvDeliveryBoy.setText(DateTimeFormat.formatTime1(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (itemOrders.getRemaining_time() < (5 * 60)) {
                tvRemaingTime.setBackgroundResource(R.drawable.bg_canceled_orders);
            } else {
                tvRemaingTime.setBackgroundResource(R.drawable.bg_despatched_orders);
            }
            if (itemOrders.getRemaining_time() != 0) {
                tvRemaingTime.setText(com.grocery.utils.Utils.getHoursAndMinutes(itemOrders.getRemaining_time()));
            }

            switch (itemOrders.getStatus()) {
                case 0:
                    tvStatus.setText(mContext.getResources().getText(R.string.pending));
                    tvStatus.setTextColor(mContext.getResources().getColor(R.color.canceled_orders));
                    break;
                case 1:
                    tvStatus.setText(mContext.getResources().getText(R.string.preparing));
                    tvStatus.setTextColor(mContext.getResources().getColor(R.color.bg_search_main));
                    break;
                case 2:
                    tvStatus.setText(mContext.getResources().getText(R.string.despatched));
                    tvStatus.setTextColor(mContext.getResources().getColor(R.color.bg_search_main));
                    break;
                case 3:
                    tvStatus.setText(mContext.getResources().getText(R.string.delivered1));
                    tvStatus.setTextColor(mContext.getResources().getColor(R.color.bg_search_main));
                    break;
                case 4:
                    tvStatus.setText(mContext.getResources().getText(R.string.rejected));
                    tvStatus.setTextColor(mContext.getResources().getColor(R.color.canceled_orders));
                    break;
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
