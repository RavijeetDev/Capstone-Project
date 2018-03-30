package com.ravijeet.teleportal.tvShowDetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Image;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravijeet on 3/2/18.
 */

public class TvShowImageListHorizontalAdapter extends RecyclerView.Adapter<TvShowImageListHorizontalAdapter.ViewHolder>{


    private List<Image> imageList;
    private ImageClickedListener imageClickedListener;

    TvShowImageListHorizontalAdapter(List<Image> imageList, ImageClickedListener imageClickedListener){
        this.imageList = imageList;
        this.imageClickedListener = imageClickedListener;
    }

    public interface ImageClickedListener {
        void onImageClicked(int position, List<Image> imageList);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.photo_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            imageClickedListener.onImageClicked(getAdapterPosition(), imageList);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Image image = imageList.get(position);

        Picasso.with(holder.image.getContext())
                .load(Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE + image.getImagePath())
                .placeholder(R.drawable.card_placeholder)
                .into(holder.image);
        holder.image.setContentDescription(image.getImagePath());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
