package com.ravijeet.teleportal.categoryScreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.TvShow;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by Ravijeet on 2/29/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ItemListener itemListener;
    private ArrayList<TvShow> tvShowArrayList;

    private static final String YEAR_FORMAT = "yyyy";

    public CategoryAdapter(ItemListener itemListener){
        this.itemListener = itemListener;
        tvShowArrayList = new ArrayList<>();
    }

    interface ItemListener {
        void onItemClicked(TvShow tvShow);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView tvShowPoster;
        TextView tvShowName;
        TextView voteAverage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvShowPoster = itemView.findViewById(R.id.poster);
            tvShowName = itemView.findViewById(R.id.tvShowName);
            voteAverage = itemView.findViewById(R.id.voteText);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemListener.onItemClicked(tvShowArrayList.get(getAdapterPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.tv_show_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TvShow tvShow = tvShowArrayList.get(position);

        Picasso.with(holder.tvShowPoster.getContext())
                .load(Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE +
                        tvShow.getPosterPath())
                .placeholder(R.drawable.banner_placeholder)
                .into(holder.tvShowPoster);
        holder.tvShowName.setText(tvShow.getTvShowName());
        holder.voteAverage.setText(String.valueOf(tvShow.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return tvShowArrayList.size();
    }

    public void setTvShowList(ArrayList<TvShow> tvShowList){
        if(tvShowArrayList.size() == 0) {
            this.tvShowArrayList = tvShowList;
        }else {
            this.tvShowArrayList.addAll(tvShowList);
        }
        notifyDataSetChanged();
    }


}
