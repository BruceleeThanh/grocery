package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.dialog.DialogSuggestPro;
import com.grocery.model.ItemProduct;

import java.util.List;

/**
 * Created by ThanhBeo on 18/07/2017.
 */

public class SuggestProductAdapter extends BaseRecyclerAdapter<ItemProduct, SuggestProductAdapter.ViewHolder> {
    private DialogSuggestPro dialogSuggestPro;

    public SuggestProductAdapter(Context context, DialogSuggestPro dialogSuggestPro, List<ItemProduct> list) {
        super(context, list);
        this.dialogSuggestPro = dialogSuggestPro;
    }

    @Override
    public void onBindViewHolder(SuggestProductAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public SuggestProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_suggest_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public ItemProduct getItembyPostion(int position) {
        return super.getItembyPostion(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvProName;
        private CustomTextView tvProdes;
        private CustomTextView tvBrandName;
        private ImageView imDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProName = (CustomTextView) itemView.findViewById(R.id.edt_item_suggest_pro_name);
            tvProdes = (CustomTextView) itemView.findViewById(R.id.edt_item_suggest_pro_des);
            tvBrandName = (CustomTextView) itemView.findViewById(R.id.edt_item_suggest_brand_name);
            imDelete = (ImageView) itemView.findViewById(R.id.im_item_suggest_pro_delete);
        }

        public void binData(ItemProduct itemProduct) {
            tvProName.setText(itemProduct.getProduct_name() + "");
            tvBrandName.setText(itemProduct.getBrand_name() + "");
            tvProdes.setText(itemProduct.getDescription() + "");
            imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    if (!list.isEmpty()) {
                        dialogSuggestPro.checkStatusBtn(true);
                    } else {
                        dialogSuggestPro.checkStatusBtn(false);
                    }
                }
            });
        }
    }
}
