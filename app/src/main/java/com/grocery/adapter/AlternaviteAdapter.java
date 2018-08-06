package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.grocery.R;
import com.grocery.common.CustomEditText;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.dialog.DialogAlternativeProducts;
import com.grocery.model.ItemAlternavite;
import com.grocery.model.UnitModel;
import com.grocery.utils.HideSoftKeyBroad;
import com.grocery.utils.LoadImageUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhBeo on 04/10/2017.
 */

public class AlternaviteAdapter extends BaseRecyclerAdapter<ItemAlternavite, AlternaviteAdapter.ViewHoler> {
    private DialogAlternativeProducts dialogAlternativeProducts;
    private ArrayList<ItemAlternavite> arr = new ArrayList<>();

    public AlternaviteAdapter(Context context, List<ItemAlternavite> list, DialogAlternativeProducts dialogAlternativeProducts, ArrayList<ItemAlternavite> arr) {
        super(context, list);
        this.arr = arr;
        this.dialogAlternativeProducts = dialogAlternativeProducts;
    }

    @Override
    public void onBindViewHolder(AlternaviteAdapter.ViewHoler holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public AlternaviteAdapter.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_alternavite_products, parent, false);
        return new ViewHoler(view);
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private UnitModel unitModelDefault;
        private CustomTextView nameProductsOrders;
        private CustomTextView UnitProductsOrder;
        private CustomTextView AEDxUnit;
        private CustomTextView tvAvtQty;
        private RoundRectCornerImageView imProducts;
        private ProgressBar progressBar;
        private CustomTextView discount;
        private LinearLayout layoutQTY;
        private CustomTextView totalPrice;
        private LinearLayout layoutWeightPrice;
        private RelativeLayout layoutMain;
        private CustomTextView tvOutOfStock;
        private CustomEditText edtQTY;
        private CustomTextView tvUnitName;
        private CustomTextView tvWeight;
        private CustomTextView totalPrice2;

        public ViewHoler(View itemView) {
            super(itemView);
            unitModelDefault = new UnitModel();
            nameProductsOrders = (CustomTextView) itemView.findViewById(R.id.nameProductsOrders);
            UnitProductsOrder = (CustomTextView) itemView.findViewById(R.id.UnitProductsOrder);
            AEDxUnit = (CustomTextView) itemView.findViewById(R.id.AEDxUnit);
            tvAvtQty = (CustomTextView) itemView.findViewById(R.id.tvAvtQty);
            imProducts = (RoundRectCornerImageView) itemView.findViewById(R.id.imProducts);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            discount = (CustomTextView) itemView.findViewById(R.id.discount);
            layoutQTY = (LinearLayout) itemView.findViewById(R.id.layoutQTY);
            totalPrice = (CustomTextView) itemView.findViewById(R.id.totalPrice);
            layoutWeightPrice = (LinearLayout) itemView.findViewById(R.id.layoutWeightPrice);
            layoutMain = (RelativeLayout) itemView.findViewById(R.id.layoutMain);
            tvOutOfStock = (CustomTextView) itemView.findViewById(R.id.tvOutOfStock);
            edtQTY = (CustomEditText) itemView.findViewById(R.id.tvQTY);
            tvWeight = (CustomTextView) itemView.findViewById(R.id.tvWeight);
            tvUnitName = (CustomTextView) itemView.findViewById(R.id.tvUnitName);
            totalPrice2 = (CustomTextView) itemView.findViewById(R.id.totalPrice2);
        }

