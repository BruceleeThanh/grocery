package com.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.activity.MissedSalesDetail;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.model.ItemOrders;

import java.util.List;

/**
 * Created by ducth on 10/19/2017.
 */

public class CurrentPromotionAdapter extends BaseRecyclerAdapter<ItemOrders, CurrentPromotionAdapter.ViewHolder> {
    private Context mContext;

    public CurrentPromotionAdapter(Context context, List<ItemOrders> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(CurrentPromotionAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public CurrentPromotionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_current_promotions, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /*private CustomTextView tvId;
        private CustomTextviewBold tvFlat;
        private CustomTextView tvBuildingName;
        private CustomTextView tvCustomName;
        private CustomTextviewBold tvAed;
        private CustomTextView tvMissingProducts;
        private LinearLayout btnView;*/

        public ViewHolder(View itemView) {
            super(itemView);
            /*tvId = (CustomTextView) itemView.findViewById(R.id.tvId);
            tvFlat = (CustomTextviewBold) itemView.findViewById(R.id.tvFlat);
            tvBuildingName = (CustomTextView) itemView.findViewById(R.id.tvBuildingName);
            tvCustomName = (CustomTextView) itemView.findViewById(R.id.tvCustomName);
            tvAed = (CustomTextviewBold) itemView.findViewById(R.id.tvAed);
            tvMissingProducts = (CustomTextView) itemView.findViewById(R.id.tvMissingProducts);
            btnView = itemView.findViewById(R.id.btn_view);*/
        }

        public void binData(ItemOrders itemOrders, int position) {
            /*tvId.setSelected(true);
            tvFlat.setSelected(true);
            tvBuildingName.setSelected(true);
            tvCustomName.setSelected(true);
            tvAed.setSelected(true);
            tvMissingProducts.setSelected(true);

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MissedSalesDetail.class);
                    mContext.startActivity(intent);
                }
            });*/
        }
    }
}
