package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.model.ItemMyStock;

import java.util.List;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class RewardManageAdapter extends BaseRecyclerAdapter<ItemMyStock, RewardManageAdapter.ViewHoler> {

    private Context mContext;

    public RewardManageAdapter(Context context, List<ItemMyStock> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(RewardManageAdapter.ViewHoler holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public RewardManageAdapter.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_reward_manage, parent, false);
        return new ViewHoler(view);
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private CustomTextView aed;
        private CustomTextView qte;
        private CustomTextView weight;
        private CustomTextView note;

        public ViewHoler(View itemView) {
            super(itemView);
            aed = (CustomTextView) itemView.findViewById(R.id.aed_item_reward_manage);
            qte = (CustomTextView) itemView.findViewById(R.id.qte_item_reward_manage);
            weight = (CustomTextView) itemView.findViewById(R.id.weight_item_reward_manage);
            note = (CustomTextView) itemView.findViewById(R.id.note_item_reward_manage);
        }

        public void binData(ItemMyStock itemMyStock) {
            aed.setText(mContext.getString(R.string.aed1) + " " + itemMyStock.getAed() + "");
            qte.setText(itemMyStock.getQte() + "");
            weight.setText(itemMyStock.getWeight() + "");
            note.setText(itemMyStock.getNote());
        }
    }
}
