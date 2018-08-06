package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentComboOffer.FragmentManageOfferComboCreat;
import com.grocery.model.ItemManagerProductsUpdate;

import java.util.List;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class ManageOfferComboAdd1Adapter extends BaseRecyclerAdapter<ItemManagerProductsUpdate,
        ManageOfferComboAdd1Adapter.ViewHolder> {
    FragmentManageOfferComboCreat fragmentManageOfferComboCreat;
    private Context mContext;

    public ManageOfferComboAdd1Adapter(Context context, List<ItemManagerProductsUpdate> list,
                                       FragmentManageOfferComboCreat fragmentManageOfferComboCreat) {
        super(context, list);
        this.fragmentManageOfferComboCreat = fragmentManageOfferComboCreat;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(ManageOfferComboAdd1Adapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ManageOfferComboAdd1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_manage_offer_creat_combo, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView products_id;
        private CustomTextView category;
        private CustomTextView productsName;
        private CustomTextView tvAdd;
        private CustomTextView price;
        private CustomTextView priceOffer;

        public ViewHolder(View itemView) {
            super(itemView);
            products_id = (CustomTextView) itemView.findViewById(R.id.products_id);
            category = (CustomTextView) itemView.findViewById(R.id.category);
            productsName = (CustomTextView) itemView.findViewById(R.id.productsName);
            price = (CustomTextView) itemView.findViewById(R.id.price);
            priceOffer = (CustomTextView) itemView.findViewById(R.id.priceOffer);
            tvAdd = (CustomTextView) itemView.findViewById(R.id.tv_combo_manager_offer_add);

        }

        public void binData(final ItemManagerProductsUpdate productsDetailModel, final int position) {
            price.setSelected(true);
            priceOffer.setSelected(true);
            products_id.setText(productsDetailModel.getProduct_code().toString());
            category.setText(productsDetailModel.getCategory_name().toString());
            productsName.setText(productsDetailModel.getName().toString());
            price.setText(mContext.getString(R.string.aed1) + " " + productsDetailModel.getPrice() + "");
            priceOffer.setText(mContext.getString(R.string.aed1) + " " + productsDetailModel.getOffer_price() + "");
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!fragmentManageOfferComboCreat.checkProductsAdd(list.get(position))) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.not_add_two_together), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    fragmentManageOfferComboCreat.updateListAdd(list.get(position));
                }
            });
        }
    }
}
