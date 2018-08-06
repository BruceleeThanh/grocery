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

import java.text.ParseException;
import java.util.List;

/**
 * Created by ThanhBeo on 20/06/2017.
 */

public class NewOrderAdapter extends BaseRecyclerAdapter<ItemOrders, NewOrderAdapter.ViewHolder> {

    private Context mContext;

    public NewOrderAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(NewOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ItemOrders getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_new_order, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView ID;
        private CustomTextviewBold flat;
        private CustomTextView buildingName;
        private CustomTextView timeOrder;
        private CustomTextView remainingTime;
        private CustomTextviewBold deliverBefore;
        private CustomTextView customName;
        private CustomTextviewBold Amount;
        private ImageView imRings;
        private ImageView imOrderType;
        private LinearLayout btnView;
        private LinearLayout layoutMain;
        private CustomTextView tvReplaceConfirmed;
        private CustomTextView tvReplaceWaiting;

        public ViewHolder(View itemView) {
            super(itemView);
            ID = (CustomTextView) itemView.findViewById(R.id.tv_item_new_order_id);
            flat = (CustomTextviewBold) itemView.findViewById(R.id.tv_item_new_order_flat);
            buildingName = (CustomTextView) itemView.findViewById(R.id.tv_item_new_order_building_name);
            timeOrder = (CustomTextView) itemView.findViewById(R.id.tv_item_new_order_time);
            remainingTime = (CustomTextView) itemView.findViewById(R.id.tv_item_new_order_remaining_time);
            deliverBefore = (CustomTextviewBold) itemView.findViewById(R.id.tv_item_new_order_deliver_before);
            customName = (CustomTextView) itemView.findViewById(R.id.tv_item_new_order_customr_name);
            Amount = (CustomTextviewBold) itemView.findViewById(R.id.tv_item_new_order_Amount);
            imRings = (ImageView) itemView.findViewById(R.id.icon_rings_new_orders);
            btnView = (LinearLayout) itemView.findViewById(R.id.btn_view_item_new_order);
            imOrderType = (ImageView) itemView.findViewById(R.id.imageView2);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            tvReplaceConfirmed = (CustomTextView) itemView.findViewById(R.id.tvReplaceConfirmed);
            tvReplaceWaiting = (CustomTextView) itemView.findViewById(R.id.tvReplaceWaiting);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            ID.setSelected(true);
            flat.setSelected(true);
            buildingName.setSelected(true);
            timeOrder.setSelected(true);
            customName.setSelected(true);
            Amount.setSelected(true);
            remainingTime.setSelected(true);
            deliverBefore.setSelected(true);

            ID.setText(itemOrders.getId() + "");
            if (itemOrders.getFlat_no() == null) {
                flat.setText("");
            } else {
                flat.setText(itemOrders.getFlat_no() + "");
            }
            if (itemOrders.getBuilding_name() == null) {
                buildingName.setText("");
            } else {
                buildingName.setText(itemOrders.getBuilding_name().toString());
            }
            if (itemOrders.getCustomer_name() == null) {
                customName.setText("");
            } else {
                customName.setText(itemOrders.getCustomer_name().toString());
            }
            Amount.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money() + "");

            try {
                String dateTime = DateTimeFormat.coverTimeGMTtoLocal(itemOrders.getCreated_at()) + "";
                timeOrder.setText(DateTimeFormat.formatTime1(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                String dateTime = DateTimeFormat.coverTimeGMTtoLocal(itemOrders.getDeliver_before()) + "";
                deliverBefore.setText(DateTimeFormat.formatTime1(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (itemOrders.getRemaining_time() < (5 * 60)) {
                remainingTime.setBackgroundResource(R.drawable.bg_canceled_orders);
            } else {
                remainingTime.setBackgroundResource(R.drawable.bg_despatched_orders);
            }
            if (itemOrders.getRemaining_time() != 0) {
                remainingTime.setText(com.grocery.utils.Utils.getHoursAndMinutes(itemOrders.getRemaining_time()));
            }

            /*if (itemOrders.getStatus() == 8) {
                tvReplaceConfirmed.setVisibility(View.VISIBLE);
                tvReplaceWaiting.setVisibility(View.GONE);
            } else if (itemOrders.getStatus() == 7) {
                tvReplaceConfirmed.setVisibility(View.GONE);
                tvReplaceWaiting.setVisibility(View.VISIBLE);
            } else {
                tvReplaceConfirmed.setVisibility(View.GONE);
                tvReplaceWaiting.setVisibility(View.GONE);
            }*/

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
