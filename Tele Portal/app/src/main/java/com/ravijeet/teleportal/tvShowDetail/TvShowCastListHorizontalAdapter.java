package com.ravijeet.teleportal.tvShowDetail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Cast;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravijeet on 3/2/18.
 */

public class TvShowCastListHorizontalAdapter extends RecyclerView.Adapter<TvShowCastListHorizontalAdapter.ViewHolder> {


    private List<Cast> castList;

    TvShowCastListHorizontalAdapter(List<Cast> castList) {
        this.castList = castList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView castPoster;
        TextView castName, characterName;

        public ViewHolder(View itemView) {
            super(itemView);

            castPoster = itemView.findViewById(R.id.cast_item);
            castName = itemView.findViewById(R.id.castName);
            characterName = itemView.findViewById(R.id.characterName);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Cast cast = castList.get(position);

        Picasso.with(holder.castPoster.getContext())
                .load(Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE + cast.getProfilePath())
                .placeholder(R.drawable.cast_placeholder)
                .into(holder.castPoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap source = ((BitmapDrawable) holder.castPoster.getDrawable()).getBitmap();
                        RoundedBitmapDrawable drawable =
                                RoundedBitmapDrawableFactory.create(holder.castPoster.getContext().getResources(), source);
                        drawable.setCircular(true);
                        drawable.setCornerRadius(Math.max(source.getWidth() / 2.0f, source.getHeight() / 2.0f));
                        holder.castPoster.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError() {

                    }
                });
        holder.castPoster.setContentDescription(cast.getName());
        holder.castName.setText(cast.getName());
        holder.characterName.setText(cast.getCharacter());

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
