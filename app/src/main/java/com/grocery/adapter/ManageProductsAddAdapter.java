package com.grocery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.fragment.fragmentManageProducts.FragmentAddProducts;
import com.grocery.model.ItemManagerProductsAdd;
import com.grocery.utils.LoadImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 07/07/2017.
 */

public class ManageProductsAddAdapter extends BaseRecyclerAdapter<ItemManagerProductsAdd, ManageProductsAddAdapter.ViewHolder> {
    private FragmentAddProducts fragmentAddProducts;
    public static ArrayList<ItemManagerProductsAdd> arrAdd;
    public static ArrayList<ItemManagerProductsAdd> arrDelete;

    public ManageProductsAddAdapter(Context context, List<ItemManagerProductsAdd> list, FragmentAddProducts fragmentAddProducts) {
        super(context, list);
        this.fragmentAddProducts = fragmentAddProducts;
        arrAdd = new ArrayList<>();
        arrDelete = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(ManageProductsAddAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ManageProductsAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        private RelativeLayout layoutCheckAdd;
        private RelativeLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            aed = itemView.findViewById(R.id.aed_item_manage_products);
            qty = itemView.findViewById(R.id.qte_item_manage_products);
            unti = itemView.findViewById(R.id.weight_item_manage_products);
            note = itemView.findViewById(R.id.note_item_manage_products);
            picture = itemView.findViewById(R.id.im_item_manage_products);
            layoutCheckAdd = itemView.findViewById(R.id.imCheckAdd);
            layoutMain = itemView.findViewById(R.id.layoutMain);
        }

        public void binData(ItemManagerProductsAdd itemManagerProductsAdd, final int position) {
            aed.setText(itemManagerProductsAdd.getPrice() + "");
            aed.setSelected(true);
            qty.setText("5");
            unti.setText(itemManagerProductsAdd.getNumber_unit() + " " + itemManagerProductsAdd.getUnit_name());
            unti.setSelected(true);
            note.setText(itemManagerProductsAdd.getCategory_name().toString());
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemManagerProductsAdd.getPhoto(), picture, progressBar);
            loadImageUtils.loadImageWithPicasoWithImageView();
            if (itemManagerProductsAdd.getIs_added() == 1) {
                layoutCheckAdd.setSelected(true);
            } else {
                layoutCheckAdd.setSelected(false);
            }
            setStatusItem(itemManagerProductsAdd, layoutMain, layoutCheckAdd);
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutCheckAdd.isSelected()) {
                        layoutCheckAdd.setSelected(false);
                        if (list.get(position).getIs_added() == 1) {
                            arrDelete.add(list.get(position));
                            layoutMain.setBackgroundColor(Color.WHITE);
                        }
                        arrAdd.remove(list.get(position));
                    } else {
                        layoutCheckAdd.setSelected(true);
                        layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.color_bg_F2));
                        if (list.get(position).getIs_added() == 0) {
                            arrAdd.add(list.get(position));
                        }
                        arrDelete.remove(list.get(position));
                    }
                    fragmentAddProducts.updateLayoutSelectes(arrAdd.size() + arrDelete.size());
                }
            });
        }
    }

    public void setStatusItem(ItemManagerProductsAdd item, View layoutMain, View layoutCheckAdd) {
        for (int i = 0; i < arrAdd.size(); i++) {
            if (arrAdd.get(i).getId() == item.getId() && arrAdd.get(i).getName().equals(item.getName())) {
                layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.color_bg_F2));
                layoutCheckAdd.setSelected(true);
            }
        }
        for (int i = 0; i < arrDelete.size(); i++) {
            if (arrDelete.get(i).getId() == item.getId() && arrDelete.get(i).getName().equals(item.getName())) {
                layoutMain.setBackgroundColor(Color.WHITE);
                layoutCheckAdd.setSelected(false);
            }
        }
    }
}
