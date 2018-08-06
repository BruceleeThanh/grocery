package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.CRecyclerView;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentComboOffer.FragmentManageOfferComboView;
import com.grocery.model.ItemComboOffer;
import com.grocery.request.DeleteComboOfferRequest;
import com.grocery.response.BaseResponse;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class ManageOfferComboViewAdapter extends BaseRecyclerAdapter<ItemComboOffer,
        ManageOfferComboViewAdapter.ViewHolder> {
    private FragmentManageOfferComboView fragmentManageOfferComboView;

    public ManageOfferComboViewAdapter(Context context, List<ItemComboOffer> list,
                                       FragmentManageOfferComboView fragmentManageOfferComboView) {
        super(context, list);
        this.fragmentManageOfferComboView = fragmentManageOfferComboView;
    }

    @Override
    public void onBindViewHolder(ManageOfferComboViewAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ItemComboOffer getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public ManageOfferComboViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_manage_offer_view_combo, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvDelete;
        private CustomTextView price;
        private CustomTextView priceCombo;
        private CustomTextView description;
        private ManagerProductsUpdateAdapter managerProductsUpdateAdapter;
        private CRecyclerView lvShowItemCombo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDelete = (CustomTextView) itemView.findViewById(R.id.tv_delete_a_combo);
            price = (CustomTextView) itemView.findViewById(R.id.price);
            priceCombo = (CustomTextView) itemView.findViewById(R.id.priceCombo);
            description = (CustomTextView) itemView.findViewById(R.id.description);
            lvShowItemCombo = (CRecyclerView) itemView.findViewById(R.id.lv_show_item_view_combo);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1,
                    GridLayoutManager.HORIZONTAL, false);
            lvShowItemCombo.setLayoutManager(gridLayoutManager);
        }

        public void binData(ItemComboOffer itemComboOffer, final int position) {
            price.setSelected(true);
            priceCombo.setSelected(true);
            description.setSelected(true);
            priceCombo.setText(itemComboOffer.getCombo_price() + "");
            description.setText(itemComboOffer.getDescription());
            int priceTotal = 0;
            for (int i = 0; i < itemComboOffer.getListProducts().size(); i++) {
                priceTotal += itemComboOffer.getListProducts().get(i).getPrice();
            }
            price.setText(priceTotal + "");
            managerProductsUpdateAdapter = new ManagerProductsUpdateAdapter(mContext, itemComboOffer.getListProducts());
            lvShowItemCombo.setAdapter(managerProductsUpdateAdapter);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteComboOfferRequest deleteComboOfferRequest = new DeleteComboOfferRequest(Config.AdminId,
                            Config.apiToken, list.get(position).getId(), Config.is_groceryAdmin);
                    deleteComboOffer(mContext, deleteComboOfferRequest);
                }
            });
        }
    }

    private void deleteComboOffer(final Context context, DeleteComboOfferRequest deleteComboOfferRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.deleteComboOffer(deleteComboOfferRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            fragmentManageOfferComboView.cleanAdpater();
                            fragmentManageOfferComboView.loadMore();
                        }
                        Toast.makeText(context, baseResponse.message.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
