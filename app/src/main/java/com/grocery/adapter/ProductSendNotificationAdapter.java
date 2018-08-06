package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.model.ProductInfor;
import com.grocery.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducth on 10/23/2017.
 */

public class ProductSendNotificationAdapter extends BaseRecyclerAdapter<ProductInfor, ProductSendNotificationAdapter.ViewHolder> {
    public static ArrayList<ProductInfor> arrAdd = new ArrayList<>();

    public ProductSendNotificationAdapter(Context context, List<ProductInfor> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(ProductSendNotificationAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ProductSendNotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_product_send_notification, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private ImageView imgClose;
        private CustomTextView tvNameProduct;
        private CustomTextView tvPriceProduct;
        private CustomEditText edtMessageNotification;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            imgClose = itemView.findViewById(R.id.img_close);
            tvNameProduct = itemView.findViewById(R.id.tv_name_product);
            tvPriceProduct = itemView.findViewById(R.id.tv_price_product);
            edtMessageNotification = itemView.findViewById(R.id.edt_message_notification);
        }

        public void binData(final ProductInfor productInfor, final int position) {
            if (!StringUtil.isEmpty(productInfor.getPhoto())) {
                Picasso.with(mContext).load(productInfor.getPhoto()).into(imgProduct);
            } else {
                imgProduct.setImageResource(R.mipmap.icon_no_image);
            }
            tvNameProduct.setText(productInfor.getProduct_name());
            tvPriceProduct.setText(mContext.getString(R.string.aed1) + " " + productInfor.getPrice());
        }
    }
}
