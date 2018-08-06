package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ItemSuggestionProducts;

import java.util.List;

/**
 * Created by ThanhBeo on 18/07/2017.
 */

public class SuggestProTableAdapter extends BaseRecyclerAdapter<ItemSuggestionProducts, SuggestProTableAdapter.ViewHolder> {

    public SuggestProTableAdapter(Context context, List<ItemSuggestionProducts> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(SuggestProTableAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ItemSuggestionProducts getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public SuggestProTableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_suggest_products_table, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvPro;
        private CustomTextView tvBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPro = (CustomTextView) itemView.findViewById(R.id.tv_item_suggest_table_pro);
            tvBrand = (CustomTextView) itemView.findViewById(R.id.tv_item_suggest_table_brand);
        }

        public void binData(ItemSuggestionProducts itemSuggestionProducts) {
            tvPro.setSelected(true);
            tvBrand.setSelected(true);

            tvPro.setText(itemSuggestionProducts.getProduct_name() + "");
            tvBrand.setText(itemSuggestionProducts.getBrand_name() + "");
        }
    }
}
