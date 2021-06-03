package com.example.project;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

    @BindingAdapter("app:gameStatus")
    public static void bindStatus(ImageView view, GameStatus status) {
        switch (status) {
            case LOADING: {
                view.setVisibility(View.VISIBLE);
                view.setImageResource(R.drawable.ic_cloud_download);
                break;
            }
            case FAIL: {
                view.setVisibility(View.VISIBLE);
                view.setImageResource(R.drawable.ic_connection_error);
                break;
            }
            default: view.setVisibility(View.GONE);
        }
    }
}
