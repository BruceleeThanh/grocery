package com.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grocery.R;
import com.grocery.common.CustomTextView;
import com.grocery.common.RoundRectCornerImageView;
import com.grocery.fragment.fragmentComboOffer.FragmentManageOfferComboCreat;
import com.grocery.model.ItemManagerProductsUpdate;
import com.grocery.utils.LoadImageUtils;

import java.util.ArrayList;

/**
 * Created by ThanhBeo on 17/07/2017.
 */

public class ManageOfferComboAdd2Apdater extends BaseRecyclerAdapter<ItemManagerProductsUpdate,
        ManageOfferComboAdd2Apdater.ViewHolder> {
    private FragmentManageOfferComboCreat fragmentManageOfferComboCreat;
    private Context mContext;

    public ManageOfferComboAdd2Apdater(Context context, ArrayList<ItemManagerProductsUpdate> list,
                                       FragmentManageOfferComboCreat fragmentManageOfferComboCreat) {
        super(context, list);
        this.fragmentManageOfferComboCreat = fragmentManageOfferComboCreat;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(ManageOfferComboAdd2Apdater.ViewHolder holder, int position) {
        holder.binData(list.get(position));
    }

    @Override
    public ManageOfferComboAdd2Apdater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_manager_offer_creat_combo_2, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CustomTextView aed;
        private CustomTextView qte;
        private CustomTextView untils;
        private CustomTextView note;
        private ImageView imClose;
        private RoundRectCornerImageView image;
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            aed = (CustomTextView) itemView.findViewById(R.id.aed);
            qte = (CustomTextView) itemView.findViewById(R.id.qte);
            untils = (CustomTextView) itemView.findViewById(R.id.untils);
            note = (CustomTextView) itemView.findViewById(R.id.note);
            imClose = (ImageView) itemView.findViewById(R.id.im_close);
            image = (RoundRectCornerImageView) itemView.findViewById(R.id.image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void binData(ItemManagerProductsUpdate itemManagerProductsUpdate) {
            aed.setSelected(true);
            aed.setText(mContext.getString(R.string.aed1) + " " + itemManagerProductsUpdate.getPrice() + "");
            qte.setText("5");
            untils.setText(itemManagerProductsUpdate.getNumber_unit() + " " + itemManagerProductsUpdate.getUnit_name());
            note.setText(itemManagerProductsUpdate.getName());
            imClose.setOnClickListener(this);
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, itemManagerProductsUpdate.getPhoto(), image, progressBar);
            loadImageUtils.loadImageWithPicaso();
        }

        @Override
        public void onClick(View v) {
            list.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            fragmentManageOfferComboCreat.unpdatePriceTotal((ArrayList<ItemManagerProductsUpdate>) list);
        }
    }
}
