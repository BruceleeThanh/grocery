package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentViewUser.FragmentPurAnalytics;
import com.grocery.model.ProductInfor;

import java.util.List;

public class OtherProductUserAdapter extends BaseRecyclerAdapter<ProductInfor, OtherProductUserAdapter.ViewHolder> {
    private FragmentPurAnalytics fragmentPurAnalytics;

    public OtherProductUserAdapter(Context context, List<ProductInfor> list, FragmentPurAnalytics fragmentPurAnalytics) {
        super(context, list);
        this.fragmentPurAnalytics = fragmentPurAnalytics;
    }

    @Override
    public void onBindViewHolder(OtherProductUserAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public OtherProductUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_other_product_purchase, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvProductName;
        private LinearLayout checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductName = (CustomTextView) itemView.findViewById(R.id.tv_product_name);
            checkBox = (LinearLayout) itemView.findViewById(R.id.checkBox);
        }

        public void binData(final ProductInfor productInfor, final int position) {
            tvProductName.setSelected(true);
            tvProductName.setText(productInfor.getProduct_name() + "");
            checkBox.setSelected(false);
            setStatusItem(productInfor, checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isSelected()) {
                        checkBox.setSelected(false);
                        fragmentPurAnalytics.arrProductSelected.remove(list.get(position));
                    } else {
                        checkBox.setSelected(true);
                        fragmentPurAnalytics.arrProductSelected.add(list.get(position));
                    }
                    fragmentPurAnalytics.updateLayoutOtherProductSelected(fragmentPurAnalytics.arrProductSelected);
                }
            });
        }
    }

    public void setStatusItem(ProductInfor item, View checkBox) {
        for (int i = 0; i < fragmentPurAnalytics.arrProductSelected.size(); i++) {
            if (fragmentPurAnalytics.arrProductSelected.get(i).getProduct_id() == item.getProduct_id()
                    && fragmentPurAnalytics.arrProductSelected.get(i).getProduct_name().equals(item.getProduct_name())) {
                checkBox.setSelected(true);
            }
        }
    }
}
