package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ProductInfor;

import java.util.List;

public class OtherProductUserSelectedAdapter extends BaseRecyclerAdapter<ProductInfor, OtherProductUserSelectedAdapter.ViewHolder> {

    private IListenerDeleteItem iListenerDeleteItem;

    public interface IListenerDeleteItem {
        void onClickDeleteItem(int position);
    }

    public OtherProductUserSelectedAdapter(Context context, List<ProductInfor> list, IListenerDeleteItem listener) {
        super(context, list);
        this.iListenerDeleteItem = listener;
    }

    @Override
    public void onBindViewHolder(OtherProductUserSelectedAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public OtherProductUserSelectedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_other_product_purchase_selected, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvProductName;
        private ImageView imgDeleteProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            imgDeleteProduct = itemView.findViewById(R.id.img_delete_product);
        }

        public void binData(final ProductInfor productInfor, final int position) {
            tvProductName.setSelected(true);
            tvProductName.setText(productInfor.getProduct_name() + "");

            imgDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                    iListenerDeleteItem.onClickDeleteItem(position);
                }
            });
        }
    }
}
