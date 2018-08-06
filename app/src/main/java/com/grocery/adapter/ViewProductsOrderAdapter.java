package com.grocery.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.dialog.DialogAlternativeProducts;
import com.grocery.fragment.fragmentViewOrders.FragmentViewNormalOrder;
import com.grocery.fragment.fragmentViewOrders.FragmentViewBulkSchduleOrder;
import com.grocery.model.ItemAlternavite;
import com.grocery.model.ItemProductsOrders;
import com.grocery.utils.LoadImageUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 03/10/2017.
 */

public class ViewProductsOrderAdapter extends BaseRecyclerAdapter<ItemProductsOrders, ViewProductsOrderAdapter.ViewHolder> {
    private FragmentViewNormalOrder fragmentViewNormalOrder;
    private FragmentViewBulkSchduleOrder fragmentViewBulkSchduleOrder;
    private int numberDelete = 0;
    private int orderType;
    private int is_confirm = 0;

    public ViewProductsOrderAdapter(Context context, List<ItemProductsOrders> list, FragmentViewNormalOrder fragmentViewNormalOrder, int orderType) {
        super(context, list);
        this.fragmentViewNormalOrder = fragmentViewNormalOrder;
        this.orderType = orderType;
    }

    public ViewProductsOrderAdapter(Context context, List<ItemProductsOrders> list, FragmentViewBulkSchduleOrder fragmentViewBulkSchduleOrder,
                                    int orderType, int is_confirm) {
        super(context, list);
        this.fragmentViewBulkSchduleOrder = fragmentViewBulkSchduleOrder;
        this.orderType = orderType;
        this.is_confirm = is_confirm;
    }

    public ViewProductsOrderAdapter(Context context, List<ItemProductsOrders> list, int orderType) {
        super(context, list);
        this.orderType = orderType;
    }

    @Override
    public void onBindViewHolder(ViewProductsOrderAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public ViewProductsOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_products_normal_orders, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imProducts;
        private CustomTextView tvName;
        private CustomTextView tvUnit;
        private CustomTextView tvAEDxUnit;
        private CustomTextView tvAlternavite;
        private CustomTextView tvTotal;
        private CustomTextView tvAvlQTY;
        private LinearLayout layoutWeight;
        private CustomTextView tvWeight;
        private CustomTextView tvQTY;
        private ImageView imClose;
        private ProgressBar progressBar;
        private CustomTextView tvDiscount;
        private CustomTextView tvSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            imProducts = (RoundedImageView) itemView.findViewById(R.id.imProducts);
            tvName = (CustomTextView) itemView.findViewById(R.id.nameProductsOrders);
            tvUnit = (CustomTextView) itemView.findViewById(R.id.UnitProductsOrder);
            tvAEDxUnit = (CustomTextView) itemView.findViewById(R.id.AEDxUnit);
            tvAlternavite = (CustomTextView) itemView.findViewById(R.id.Alternavite);
            tvTotal = (CustomTextView) itemView.findViewById(R.id.tvTotal);
            tvAvlQTY = (CustomTextView) itemView.findViewById(R.id.tvAvlQTY);
            layoutWeight = (LinearLayout) itemView.findViewById(R.id.layoutWeight);
            tvWeight = (CustomTextView) itemView.findViewById(R.id.tvWeight);
            tvQTY = (CustomTextView) itemView.findViewById(R.id.tvQTY);
            imClose = (ImageView) itemView.findViewById(R.id.imClose);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            tvDiscount = (CustomTextView) itemView.findViewById(R.id.tvDiscount);
            tvSelected = (CustomTextView) itemView.findViewById(R.id.tvSelected);

        }

