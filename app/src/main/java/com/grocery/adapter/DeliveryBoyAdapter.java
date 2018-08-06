package com.grocery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.SwitchView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogDeteteDeliveryBoy;
import com.grocery.fragment.fragmentMenu.FragmentAddDeliveryBoy;
import com.grocery.model.ItemDeliveryBoy;
import com.grocery.request.DeleteDeliveryBoyRequest;
import com.grocery.request.UpdateDeliveryBoyStatusRequest;
import com.grocery.response.BaseResponse;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 19/06/2017.
 */

public class DeliveryBoyAdapter extends BaseRecyclerAdapter<ItemDeliveryBoy, DeliveryBoyAdapter.ViewHolder> {
    FragmentAddDeliveryBoy fragmentAddDeliveryBoy;

    public DeliveryBoyAdapter(Context context, List<ItemDeliveryBoy> list, FragmentAddDeliveryBoy fragmentAddDeliveryBoy) {
        super(context, list);
        this.fragmentAddDeliveryBoy = fragmentAddDeliveryBoy;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_delivery_boy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public ItemDeliveryBoy getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CustomTextView tvID;
        private CustomTextView tvName;
        private CustomTextView tvEmail;
        private CustomTextView btnDelete;
        private SwitchView swActive;

        public ViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id_item_delivery_boy);
            tvName = itemView.findViewById(R.id.tv_name_item_delivery_boy);
            tvEmail = itemView.findViewById(R.id.tv_email_delivery_boy);
            btnDelete = itemView.findViewById(R.id.btn_delete_item_delivery_boy);
            swActive = itemView.findViewById(R.id.switch_active);
            btnDelete.setOnClickListener(this);
        }

        public void binData(ItemDeliveryBoy deliveryBoy) {
            tvID.setText(deliveryBoy.getId() + "");
            tvName.setText(deliveryBoy.getDelivery_boy_name() + "");
            tvEmail.setText(deliveryBoy.getDelivery_boy_email() + "");
            if (deliveryBoy.getIs_active() == 1) {
                swActive.setChecked(true);
            } else {
                swActive.setChecked(false);
            }

            swActive.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                    int isActive;
                    if (isChecked) {
                        isActive = 1;
                    } else {
                        isActive = 0;
                    }
                    UpdateDeliveryBoyStatusRequest updateDeliveryBoyStatusRequest = new UpdateDeliveryBoyStatusRequest(Config.AdminId, Config.apiToken,
                            list.get(getAdapterPosition()).getId(), Config.is_groceryAdmin, isActive);
                    updateDeliveryBoyStatus(mContext, updateDeliveryBoyStatusRequest);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_delete_item_delivery_boy:
                    DialogDeteteDeliveryBoy dialogDeteteDeliveryBoy = new DialogDeteteDeliveryBoy(mContext,
                            android.R.style.Theme_Light_NoTitleBar_Fullscreen, list.get(getAdapterPosition()).getId(),
                            new DialogDeteteDeliveryBoy.IClickDeleteDeliveryBoy() {
                                @Override
                                public void onClickDeleteDeliveryBoy(int deliveryBoyId) {
                                    DeleteDeliveryBoyRequest deleteDeliveryBoyRequest = new DeleteDeliveryBoyRequest(Config.AdminId, Config.apiToken,
                                            deliveryBoyId, Config.is_groceryAdmin);
                                    deleteDeliveryBoy(mContext, deleteDeliveryBoyRequest);
                                }
                            });
                    dialogDeteteDeliveryBoy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogDeteteDeliveryBoy.show();
                    break;
            }
        }
    }

    private void updateDeliveryBoyStatus(final Context context, UpdateDeliveryBoyStatusRequest updateDeliveryBoyStatusRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateDeliveryBoyStatus(updateDeliveryBoyStatusRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
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

    private void deleteDeliveryBoy(final Context context, DeleteDeliveryBoyRequest deleteDeliveryBoyRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.deleteDeiveryBoy(deleteDeliveryBoyRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            fragmentAddDeliveryBoy.cleanAdapter();
                            fragmentAddDeliveryBoy.loadMore();
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
