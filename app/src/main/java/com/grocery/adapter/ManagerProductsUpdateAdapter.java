package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.utils.LoadImageUtils;

import java.util.List;

/**
 * Created by ThanhBeo on 20/09/2017.
 */

public class ManagerProductsUpdateAdapter extends BaseRecyclerAdapter<ItemManagerProductsUpdate,
        ManagerProductsUpdateAdapter.ViewHolder> {

    private Context mContext;

    public ManagerProductsUpdateAdapter(Context context, List<ItemManagerProductsUpdate> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(ManagerProductsUpdateAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ManagerProductsUpdateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_manage_product, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView aed;
        private CustomTextView qty;
        private CustomTextView unti;
        private CustomTextView note;
        private ImageView picture;
        private ProgressBar progressBar;
        private RelativeLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            aed = (CustomTextView) itemView.findViewById(R.id.aed_item_manage_products);
            qty = (CustomTextView) itemView.findViewById(R.id.qte_item_manage_products);
            unti = (CustomTextView) itemView.findViewById(R.id.weight_item_manage_products);
            note = (CustomTextView) itemView.findViewById(R.id.note_item_manage_products);
            picture = itemView.findViewById(R.id.im_item_manage_products);
            layoutMain = (RelativeLayout) itemView.findViewById(R.id.layoutMain);
        }

        public void binData(ItemManagerProductsUpdate itemManagerProductsUpdate, int position) {
            aed.setText(mContext.getString(R.string.aed1) + " " + itemManagerProductsUpdate.getPrice() + "");
            aed.setSelected(true);
            qty.setText(itemManagerProductsUpdate.getInstock() + "");
            unti.setText(itemManagerProductsUpdate.getNumber_unit() + " " + itemManagerProductsUpdate.getUnit_name());
            unti.setSelected(true);
            note.setText(itemManagerProductsUpdate.getName().toString());
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemManagerProductsUpdate.getPhoto(), picture, progressBar);
            loadImageUtils.loadImageWithPicasoWithImageView();
        }
    }
}
