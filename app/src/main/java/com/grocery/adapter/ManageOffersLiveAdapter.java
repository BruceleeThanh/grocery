package com.grocery.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.dialog.DialogEditUpdateProduct;
import com.grocery.fragment.fragmentManageProducts.FragmentUpdateProducts;
import com.grocery.fragment.fragmentManagerOffer.FragmentManagerOffersRegular;
import com.grocery.model.ItemManagerProductsUpdate;

import java.util.List;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class ManageOffersLiveAdapter extends BaseRecyclerAdapter<ItemManagerProductsUpdate,
        ManageOffersLiveAdapter.ViewHolder> {
    private FragmentManagerOffersRegular fragmentManagerOffersRegular;
    private Context mContext;

    public ManageOffersLiveAdapter(Context context, List<ItemManagerProductsUpdate> list,
                                   FragmentManagerOffersRegular fragmentManagerOffersRegular) {
        super(context, list);
        this.fragmentManagerOffersRegular = fragmentManagerOffersRegular;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(ManageOffersLiveAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ManageOffersLiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_manage_offers, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView id;
        private CustomTextView category;
        private CustomTextView name;
        private CustomTextView tvPrice;
        private CustomTextView tvPriceOffer;
        private CustomTextView edit;
        private CustomTextView delete;
        private CustomTextView active;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (CustomTextView) itemView.findViewById(R.id.item_manage_offer_id);
            category = (CustomTextView) itemView.findViewById(R.id.item_manage_offer_category);
            name = (CustomTextView) itemView.findViewById(R.id.item_manage_offer_name);
            tvPrice = (CustomTextView) itemView.findViewById(R.id.tvPrice);
            tvPriceOffer = (CustomTextView) itemView.findViewById(R.id.tvPriceOffer);
            edit = (CustomTextView) itemView.findViewById(R.id.tv_item_manage_offer_edit);
            delete = (CustomTextView) itemView.findViewById(R.id.tv_item_manage_offer_delete);
            active = (CustomTextView) itemView.findViewById(R.id.tv_item_manage_offer_active);
        }

        public void binData(ItemManagerProductsUpdate itemManageOffers) {
            name.setSelected(true);
            category.setSelected(true);
            tvPrice.setSelected(true);
            tvPriceOffer.setSelected(true);
            id.setText(itemManageOffers.getId() + "");
            category.setText(itemManageOffers.getCategory_name().toString());
            name.setText(itemManageOffers.getName().toString());
            tvPrice.setText(mContext.getString(R.string.aed1) + " " + itemManageOffers.getPrice() + "");
            tvPriceOffer.setText(mContext.getString(R.string.aed1) + " " + itemManageOffers.getOffer_price() + "");
            if (itemManageOffers.getStatus() == 1) {
                active.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                active.setBackgroundResource(R.drawable.btn_open_again_shop);
            } else {
                active.setTextColor(mContext.getResources().getColor(R.color.text_save));
                active.setBackgroundResource(R.drawable.btn_save);
            }
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUpdateProducts.productsId = list.get(getAdapterPosition()).getId();
                    DialogEditUpdateProduct dialogEditUpdateProduct = new DialogEditUpdateProduct(mContext
                            , android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialogEditUpdateProduct.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogEditUpdateProduct.show();
                    dialogEditUpdateProduct.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (DialogEditUpdateProduct.isLoad == true) {
                                fragmentManagerOffersRegular.cleanAdapter();
                                fragmentManagerOffersRegular.loadMore();
                                DialogEditUpdateProduct.isLoad = false;
                            }
                        }
                    });
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
