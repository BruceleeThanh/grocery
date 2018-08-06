package com.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.grocery.R;
import com.grocery.activity.MainViewUser;
import com.grocery.common.CustomTextView;
import com.grocery.common.CustomTextviewBold;
import com.grocery.model.UserModel;
import com.grocery.utils.LoadImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ThanhBeo on 05/07/2017.
 */

public class UserAdapter extends BaseRecyclerAdapter<UserModel, UserAdapter.ViewHolder> {
    public static final String KEY_BUNDLE = "key bundle";
    public static final String KEY_PUT_INTENT = "key put intent";

    public UserAdapter(Context context, List<UserModel> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.binData(list.get(position), position);
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_users, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imAvata;
        private CustomTextView slNo;
        private CustomTextView name;
        private CustomTextviewBold flat;
        private CustomTextView buildingName;
        private LinearLayout layoutView;

        public ViewHolder(View itemView) {
            super(itemView);
            imAvata = (CircleImageView) itemView.findViewById(R.id.im_item_users);
            name = (CustomTextView) itemView.findViewById(R.id.tv_name_item_users);
            slNo = (CustomTextView) itemView.findViewById(R.id.tv_no);
            flat = (CustomTextviewBold) itemView.findViewById(R.id.tv_flat_item_users);
            buildingName = (CustomTextView) itemView.findViewById(R.id.tv_building_item_users);
            layoutView = (LinearLayout) itemView.findViewById(R.id.layout_view_item_user);
        }

        public void binData(final UserModel userModel, int position) {
            slNo.setText((position + 1) + "");
            slNo.setSelected(true);
            name.setSelected(true);
            flat.setSelected(true);
            buildingName.setSelected(true);
            name.setText(userModel.getUser_name());
            flat.setText(userModel.getFlat_no());
            buildingName.setText(userModel.getBuilding_name());
            layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_BUNDLE, userModel);
                    Intent intent = new Intent(mContext, MainViewUser.class);
                    intent.putExtra(KEY_PUT_INTENT, bundle);
                    mContext.startActivity(intent);
                }
            });
            if (userModel.getUser_photo() == null) {
                Picasso.with(mContext).load(R.mipmap.icon_users).into(imAvata);
            }
            LoadImageUtils loadImageUtils = new LoadImageUtils(mContext, userModel.getUser_photo() + "", imAvata);
            loadImageUtils.loadImageWithPicasoUSer();
        }
    }
}
