package com.grocery.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentAllProduct.FragmentProBrand;
import com.grocery.model.ItemBrand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 18/09/2017.
 */

public class BrandAdapter extends BaseRecyclerAdapter<ItemBrand, BrandAdapter.ViewHolder> {
    private FragmentProBrand fragmentProBrand;
    public static ArrayList<ItemBrand> arrAdd;
    public static ArrayList<ItemBrand> arrDelete;

    public BrandAdapter(Context context, List<ItemBrand> list, FragmentProBrand fragmentProBrand) {
        super(context, list);
        this.fragmentProBrand = fragmentProBrand;
        arrAdd = new ArrayList<>();
        arrDelete = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(BrandAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_brand, parent, false);
        return new ViewHolder(view);
    }

    public void filter(ArrayList<ItemBrand> arr) {
        list = new ArrayList<>();
        list.addAll(arr);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvId;
        private CustomTextView tvName;
        private ImageView imCheckBox;
        private LevelListDrawable levelListDrawable;
        private Drawable drawable;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (CustomTextView) itemView.findViewById(R.id.idBrand);
            tvName = (CustomTextView) itemView.findViewById(R.id.NameBrand);
            imCheckBox = (ImageView) itemView.findViewById(R.id.imCheckBox);
            drawable = imCheckBox.getDrawable();
            levelListDrawable = (LevelListDrawable) drawable;
        }

        public void binData(final ItemBrand itemBrand, final int position) {
            tvId.setText(itemBrand.getId() + "");
            if (itemBrand.getIs_added() == 0) {
                tvName.setTypeface(tvName.getTypeface(), Typeface.BOLD);
                levelListDrawable.setLevel(0);
            } else {
                tvName.setTypeface(tvName.getTypeface(), Typeface.NORMAL);
                levelListDrawable.setLevel(1);
            }
            tvName.setText(itemBrand.getName().toString());
            setStatusItem(itemBrand, levelListDrawable);
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
                    fragmentProBrand.updateLayoutSelectes(arrAdd.size() + arrDelete.size());
                }
            });
        }

    }

    public void setStatusItem(ItemBrand item, LevelListDrawable levelListDrawable) {
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
}
