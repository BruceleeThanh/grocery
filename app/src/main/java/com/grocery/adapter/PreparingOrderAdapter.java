package com.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.activity.MainViewOrders;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogSelectDeliveryBoy;
import com.grocery.fragment.fragmentOrders.FragmentDespatchedOrders;
import com.grocery.fragment.fragmentOrders.FragmentPreparingOrders;
import com.grocery.model.ItemOrders;
import com.grocery.request.UpdatePreparingOrderRequest;
import com.grocery.response.BaseResponse;
import com.grocery.response.DeliveryBoyResponse;
import com.grocery.utils.StringUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 20/06/2017.
 */

public class PreparingOrderAdapter extends BaseRecyclerAdapter<ItemOrders, PreparingOrderAdapter.ViewHolder> {
    private FragmentPreparingOrders fragmentPreparingOrders;
    private static OnClickListener onClickListener;
    private Context mContext;

    public interface OnClickListener {
        void onClicked();
    }

    public static void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public PreparingOrderAdapter(Context context, List<ItemOrders> list, FragmentPreparingOrders fragmentPreparingOrders) {
        super(context, list);
        this.fragmentPreparingOrders = fragmentPreparingOrders;
        this.mContext = context;
    }

    @Override
    public ItemOrders getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_preparing_order, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView ID;
        private CustomTextviewBold flat;
        private CustomTextView buildingName;
        private CustomTextView remainingTime;
        private CustomTextView customName;
        private CustomTextviewBold Amount;
        private LinearLayout btnView;
        private CustomTextView deleveryBoy;
        private ImageView imOrderType;
        private LinearLayout layoutMain;
        private CustomTextView tvDespatch;

        public ViewHolder(View itemView) {
            super(itemView);
            ID = (CustomTextView) itemView.findViewById(R.id.tv_item_going_order_id);
            flat = (CustomTextviewBold) itemView.findViewById(R.id.tv_item_going_order_flat);
            buildingName = (CustomTextView) itemView.findViewById(R.id.tv_item_going_order_building_name);
            remainingTime = (CustomTextView) itemView.findViewById(R.id.tv_item_going_order_remaining_time);
            customName = (CustomTextView) itemView.findViewById(R.id.tv_item_going_order_customr_name);
            Amount = (CustomTextviewBold) itemView.findViewById(R.id.tv_item_going_order_Amount);
            btnView = (LinearLayout) itemView.findViewById(R.id.btn_view_item_going_order);
            deleveryBoy = (CustomTextView) itemView.findViewById(R.id.current_status);
            imOrderType = (ImageView) itemView.findViewById(R.id.imTypeOrder);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            tvDespatch = (CustomTextView) itemView.findViewById(R.id.tvDespatch);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            ID.setSelected(true);
            flat.setSelected(true);
            buildingName.setSelected(true);
            Amount.setSelected(true);
            customName.setSelected(true);
            deleveryBoy.setSelected(true);
            remainingTime.setSelected(true);

            ID.setText(itemOrders.getId() + "");
            flat.setText(itemOrders.getFlat_no() + "");
            buildingName.setText(itemOrders.getBuilding_name());
            Amount.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money() + "");

            if (itemOrders.getCustomer_name() == null) {
                customName.setText(mContext.getResources().getString(R.string.noName));
            } else {
                customName.setText(itemOrders.getCustomer_name().toString());
            }

            if (!StringUtil.isEmpty(itemOrders.getDelivery_boy_name())) {
                deleveryBoy.setText(mContext.getString(R.string.preparing) + " " + itemOrders.getDelivery_boy_name());
            } else {
                deleveryBoy.setText("");
            }

            if (itemOrders.getRemaining_time() < (5 * 60)) {
                remainingTime.setBackgroundResource(R.drawable.bg_canceled_orders);
            } else {
                remainingTime.setBackgroundResource(R.drawable.bg_despatched_orders);
            }
            if (itemOrders.getRemaining_time() != 0) {
                remainingTime.setText(com.grocery.utils.Utils.getHoursAndMinutes(itemOrders.getRemaining_time()));
            }

            switch (itemOrders.getOrder_type()) {
                case 1:
                    imOrderType.setBackgroundResource(0);
                    break;
                case 2:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                    imOrderType.setBackgroundResource(R.mipmap.icon_cars);
                    break;
                case 3:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                    imOrderType.setBackgroundResource(R.mipmap.icon_schedule);
                    break;
                case 4:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.bg_bulk_order));
                    imOrderType.setBackgroundResource(R.mipmap.icon_bulk_full);
                    break;
                case 5:
                    layoutMain.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
                    imOrderType.setBackgroundResource(R.mipmap.icon_money);
                    break;
            }

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Config.ItemOrdersView = itemOrders;
                    Intent intent = new Intent(mContext, MainViewOrders.class);
                    intent.putExtra(Config.KEY_VIEW_ORDER, list.get(position));
                    mContext.startActivity(intent);
                }
            });
            tvDespatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDeliveryBoy(Config.AdminId, Config.apiToken, Config.is_groceryAdmin, itemOrders.getId());
                }
            });
        }
    }

    private void getDeliveryBoy(int userID, String apiToken, int is_groceryAdmin, final int itemOrdersId) {
        Controller controller = new Controller();
        Call<DeliveryBoyResponse> call = controller.service.getDeliveryBoy(userID, apiToken, is_groceryAdmin);
        call.enqueue(new Callback<DeliveryBoyResponse>() {
            @Override
            public void onResponse(Response<DeliveryBoyResponse> response, Retrofit retrofit) {
                if (response != null) {
                    DeliveryBoyResponse deliveryBoyResponse = response.body();
                    if (deliveryBoyResponse != null && deliveryBoyResponse.code == 200) {
                        if (deliveryBoyResponse.result != null && deliveryBoyResponse.result.size() > 0) {
                            DialogSelectDeliveryBoy dialogSelectDeliveryBoy = new DialogSelectDeliveryBoy(mContext,
                                    android.R.style.Theme_Light_NoTitleBar_Fullscreen, deliveryBoyResponse.result,
                                    new DialogSelectDeliveryBoy.IClickSelectDeliveryBoy() {
                                        @Override
                                        public void onClickContinue(int deliveryBoyId) {
                                            UpdatePreparingOrderRequest updatePreparingOrderRequest = new UpdatePreparingOrderRequest(Config.AdminId,
                                                    Config.apiToken, itemOrdersId, deliveryBoyId, Config.is_groceryAdmin);
                                            updatePreparingOrder(mContext, updatePreparingOrderRequest);
                                            try {
                                                onClickListener.onClicked();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            dialogSelectDeliveryBoy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialogSelectDeliveryBoy.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updatePreparingOrder(final Context context, UpdatePreparingOrderRequest updatePreparingOrderRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updatePreparingOrder(updatePreparingOrderRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            fragmentPreparingOrders.cleanAdapter();
                            fragmentPreparingOrders.loadMore();
                            fragmentPreparingOrders.updateTitleFragment();
                            FragmentDespatchedOrders.isReLoadDespatch = true;
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
