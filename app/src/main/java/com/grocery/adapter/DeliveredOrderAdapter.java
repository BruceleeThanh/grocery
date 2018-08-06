package com.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class DeliveredOrderAdapter extends BaseRecyclerAdapter<ItemOrders, DeliveredOrderAdapter.ViewHolder> {

    private Context mContext;

    public DeliveredOrderAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(DeliveredOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public DeliveredOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_delivered_order, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView id;
        private CustomTextviewBold flat;
        private CustomTextView buildingName;
        private CustomTextView customName;
        private CustomTextviewBold Amount;
        private ImageView imOrderType;
        private CustomTextView tvDeliveryBoy;
        private CustomTextView tvDeliveryTime;
        private LinearLayout layoutMain;
        private LinearLayout layoutView;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (CustomTextView) itemView.findViewById(R.id.tvOrder);
            flat = (CustomTextviewBold) itemView.findViewById(R.id.tvFlatNo);
            buildingName = (CustomTextView) itemView.findViewById(R.id.tvBuildingName);
            customName = (CustomTextView) itemView.findViewById(R.id.tvCustomName);
            Amount = (CustomTextviewBold) itemView.findViewById(R.id.tvAmount);
            imOrderType = (ImageView) itemView.findViewById(R.id.imOrderType);
            tvDeliveryBoy = (CustomTextView) itemView.findViewById(R.id.tvDeliveryBoy);
            tvDeliveryTime = (CustomTextView) itemView.findViewById(R.id.tvDeliveryTime);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            layoutView = (LinearLayout) itemView.findViewById(R.id.btnView);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            id.setSelected(true);
            flat.setSelected(true);
            buildingName.setSelected(true);
            Amount.setSelected(true);
            customName.setSelected(true);
            tvDeliveryBoy.setSelected(true);
            tvDeliveryTime.setSelected(true);

            id.setText(itemOrders.getId() + "");
            flat.setText(itemOrders.getFlat_no() + "");
            buildingName.setText(itemOrders.getBuilding_name() + "");
            Amount.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money() + "");
            if (itemOrders.getCustomer_name() == null) {
                customName.setText(mContext.getResources().getString(R.string.noName));
            } else {
                customName.setText(itemOrders.getCustomer_name());
            }
            if (itemOrders.getDelivery_boy_name() == null) {
                tvDeliveryBoy.setText(mContext.getResources().getString(R.string.noName));
            } else {
                tvDeliveryBoy.setText(itemOrders.getDelivery_boy_name());
            }
            tvDeliveryTime.setText(DateTimeFormat.formatTime1(itemOrders.getSchedule_date()));

            switch (itemOrders.getOrder_type()) {
                case 1:
                    imOrderType.setBackgroundResource(0);
                    break;
                case 2:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                    imOrderType.setBackgroundResource(R.mipmap.icon_cars);
                    break;
                case 3:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                    imOrderType.setBackgroundResource(R.mipmap.icon_schedule);
                    break;
                case 4:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.bg_bulk_order));
                    imOrderType.setBackgroundResource(R.mipmap.icon_bulk_full);
                    break;
                case 5:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                    imOrderType.setBackgroundResource(R.mipmap.icon_money);
                    break;
            }

            layoutView.setOnClickListener(new View.OnClickListener() {
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
