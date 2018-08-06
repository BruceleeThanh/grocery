package com.grocery.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grocery.R;
import com.grocery.common.RoundRectCornerImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ThanhBeo on 28/08/2017.
 */

public class LoadImageUtils {
    private Context context;
    private String path;
    private ProgressBar progressBar;
    private RoundRectCornerImageView roundRectCornerImageView;
    private RoundedImageView roundedImageView;
    private ImageView imageViewNormal;
    private CircleImageView imageView;

    public LoadImageUtils(Context context, String path, ProgressBar progressBar, CircleImageView imageView) {
        this.context = context;
        this.path = path;
        this.progressBar = progressBar;
        this.imageView = imageView;
    }

    public LoadImageUtils(Context context, String path, RoundRectCornerImageView roundRectCornerImageView, ProgressBar progressBar) {
        this.context = context;
        this.path = path;
        this.roundRectCornerImageView = roundRectCornerImageView;
        this.progressBar = progressBar;
    }

    public LoadImageUtils(Context context, String path, ImageView imageView, ProgressBar progressBar) {
        this.context = context;
        this.path = path;
        this.imageViewNormal = imageView;
        this.progressBar = progressBar;
    }

    public LoadImageUtils(Context context, String path, RoundedImageView roundRectCornerImageView, ProgressBar progressBar) {
        this.context = context;
        this.path = path;
        this.roundedImageView = roundRectCornerImageView;
        this.progressBar = progressBar;
    }

    public LoadImageUtils(Context context, String path, CircleImageView imageView) {
        this.context = context;
        this.path = path;
        this.imageView = imageView;
    }
    public LoadImageUtils(Context context, String path, RoundRectCornerImageView roundRectCornerImageView) {
        this.context = context;
        this.path = path;
        this.roundRectCornerImageView = roundRectCornerImageView;
    }
    public void loadImageWithPicasoRounderConner() {

        if (path.toString().isEmpty() || path == null||path.equals("")) {
            Picasso.with(context).load(R.mipmap.icon_no_image).into(roundedImageView);
            progressBar.setVisibility(View.INVISIBLE);
            roundedImageView.setVisibility(View.VISIBLE);
            return;
        }
        Picasso.with(context).load(path).centerCrop().resize(Utils.dpToPx(80,context.getResources()),Utils.dpToPx(80,context.getResources())).error(R.mipmap.icon_no_image).into(roundedImageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                roundedImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);
                roundedImageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.mipmap.icon_no_image).into(roundedImageView);
            }
        });
    }

    public void loadImageWithPicaso() {

        if (path.toString().isEmpty() || path == null||path.equals("")) {
            Picasso.with(context).load(R.mipmap.icon_no_image).into(roundRectCornerImageView);
            progressBar.setVisibility(View.INVISIBLE);
            roundRectCornerImageView.setVisibility(View.VISIBLE);
            return;
        }
        Picasso.with(context).load(path).centerCrop().resize(Utils.dpToPx(80,context.getResources()),Utils.dpToPx(80,context.getResources())).error(R.mipmap.icon_no_image).into(roundRectCornerImageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                roundRectCornerImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);
                roundRectCornerImageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.mipmap.icon_no_image).into(roundRectCornerImageView);
            }
        });
    }

    public void loadImageWithPicasoWithImageView() {

        if (path.toString().isEmpty() || path == null||path.equals("")) {
            Picasso.with(context).load(R.mipmap.icon_no_image).into(imageViewNormal);
            progressBar.setVisibility(View.INVISIBLE);
            imageViewNormal.setVisibility(View.VISIBLE);
            return;
        }
        Picasso.with(context).load(path).centerCrop().resize(Utils.dpToPx(80,context.getResources()),
                Utils.dpToPx(80,context.getResources())).error(R.mipmap.icon_no_image).into(imageViewNormal, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                imageViewNormal.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);
                imageViewNormal.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.mipmap.icon_no_image).into(imageViewNormal);
            }
        });
    }

    public void loadImageWithPicasoUSer() {

        if (path.toString().isEmpty() || path == null||path.equals("")) {
            Picasso.with(context).load(R.mipmap.icon_users).into(imageView);
            return;
        }
        Picasso.with(context).load(path).centerCrop().resize(Utils.dpToPx(80,context.getResources()),Utils.dpToPx(80,context.getResources())).error(R.mipmap.icon_users).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(R.mipmap.icon_no_image).into(imageView);
            }
        });
    }
    public void loadImageWithPicasoUSer2() {

        if (path.toString().isEmpty() || path == null||path.equals("")) {
            Picasso.with(context).load(R.mipmap.icon_no_image).into(roundRectCornerImageView);
            return;
        }
        Picasso.with(context).load(path).centerCrop().resize(Utils.dpToPx(80,context.getResources()),Utils.dpToPx(80,context.getResources())).error(R.mipmap.icon_users).into(roundRectCornerImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(R.mipmap.icon_no_image).into(roundRectCornerImageView);
            }
        });
    }

    public void loadImageWithPicasoMarker() {

        if (path.toString().isEmpty() || path == null||path.equals("")) {
            Picasso.with(context).load(R.drawable.round_avatar_2).into(imageView);
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            return;
        }
        Picasso.with(context).load(path).centerCrop().resize(Utils.dpToPx(80,context.getResources()),Utils.dpToPx(80,context.getResources())).error(R.drawable.round_avatar_2).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load(R.drawable.round_avatar_2).into(imageView);
            }
        });
    }
}
