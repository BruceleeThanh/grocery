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
import com.grocery.model.ItemOrders;
import com.grocery.model.ItemReasonReject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 10/10/2017.
 */

public class CancelOrdersAdapter extends BaseRecyclerAdapter<ItemOrders, CancelOrdersAdapter.ViewHolder> {
    ArrayList<ItemReasonReject> arrSeason = new ArrayList<>();
    private Context mContext;

    public CancelOrdersAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
        mContext = context;

        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.cant_deliver_time), 1));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.no_delivery_boy), 2));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.no_item), 3));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.products_expired), 4));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.products_damaged), 5));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.notes_conditions), 6));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.incorrect), 7));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.customer_not_opening), 8));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.duplicate_order), 9));
        arrSeason.add(new ItemReasonReject(mContext.getResources().getString(R.string.late_delivery), 10));
    }

    @Override
    public void onBindViewHolder(CancelOrdersAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public CancelOrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_cancel_orders, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvOrderId;
        ;
        private CustomTextviewBold tvFlatNo;
        private CustomTextView tvBuildingName;
        private CustomTextView tvCustomName;
        private CustomTextviewBold tvAmount;
        private CustomTextView tvReason;
        private LinearLayout btnView;
        private LinearLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = (CustomTextView) itemView.findViewById(R.id.tvOrderId);
            tvFlatNo = (CustomTextviewBold) itemView.findViewById(R.id.tvFlatNo);
            tvBuildingName = (CustomTextView) itemView.findViewById(R.id.tvBuildingName);
            tvCustomName = (CustomTextView) itemView.findViewById(R.id.tvCustomName);
            tvAmount = (CustomTextviewBold) itemView.findViewById(R.id.tvAmount);
            tvReason = (CustomTextView) itemView.findViewById(R.id.tvReason);
            btnView = (LinearLayout) itemView.findViewById(R.id.btnView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            tvFlatNo.setSelected(true);
            tvBuildingName.setSelected(true);
            tvCustomName.setSelected(true);
            tvAmount.setSelected(true);
            tvReason.setSelected(true);
            tvOrderId.setSelected(true);

            tvOrderId.setText(itemOrders.getId() + "");
            tvFlatNo.setText(itemOrders.getFlat_no() + "");
            tvBuildingName.setText(itemOrders.getBuilding_name() + "");
            if(itemOrders.getCustomer_name() != null) tvCustomName.setText(itemOrders.getCustomer_name() + "");
            tvAmount.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money() + "");
            if (getReason(itemOrders.getReject_type()) != -1) {
                tvReason.setText(arrSeason.get(getReason(itemOrders.getReject_type())).getReason().toString());
            } else {
                if(itemOrders.getReject_comment() != null) tvReason.setText(itemOrders.getReject_comment() + "");
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

            if (itemOrders.getOrder_type() == 4) {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.bg_bulk_order));
            } else {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
            }
        }
    }

    public int getReason(int reasonType) {
        for (int i = 0; i < arrSeason.size(); i++) {
            if (reasonType == arrSeason.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }
}