        public void binData(ItemProductsOrders itemProductsOrders, final int position) {
            if (itemProductsOrders.getProduct_photo() != null) {
                LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemProductsOrders.getProduct_photo(),
                        imProducts, progressBar);
                loadImageUtils.loadImageWithPicasoRounderConner();


            }
            if (itemProductsOrders.getProduct_name() != null) {
                tvName.setText(itemProductsOrders.getProduct_name().toString());
                tvName.setSelected(true);
            }
            tvUnit.setText(itemProductsOrders.getNumber_unit() + " " + itemProductsOrders.getUnit_name());
            if (orderType == 0 || orderType == 1 || orderType == 3) {
                tvAlternavite.setVisibility(View.VISIBLE);
                if (itemProductsOrders.getImclose() == 0) {
                    imClose.setVisibility(View.VISIBLE);
                } else {
                    imClose.setVisibility(View.INVISIBLE);
                }
            } else if (orderType == 4) {
                tvAlternavite.setVisibility(View.INVISIBLE);
                if (itemProductsOrders.getImclose() == 0) {
                    imClose.setVisibility(View.VISIBLE);
                } else {
                    imClose.setVisibility(View.INVISIBLE);
                }
            } else {
                tvAlternavite.setVisibility(View.INVISIBLE);
                imClose.setVisibility(View.INVISIBLE);
            }
            tvAlternavite.setText(itemProductsOrders.getNumber_alternative() + " " + mContext.getResources().getString(R.string.Alternative));
            tvAEDxUnit.setText(mContext.getResources().getString(R.string.aed1) + " " + itemProductsOrders.getPrice() +
                    " X " + itemProductsOrders.getQuantity() + " " + mContext.getResources().getString(R.string.unit));
            tvAEDxUnit.setSelected(true);
            tvAvlQTY.setText(mContext.getResources().getString(R.string.avl_qty) + "" + itemProductsOrders.getAvaiable_quantity() + " " +
                    mContext.getResources().getString(R.string.unit));
            tvAvlQTY.setSelected(true);
            tvTotal.setText(mContext.getResources().getString(R.string.total) + " " + itemProductsOrders.getAvaiable_quantity() + "");
            tvQTY.setText(itemProductsOrders.getQuantity() + "");
            tvQTY.setSelected(true);
            float totalPrice = itemProductsOrders.getPrice() * itemProductsOrders.getQuantity();
            tvTotal.setText(mContext.getResources().getString(R.string.total) + " " + totalPrice + "");
            tvTotal.setSelected(true);
            tvDiscount.setSelected(true);
            tvDiscount.setText(itemProductsOrders.getPrice_discount() + "");
            if (itemProductsOrders.getNumber_of_units() == 1) {
                layoutWeight.setVisibility(View.INVISIBLE);
                tvQTY.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.radius_edt));
            } else {
                layoutWeight.setVisibility(View.VISIBLE);
                int weight = itemProductsOrders.getNumber_unit() * itemProductsOrders.getQuantity();
                tvWeight.setText(weight + "");
                tvQTY.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.render_gray));
            }
            imClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberDelete++;
                    try {
                        fragmentViewNormalOrder.updateNumberItemDelete(numberDelete);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        fragmentViewBulkSchduleOrder.updateNumberItemDelete(numberDelete);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.remove(getAdapterPosition());
                    if (list.size() == 0) {
                        try {
                            fragmentViewNormalOrder.setStatusTvSendRepleace(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            fragmentViewBulkSchduleOrder.setStatusTvSendRepleace(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    notifyItemRemoved(getAdapterPosition());
                }
            });
            tvAlternavite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogAlternativeProducts.itemProductsOrders = list.get(position);
                    DialogAlternativeProducts dialogAlternativeProducts = new DialogAlternativeProducts(mContext, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    dialogAlternativeProducts.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogAlternativeProducts.show();
                    dialogAlternativeProducts.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ArrayList<ItemAlternavite> arrSelect = new ArrayList<ItemAlternavite>();
                            if (orderType == 0 || orderType == 1 || orderType == 2) {
                                arrSelect = FragmentViewNormalOrder.arrRepleace[position];
                                tvSelected.setText(arrSelect.size() + " " + mContext.getResources().getString(R.string.selected));
                                if (arrSelect.size() > 0) {

                                    tvSelected.setVisibility(View.VISIBLE);
                                } else {
                                    tvSelected.setVisibility(View.INVISIBLE);
                                }
                                for (int i = 0; i < FragmentViewNormalOrder.arrRepleace.length; i++) {
                                    ArrayList<ItemAlternavite> arrSelect1 = new ArrayList<ItemAlternavite>();
                                    arrSelect1 = FragmentViewNormalOrder.arrRepleace[i];
                                    if (arrSelect1.size() > 0) {
                                        visiableClose(true);
                                        fragmentViewNormalOrder.setStatusTvSendRepleace(true);
                                        return;
                                    } else {
                                        visiableClose(false);
                                        fragmentViewNormalOrder.setStatusTvSendRepleace(false);
                                    }
                                }
                            }
                            if (orderType == 3 || orderType == 4) {
                                arrSelect = FragmentViewBulkSchduleOrder.arrRepleace[position];
                                tvSelected.setText(arrSelect.size() + " " + mContext.getResources().getString(R.string.selected));
                                if (arrSelect.size() > 0) {
                                    tvSelected.setVisibility(View.VISIBLE);
                                } else {
                                    tvSelected.setVisibility(View.INVISIBLE);
                                }
                                for (int i = 0; i < FragmentViewBulkSchduleOrder.arrRepleace.length; i++) {
                                    ArrayList<ItemAlternavite> arrSelect1 = new ArrayList<ItemAlternavite>();
                                    arrSelect1 = FragmentViewBulkSchduleOrder.arrRepleace[i];
                                    if (arrSelect1.size() > 0) {
                                        visiableClose(true);
                                        fragmentViewBulkSchduleOrder.setStatusTvSendRepleace(true);
                                        return;
                                    } else {
                                        visiableClose(false);
                                        fragmentViewBulkSchduleOrder.setStatusTvSendRepleace(false);
                                    }
                                }
                            }

                        }
                    });
                }
            });
        }
    }

    public void visiableClose(boolean status) {
        for (int i = 0; i < list.size(); i++) {
            if (status) {
                list.get(i).setImclose(1);
            } else {
                list.get(i).setImclose(0);
            }
        }
        notifyDataSetChanged();
    }
}
