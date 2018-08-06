package com.grocery.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.dialog.DialogEditUpdateProduct;
import com.grocery.model.UnitModel;

import java.util.List;

/**
 * Created by ThanhBeo on 25/09/2017.
 */

public class UnitAdapter extends BaseRecyclerAdapter<UnitModel, UnitAdapter.ViewHolder> {
    DialogEditUpdateProduct dialogEditUpdateProduct;

    public UnitAdapter(Context context, List<UnitModel> list, DialogEditUpdateProduct dialogEditUpdateProduct) {
        super(context, list);
        this.dialogEditUpdateProduct = dialogEditUpdateProduct;
    }

    @Override
    public void onBindViewHolder(UnitAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public UnitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_unit, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvUnits;
        private LinearLayout rdUnits;
        private ImageView imCheckBox;
        private LevelListDrawable levelListDrawable;
        private Drawable drawable;
        private LinearLayout layoutCheckBox;
        private LinearLayout layoutRd;


        public ViewHolder(View itemView) {
            super(itemView);
            tvUnits = (CustomTextView) itemView.findViewById(R.id.tv_unit);
            rdUnits = (LinearLayout) itemView.findViewById(R.id.rd_unit);
            layoutCheckBox = (LinearLayout) itemView.findViewById(R.id.layoutCheckBox);
            layoutRd = (LinearLayout) itemView.findViewById(R.id.layoutRd);
            imCheckBox = (ImageView) itemView.findViewById(R.id.imCheckBoxUnits);
            drawable = imCheckBox.getDrawable();
            levelListDrawable = (LevelListDrawable) drawable;
        }

        public void binData(final UnitModel unitModel, final int position) {
            tvUnits.setText(unitModel.getNumber_unit() + " " + unitModel.getUnit_name());
            if (unitModel.getIs_choose() == 1) {
                levelListDrawable.setLevel(2);
            } else {
                levelListDrawable.setLevel(1);
            }

            if (unitModel.getMain_unit() == 1) {
                rdUnits.setSelected(true);
            } else {
                rdUnits.setSelected(false);
            }
            layoutCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getMain_unit() == 1) {
                        return;
                    }
                    if (list.get(position).getIs_choose() == 0) {
                        list.get(position).setIs_choose(1);
                    } else {
                        list.get(position).setIs_choose(0);
                    }
                    dialogEditUpdateProduct.setStatusTvUpdate(true);
                    notifyDataSetChanged();
                }
            });

            layoutRd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setMain_unit(0);
                    }
                    list.get(position).setMain_unit(1);
                    list.get(position).setIs_choose(1);
                    dialogEditUpdateProduct.setStatusTvUpdate(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
