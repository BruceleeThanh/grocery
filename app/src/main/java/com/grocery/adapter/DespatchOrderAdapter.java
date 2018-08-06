package com.grocery.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.grocery.common.DateTimeFormat;
import com.grocery.controller.Controller;
import com.grocery.fragment.fragmentOrders.FragmentDespatchedOrders;
import com.grocery.model.ItemOrders;
import com.grocery.request.UpdateDespatchOrderRequest;
import com.grocery.response.BaseResponse;

import java.text.ParseException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 07/10/2017.
 */

public class DespatchOrderAdapter extends BaseRecyclerAdapter<ItemOrders, DespatchOrderAdapter.ViewHolder> {
    private FragmentDespatchedOrders fragmentDespatchedOrders;
    private static OnClickListener onClickListener;
    private Context mContext;

    public interface OnClickListener {
        void onClicked();
    }

    public static void setOnClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public DespatchOrderAdapter(Context context, List<ItemOrders> list, FragmentDespatchedOrders fragmentDespatchedOrders) {
        super(context, list);
        this.fragmentDespatchedOrders = fragmentDespatchedOrders;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(DespatchOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public DespatchOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_despatch_orders, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvOrderId;
        private CustomTextviewBold tvFlatNo;
        private CustomTextView tvBuildingName;
        private ImageView imOrderType;
        private CustomTextView tvCustomName;
        private CustomTextviewBold tvAmount;
        private CustomTextView tvDeleveryboy;
        private CustomTextView tvDeliverBefore;
        private CustomTextView tvDelivered;
        private LinearLayout layoutMain;
        private LinearLayout layoutView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = (CustomTextView) itemView.findViewById(R.id.tvOrderId);
            tvFlatNo = (CustomTextviewBold) itemView.findViewById(R.id.tvFlatNo);
            tvBuildingName = (CustomTextView) itemView.findViewById(R.id.tvBuildingName);
            imOrderType = (ImageView) itemView.findViewById(R.id.imTypeOrder);
            tvCustomName = (CustomTextView) itemView.findViewById(R.id.tvCustomName);
            tvAmount = (CustomTextviewBold) itemView.findViewById(R.id.tvAmount);
            tvDeleveryboy = (CustomTextView) itemView.findViewById(R.id.tvDeleveryboy);
            tvDeliverBefore = (CustomTextView) itemView.findViewById(R.id.tvDeliverBefore);
            tvDelivered = (CustomTextView) itemView.findViewById(R.id.tvDelivered);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
            layoutView = (LinearLayout) itemView.findViewById(R.id.btnView);
        }

        public void binData(final ItemOrders itemOrders, final int position) {
            tvOrderId.setSelected(true);
            tvFlatNo.setSelected(true);
            tvBuildingName.setSelected(true);
            tvCustomName.setSelected(true);
            tvAmount.setSelected(true);
            tvDeleveryboy.setSelected(true);
            tvDeliverBefore.setSelected(true);

            tvOrderId.setText(itemOrders.getId() + "");
            tvFlatNo.setText(itemOrders.getFlat_no());
            tvBuildingName.setText(itemOrders.getBuilding_name());
            if (itemOrders.getCustomer_name() == null) {
                tvCustomName.setText(mContext.getResources().getString(R.string.noName));
            } else {
                tvCustomName.setText(itemOrders.getCustomer_name());
            }
            tvAmount.setText(mContext.getString(R.string.aed1) + " " + itemOrders.getTotal_money() + "");
            if (itemOrders.getDelivery_boy_name() == null) {
                tvDeleveryboy.setText(mContext.getResources().getString(R.string.noName));
            } else {
                tvDeleveryboy.setText(itemOrders.getDelivery_boy_name());
            }
            try {
                String dateTime = DateTimeFormat.coverTimeGMTtoLocal(itemOrders.getDeliver_before()) + "";
                tvDeliverBefore.setText(DateTimeFormat.formatTime1(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
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

            tvDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateDespatchOrderRequest updateDespatchOrderRequest = new UpdateDespatchOrderRequest(Config.AdminId,
                            Config.apiToken, itemOrders.getId(), Config.is_groceryAdmin);
                    updateDespatchOrder(mContext, updateDespatchOrderRequest);
                    try {
                        onClickListener.onClicked();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Config.ItemOrdersView = itemOrders;
                    Intent intent = new Intent(mContext, MainViewOrders.class);
                    intent.putExtra(Config.KEY_VIEW_ORDER, list.get(position));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void updateDespatchOrder(final Context context, UpdateDespatchOrderRequest updateDespatchOrderRequest) {
        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateDespatchOrder(updateDespatchOrderRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            fragmentDespatchedOrders.cleanAdapter();
                            fragmentDespatchedOrders.loadMore();
                            fragmentDespatchedOrders.updateTitleFragment();
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
