package com.grocery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ItemMenu;

import java.util.List;

/**
 * Created by ThanhBeo on 29/06/2017.
 */

public class Menu2Adapter extends BaseRecyclerAdapter<ItemMenu, Menu2Adapter.ViewHolder> {
    public Menu2Adapter(Context context, List<ItemMenu> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(Menu2Adapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ItemMenu getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_menu_2, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvNumber;
        private CustomTextView tvText;
        private CustomTextView tvNote;
        private LinearLayout lnItem;
        private LinearLayout lnNote;
        private CustomTextView tvExpiring;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber = (CustomTextView) itemView.findViewById(R.id.tv_number_list_item_category);
            tvText = (CustomTextView) itemView.findViewById(R.id.tv_text_list_item_category);
            tvNote = (CustomTextView) itemView.findViewById(R.id.tv_note_list_item_category);
            lnNote = (LinearLayout) itemView.findViewById(R.id.layout_note_list_category);
            lnItem = (LinearLayout) itemView.findViewById(R.id.layout_item_category_2);
            tvExpiring = (CustomTextView) itemView.findViewById(R.id.tv_expiring);
        }

        public void binData(ItemMenu itemMenu, final int position) {
            tvText.setSelected(true);

            tvText.setText(itemMenu.getName());
            if (itemMenu.getNote() > 0) {
                tvNote.setText("(" + itemMenu.getNote() + ")");
                lnNote.setVisibility(LinearLayout.VISIBLE);
            } else {
                lnNote.setVisibility(LinearLayout.INVISIBLE);
            }
            // set background cho layout_search_main duoc click
            if (itemMenu.checkBG) {
                lnItem.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_list_frag));
                tvNumber.setTextColor(Color.WHITE);
                tvText.setTextColor(Color.WHITE);
                tvNote.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
                tvExpiring.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
            } else {
                lnItem.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_table));
                tvNumber.setTextColor(mContext.getResources().getColor(R.color.despatched_orders));
                tvText.setTextColor(mContext.getResources().getColor(R.color.despatched_orders));
                tvNote.setTextColor(mContext.getResources().getColor(R.color.color_Mon));
                tvExpiring.setTextColor(mContext.getResources().getColor(R.color.color_Mon));
            }
            tvNumber.setText(itemMenu.getNumber() + "");
        }
    }
}
