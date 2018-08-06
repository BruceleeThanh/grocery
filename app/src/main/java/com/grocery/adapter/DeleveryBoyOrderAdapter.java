package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ItemDeliveryBoy;

import java.util.List;

/**
 * Created by ThanhBeo on 02/10/2017.
 */

public class DeleveryBoyOrderAdapter extends BaseRecyclerAdapter<ItemDeliveryBoy, DeleveryBoyOrderAdapter.ViewHolder> {
    public DeleveryBoyOrderAdapter(Context context, List<ItemDeliveryBoy> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(DeleveryBoyOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public DeleveryBoyOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_delivery_boy_order, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (CustomTextView) itemView.findViewById(R.id.NameDeliveryBoy);
        }

        public void binData(ItemDeliveryBoy deliveryBoy, final int position) {
            tvName.setSelected(true);
            if (deliveryBoy.getDelivery_boy_name() != null) {
                tvName.setText(deliveryBoy.getDelivery_boy_name().toString());
            }
            if (deliveryBoy.isChoose()) {
                tvName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                tvName.setBackgroundResource(R.drawable.bg_despatched_orders);
            } else {
                tvName.setTextColor(mContext.getResources().getColor(R.color.colorBlackTrans30));
                tvName.setBackgroundResource(R.drawable.radius_edt);
            }
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoose(false);
                    }
                    list.get(position).setChoose(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