        public void binData(final ItemAlternavite itemAlternavite, int position) {
            nameProductsOrders.setText(itemAlternavite.getProduct_name().toString());
            discount.setText(itemAlternavite.getDiscount_price() + "");
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemAlternavite.getPhoto(), imProducts, progressBar);
            loadImageUtils.loadImageWithPicaso();
            layoutQTY.setVisibility(View.VISIBLE);
            tvAvtQty.setText(itemAlternavite.getAvaiable_instock() + "");
            ArrayList<UnitModel> units = itemAlternavite.getUnit();
            layoutMain.setBackgroundResource(R.drawable.render_gray_right);
            edtQTY.setEnabled(false);
            itemAlternavite.setQuantity(1);
            edtQTY.setText(itemAlternavite.getQuantity() + "");
            AEDxUnit.setText(mContext.getResources().getString(R.string.aed) + itemAlternavite.getPrice());
            AEDxUnit.setSelected(true);
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getIs_choose() == 1) {
                    unitModelDefault = units.get(i);
                    UnitProductsOrder.setText(units.get(i).getNumber_unit() + units.get(i).getUnit_name());
                }
            }
            AEDxUnit.setText(mContext.getResources().getString(R.string.aed) + itemAlternavite.getPrice() + " X " +
                    "1" + unitModelDefault.getUnit_name());
            tvWeight.setText(unitModelDefault.getNumber_unit() + "");
            if (itemAlternavite.getAvaiable_instock() < 0) {
                totalPrice.setVisibility(View.INVISIBLE);
                layoutWeightPrice.setVisibility(View.INVISIBLE);
                tvOutOfStock.setVisibility(View.VISIBLE);
                return;
            } else {
                tvOutOfStock.setVisibility(View.INVISIBLE);
                totalPrice.setVisibility(View.VISIBLE);
                layoutWeightPrice.setVisibility(View.VISIBLE);
            }
            if (units.size() == 1) {
                totalPrice.setVisibility(View.VISIBLE);
                totalPrice.setText(mContext.getString(R.string.total) + ":" + itemAlternavite.getPrice());
                layoutWeightPrice.setVisibility(View.INVISIBLE);
            } else {
                tvUnitName.setText(unitModelDefault.getUnit_name());
                layoutWeightPrice.setVisibility(View.VISIBLE);
                totalPrice.setVisibility(View.INVISIBLE);
            }
            for (int i = 0; i < arr.size(); i++) {
                if (itemAlternavite.getId() == arr.get(i).getId()) {
                    itemAlternavite.setIschoose(true);
                    layoutMain.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.render_green_light));
                    break;
                } else {
                    itemAlternavite.setIschoose(false);
                    layoutMain.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.render_gray_right));
                }
            }
            if (itemAlternavite.getAvaiable_instock() > 0) {
                layoutMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HideSoftKeyBroad.hideSoftKeyboard(dialogAlternativeProducts.getOwnerActivity(), dialogAlternativeProducts.getCurrentFocus());
                        if (itemAlternavite.ischoose()) {
                            edtQTY.setEnabled(false);
                            itemAlternavite.setIschoose(false);
                            layoutMain.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.render_gray_right));
                        } else {
                            edtQTY.setEnabled(true);
                            itemAlternavite.setIschoose(true);
                            layoutMain.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.render_green_light));
                        }
                        dialogAlternativeProducts.setStatusChoosebtn(true);
                    }
                });
                edtQTY.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        dialogAlternativeProducts.hideLayout(false);
                    }
                });
                edtQTY.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        if (s.toString().isEmpty()) {
                            s = "1";
                        }
                        AEDxUnit.setText(mContext.getResources().getString(R.string.aed) + itemAlternavite.getPrice() + " X " +
                                s + " " + unitModelDefault.getUnit_name());
                        if (layoutWeightPrice.isShown()) {
                            int totalWeight = Integer.parseInt(s + "") * unitModelDefault.getNumber_unit();
                            tvWeight.setText(totalWeight + "");
                            float price = Integer.parseInt(s + "") * unitModelDefault.getNumber_unit() * (itemAlternavite.getPrice() - itemAlternavite.getDiscount_price());
                            totalPrice2.setText(decimalFormat.format(price) + "");
                        }
                        if (totalPrice.isShown()) {
                            float Price = Integer.parseInt(s + "") * (itemAlternavite.getPrice() - itemAlternavite.getDiscount_price());
                            totalPrice.setText(mContext.getResources().getString(R.string.total) + ": " + decimalFormat.format(Price) + "");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().isEmpty()) {
                            itemAlternavite.setQuantity(1);
                        } else {
                            int qty = Integer.parseInt(edtQTY.getText().toString());
                            if (qty > itemAlternavite.getAvaiable_instock()) {
                                itemAlternavite.setQuantity(itemAlternavite.getAvaiable_instock());
                                edtQTY.setText(itemAlternavite.getAvaiable_instock() + "");
                            } else {
                                itemAlternavite.setQuantity(qty);
                            }
                        }
                    }
                });

            }
        }
    }
}
