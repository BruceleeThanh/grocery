package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.model.ItemMenu;

import java.util.List;


/**
 * Created by ThanhBeo on 29/06/2017.
 */

public class Menu1Adapter extends BaseRecyclerAdapter<ItemMenu, Menu1Adapter.ViewHolder> {

    public Menu1Adapter(Context context, List<ItemMenu> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(Menu1Adapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ItemMenu getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextviewBold tvNumber;
        private CustomTextView tvText;
        private CustomTextView tvNote;
        private LinearLayout lnItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNumber = (CustomTextviewBold) itemView.findViewById(R.id.tvNumberOrder);
            tvText = (CustomTextView) itemView.findViewById(R.id.tv_text_item_category);
            tvNote = (CustomTextView) itemView.findViewById(R.id.tv_notifi);
            lnItem = (LinearLayout) itemView.findViewById(R.id.layout_item_category);
        }

        public void binData(ItemMenu itemMenu) {
            lnItem.setBackgroundResource(itemMenu.getSrc());
            tvText.setText(itemMenu.getName());
            if (itemMenu.getNote() >= 0) {
                tvNote.setText(itemMenu.getNote() + "");
                tvNote.setVisibility(LinearLayout.VISIBLE);
            }else {
                tvNote.setVisibility(LinearLayout.INVISIBLE);
            }
            if (itemMenu.getNumber() < 0) {
                tvNumber.setVisibility(LinearLayout.GONE);
            }else {
                tvNumber.setVisibility(View.VISIBLE);
                tvNumber.setText(itemMenu.getNumber() + "");
            }
        }
    }
}
