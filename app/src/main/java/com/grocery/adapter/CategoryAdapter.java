package com.grocery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.fragment.fragmentManageCategory.FragmentManageCategoryAdd;
import com.grocery.model.ItemCategory;
import com.grocery.utils.LoadImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 30/06/2017.
 */

public class CategoryAdapter extends BaseRecyclerAdapter<ItemCategory, CategoryAdapter.ViewHolder> {
    public FragmentManageCategoryAdd fragmentManageCategoryAdd;
    public static ArrayList<ItemCategory> arrChoose;
    public static ArrayList<ItemCategory> arrDelete;

    public CategoryAdapter(Context context, FragmentManageCategoryAdd fragmentManageCategoryAdd, List<ItemCategory> list) {
        super(context, list);
        this.fragmentManageCategoryAdd = fragmentManageCategoryAdd;
        arrChoose = new ArrayList<>();
        arrDelete = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public ItemCategory getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvName;
        private RelativeLayout layoutAdd;
        private ImageView imItem;
        private ProgressBar progressBar;
        private LinearLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (CustomTextView) itemView.findViewById(R.id.tv_name_item_category);
            tvName.setSelected(true);
            layoutAdd = (RelativeLayout) itemView.findViewById(R.id.layoutAdded);
            imItem = itemView.findViewById(R.id.im_item_category);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layout_main_item_category);
        }

        public void binData(ItemCategory itemCategory, final int position) {
            layoutMain.setBackgroundResource(R.drawable.radius_list);
            tvName.setText(itemCategory.getName());
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemCategory.getPhoto(), imItem, progressBar);
            loadImageUtils.loadImageWithPicasoWithImageView();
            if (itemCategory.getIs_added() == 1) {
                layoutAdd.setSelected(true);
            } else {
                layoutAdd.setSelected(false);
            }
            setStatusItem(itemCategory, layoutMain, layoutAdd, tvName);
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutAdd.isSelected()) {
                        if (list.get(position).getIs_added() == 1) {
                            layoutMain.setBackgroundResource(R.drawable.bg_not_added);
                            tvName.setTextColor(Color.WHITE);
                            arrDelete.add(list.get(position));
                        } else {
                            layoutMain.setBackgroundResource(R.drawable.radius_list);
                            tvName.setTextColor(mContext.getResources().getColor(R.color.text_live_cus));
                        }
                        layoutAdd.setSelected(false);
                        arrChoose.remove(list.get(position));
                    } else {
                        tvName.setTextColor(mContext.getResources().getColor(R.color.text_live_cus));
                        layoutMain.setBackgroundResource(R.drawable.radius_list);
                        layoutAdd.setSelected(true);
                        if (list.get(position).getIs_added() == 0) {
                            arrChoose.add(list.get(position));
                        }
                        arrDelete.remove(list.get(position));
                    }
                    fragmentManageCategoryAdd.updateLayoutSelectes(arrChoose.size() + arrDelete.size());
                }
            });
        }
    }

    public void filter(ArrayList<ItemCategory> arr) {
        list = new ArrayList<>();
        list.addAll(arr);
        notifyDataSetChanged();
    }

    public void setStatusItem(ItemCategory item, View layoutMain, View layoutAdd, CustomTextView tvName) {
        for (int i = 0; i < arrChoose.size(); i++) {
            if (arrChoose.get(i).getId() == item.getId() && arrChoose.get(i).getName().equals(item.getName())) {
                tvName.setTextColor(mContext.getResources().getColor(R.color.text_live_cus));
                layoutMain.setBackgroundResource(R.drawable.radius_list);
                layoutAdd.setSelected(true);
            }
        }
        for (int i = 0; i < arrDelete.size(); i++) {
            if (arrDelete.get(i).getId() == item.getId() && arrDelete.get(i).getName().equals(item.getName())) {
                layoutMain.setBackgroundResource(R.drawable.bg_not_added);
                tvName.setTextColor(Color.WHITE);
                layoutAdd.setSelected(false);
            }
        }
    }
}
