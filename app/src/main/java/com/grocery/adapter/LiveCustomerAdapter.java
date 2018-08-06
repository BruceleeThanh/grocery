package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.activity.MainActivity;
import com.grocery.common.CustomTextView;
import com.grocery.fragment.fragmentMainScreen.FragmentLiveCustomer;
import com.grocery.model.ItemCategory;

import java.util.List;

/**
 * Created by ThanhBeo on 18/07/2017.
 */

public class LiveCustomerAdapter extends BaseRecyclerAdapter<ItemCategory, LiveCustomerAdapter.ViewHolder> {
    private MainActivity mainActivity;
    private FragmentLiveCustomer fragmentLiveCustomer;

    public LiveCustomerAdapter(Context context, List<ItemCategory> list) {
        super(context, list);
        this.mainActivity = (MainActivity) context;
    }

    @Override
    public void onBindViewHolder(LiveCustomerAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public LiveCustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_live_customer, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CustomTextView tvName;
        private ImageView imCategory;
        private CustomTextView tvPlace;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (CustomTextView) itemView.findViewById(R.id.tv_item_live_custom_name);
            imCategory = (ImageView) itemView.findViewById(R.id.im_item_live_custom_name);
            tvPlace = (CustomTextView) itemView.findViewById(R.id.tv_place_offer);
        }

        public void binData(ItemCategory itemCategory) {
            tvName.setSelected(true);
            tvPlace.setSelected(true);

            tvName.setText(itemCategory.getName() + "");
            tvPlace.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*mainActivity.getFragmentLiveCustomer().changedFragment(mainActivity.getFragmentLiveCustomer().getFragmentLiveCusItemCategory());
            mainActivity.setFragmentTMP(mainActivity.getFragmentLiveCustomer().getFragmentLiveCusItemCategory());*/
        }
    }
}
