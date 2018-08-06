package com.grocery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.fragment.fragmentAllProduct.FragmentProSubCategory;
import com.grocery.model.ItemManagerProductsAdd;
import com.grocery.model.ItemSubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 18/09/2017.
 */

public class SubCategoryAdapter extends BaseRecyclerAdapter<ItemSubCategory, SubCategoryAdapter.ViewHolder> {
    private FragmentProSubCategory fragmentProSubCategory;
    public static ArrayList<ItemSubCategory> arrAdd;
    public static ArrayList<ItemSubCategory> arrDelete;

    public SubCategoryAdapter(Context context, List<ItemSubCategory> list, FragmentProSubCategory fragmentProSubCategory) {
        super(context, list);
        this.fragmentProSubCategory = fragmentProSubCategory;
        arrAdd = new ArrayList<>();
        arrDelete = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(SubCategoryAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_sub_category, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextviewBold tvId;
        private CustomTextView tvCategory;
        private CustomTextView tvSubCategory;
        private ImageView imCheckBox;
        private LevelListDrawable levelListDrawable;
        private Drawable drawable;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (CustomTextviewBold) itemView.findViewById(R.id.tv_id);
            tvCategory = (CustomTextView) itemView.findViewById(R.id.tv_category);
            tvSubCategory = (CustomTextView) itemView.findViewById(R.id.tv_subcategory);
            imCheckBox = (ImageView) itemView.findViewById(R.id.imCheckBox);
            drawable = imCheckBox.getDrawable();
            levelListDrawable = (LevelListDrawable) drawable;
        }

        public void binData(ItemSubCategory itemSubCategory, final int position) {
            tvId.setText(itemSubCategory.getId() + "");
            for (int i = 0; i < Config.arrItemCategory.size(); i++) {
                if (itemSubCategory.getCategory_id() == Config.arrItemCategory.get(i).getId()) {
                    tvCategory.setText(Config.arrItemCategory.get(i).getName().toString());
                }
            }
            if (itemSubCategory.getIs_added() == 0) {
                tvSubCategory.setTypeface(tvSubCategory.getTypeface(), Typeface.BOLD);
                levelListDrawable.setLevel(0);
            } else {
                tvSubCategory.setTypeface(tvSubCategory.getTypeface(), Typeface.NORMAL);
                levelListDrawable.setLevel(1);
            }
            tvSubCategory.setText(itemSubCategory.getName().toString());
            setStatusItem(itemSubCategory, levelListDrawable);
            imCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (levelListDrawable.getLevel()) {
                        case 0:
                            if (list.get(position).getIs_added() == 0) {
                                levelListDrawable.setLevel(2);
                                arrAdd.add(list.get(position));
                            } else {
                                levelListDrawable.setLevel(1);
                                arrDelete.remove(list.get(position));
                            }
                            break;
                        case 1:
                            levelListDrawable.setLevel(0);
                            arrDelete.add(list.get(position));
                            break;
                        case 2:
                            arrAdd.remove(list.get(position));
                            levelListDrawable.setLevel(0);
                            break;
                    }
                    fragmentProSubCategory.updateLayoutSelectes(arrAdd.size() + arrDelete.size());
                }
            });
        }

    }

    public void setStatusItem(ItemSubCategory item, LevelListDrawable levelListDrawable) {
        for (int i = 0; i < arrAdd.size(); i++) {
            if (item.getId() == arrAdd.get(i).getId() && item.getName().equals(arrAdd.get(i).getName())) {
                levelListDrawable.setLevel(2);
            }
        }
        for (int i = 0; i < arrDelete.size(); i++) {
            if (item.getId() == arrDelete.get(i).getId() && item.getName().equals(arrDelete.get(i).getName())) {
                levelListDrawable.setLevel(0);
            }
        }
    }

    public void filter(ArrayList<ItemSubCategory> arr) {
        list = new ArrayList<>();
        list.addAll(arr);
        notifyDataSetChanged();
    }
}
