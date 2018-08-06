package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grocery.R;
import com.grocery.model.UserInfor;

import java.util.List;

/**
 * Created by DangTin on 29/06/2018.
 */

public class LiveCustomerSearchProductAdapter extends BaseRecyclerAdapter<UserInfor, LiveCustomerSearchProductAdapter.ViewHolder> {

    private Context mContext;

    public LiveCustomerSearchProductAdapter(Context context, List<UserInfor> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(LiveCustomerSearchProductAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public LiveCustomerSearchProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_live_customer_search_product, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void binData(UserInfor userInfor) {

        }
    }
}
