package com.grocery.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grocery.R;
import com.grocery.common.Config;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.common.SwitchView;
import com.grocery.controller.Controller;
import com.grocery.dialog.DialogEditUpdateProduct;
import com.grocery.dialog.DialogSendNotifiStock;
import com.grocery.fragment.fragmentMainScreen.FragmentMyStock;
import com.grocery.fragment.fragmentManageProducts.FragmentUpdateProducts;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.request.UpdateInStockStatusRequest;
import com.grocery.response.BaseResponse;
import com.grocery.utils.LoadImageUtils;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ThanhBeo on 22/06/2017.
 */

public class MyStockAdapter extends BaseRecyclerAdapter<ItemManagerProductsUpdate, MyStockAdapter.ViewHolder> {
    private FragmentMyStock fragmentMyStock;

    public MyStockAdapter(Context context, List<ItemManagerProductsUpdate> list, FragmentMyStock fragmentMyStock) {
        super(context, list);
        this.fragmentMyStock = fragmentMyStock;
    }

    @Override
    public void onBindViewHolder(MyStockAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_my_stock, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView aed;
        private CustomTextView qte;
        private CustomTextView weight;
        private CustomTextView note;
        private ImageView imEdit;
        private ImageView imRing;
        private RoundRectCornerImageView imAvata;
        private ProgressBar progressBar;
        private SwitchView switchStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            aed = (CustomTextView) itemView.findViewById(R.id.aed_item_my_stock);
            qte = (CustomTextView) itemView.findViewById(R.id.qte_item_my_stock);
            weight = (CustomTextView) itemView.findViewById(R.id.weight_item_my_stock);
            note = (CustomTextView) itemView.findViewById(R.id.note_item_my_stock);
            imEdit = (ImageView) itemView.findViewById(R.id.im_item_my_stock_edit);
            imRing = (ImageView) itemView.findViewById(R.id.im_item_my_stock_ring);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            imAvata = (RoundRectCornerImageView) itemView.findViewById(R.id.imAvata);
            switchStatus = (SwitchView) itemView.findViewById(R.id.switchStatus);
        }

        public void binData(ItemManagerProductsUpdate itemMyStock, final int position) {
            aed.setSelected(true);
            qte.setSelected(true);
            weight.setSelected(true);
            note.setSelected(true);
            aed.setText(itemMyStock.getPrice() + "");
            qte.setText(itemMyStock.getInstock() + "");
            weight.setText(itemMyStock.getNumber_unit() + " " + itemMyStock.getUnit_name());
            note.setText(itemMyStock.getName() + "");
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemMyStock.getPhoto(), imAvata, progressBar);
            loadImageUtils.loadImageWithPicaso();
            switchStatus.setChecked(false);
            if (itemMyStock.getInstock_status() == 1) {
                switchStatus.setChecked(true);
            } else {
                switchStatus.setChecked(false);
            }
            imEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUpdateProducts.productsId = list.get(getAdapterPosition()).getId();
                    DialogEditUpdateProduct dialogEditUpdateProduct = new DialogEditUpdateProduct(mContext
                            , android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialogEditUpdateProduct.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogEditUpdateProduct.show();
                    dialogEditUpdateProduct.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (DialogEditUpdateProduct.isLoad == true) {
                                fragmentMyStock.cleanAdapter();
                                fragmentMyStock.loadMore();
                                DialogEditUpdateProduct.isLoad = false;
                            }
                        }
                    });
                }
            });
            imRing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogSendNotifiStock dialogSendNotifiStock = new DialogSendNotifiStock(mContext,
                            android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialogSendNotifiStock.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogSendNotifiStock.show();
                }
            });

            switchStatus.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchView switchView, boolean isChecked) {
                    int inStock_status = 0;
                    if (isChecked) {
                        inStock_status = 1;
                    } else {
                        inStock_status = 0;
                    }
                    UpdateInStockStatusRequest updateInStockStatusRequest = new UpdateInStockStatusRequest(Config.AdminId,
                            Config.apiToken, list.get(position).getId(), inStock_status, Config.is_groceryAdmin);
                    updateInStockStatus(mContext, switchView, updateInStockStatusRequest);
                }
            });
        }
    }

    void updateInStockStatus(Context context, final SwitchView switchView,
                             UpdateInStockStatusRequest updateInStockStatusRequest) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting....");
        dialog.setCancelable(false);
        dialog.show();

        Controller controller = new Controller();
        Call<BaseResponse> call = controller.service.updateInStockStatus(updateInStockStatusRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                dialog.dismiss();
                if (response != null) {
                    BaseResponse baseResponse = response.body();
                    if (baseResponse != null) {
                        if (baseResponse.code == 200) {
                            return;
                        }
                        if (switchView.isChecked()) {
                            switchView.setChecked(false);
                        } else {
                            switchView.setChecked(true);
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
}
