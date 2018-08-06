package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ItemStockNotifi;

import java.util.List;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class StockNotifiAdapter extends BaseRecyclerAdapter<ItemStockNotifi, StockNotifiAdapter.ViewHolder> {
    public StockNotifiAdapter(Context context, List<ItemStockNotifi> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(StockNotifiAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ItemStockNotifi getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_stock_notifi, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CustomTextView aed;
        private CustomTextView qte;
        private CustomTextView weight;
        private CustomTextView note;
        private CustomTextView updateStock;

        public ViewHolder(View itemView) {
            super(itemView);
            aed = (CustomTextView) itemView.findViewById(R.id.aed_item_stock_notifi);
            qte = (CustomTextView) itemView.findViewById(R.id.qte_item_stock_notifi);
            weight = (CustomTextView) itemView.findViewById(R.id.weight_item_stock_notifi);
            note = (CustomTextView) itemView.findViewById(R.id.note_item_stock_nOtifi);
            updateStock = (CustomTextView) itemView.findViewById(R.id.btn_item_stock_notifi);
        }

        public void binData(ItemStockNotifi itemStockNotifi) {
            aed.setText(itemStockNotifi.getAed() + "");
            qte.setText(itemStockNotifi.getQte() + "");
            weight.setText(itemStockNotifi.getWeight() + "");
            note.setText(itemStockNotifi.getNote());
            updateStock.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
