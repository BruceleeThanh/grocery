package com.grocery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentViewUser.FragmentPurAnalytics;
import com.grocery.model.ItemManagerProductsAdd;
import com.grocery.model.ProductInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducth on 10/23/2017.
 */

public class ProductUserAdapter extends BaseRecyclerAdapter<ProductInfor, ProductUserAdapter.ViewHolder> {
    private FragmentPurAnalytics fragmentPurAnalytics;
    public static ArrayList<ProductInfor> arrAdd = new ArrayList<>();

    public ProductUserAdapter(Context context, List<ProductInfor> list, FragmentPurAnalytics fragmentPurAnalytics) {
        super(context, list);
        this.fragmentPurAnalytics = fragmentPurAnalytics;
    }

    @Override
    public void onBindViewHolder(ProductUserAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ProductUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_product_purchase, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvId;
        private CustomTextView tvName;
        private CustomTextView tvQuantity;
        private LinearLayout checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (CustomTextView) itemView.findViewById(R.id.tvId);
            tvName = (CustomTextView) itemView.findViewById(R.id.tvName);
            tvQuantity = (CustomTextView) itemView.findViewById(R.id.tvQuantity);
            checkBox = (LinearLayout) itemView.findViewById(R.id.checkBox);
        }

        public void binData(final ProductInfor productInfor, final int position) {
            tvId.setSelected(true);
            tvName.setSelected(true);
            tvQuantity.setSelected(true);
            tvId.setText(productInfor.getProduct_id() + "");
            tvName.setText(productInfor.getProduct_name() + "");
            tvQuantity.setText(productInfor.getQuantity() + "");
            checkBox.setSelected(false);
            setStatusItem(productInfor, checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isSelected()) {
                        checkBox.setSelected(false);
                        arrAdd.remove(list.get(position));
                    } else {
                        checkBox.setSelected(true);
                        arrAdd.add(list.get(position));
                    }
                    fragmentPurAnalytics.updateLayoutSelectes(arrAdd);
                }
            });
        }
    }

    public void setStatusItem(ProductInfor item, View checkBox) {
        for (int i = 0; i < arrAdd.size(); i++) {
            if (arrAdd.get(i).getProduct_id() == item.getProduct_id() && arrAdd.get(i).getProduct_name().equals(item.getProduct_name())) {
                checkBox.setSelected(true);
            }
        }
    }
}
