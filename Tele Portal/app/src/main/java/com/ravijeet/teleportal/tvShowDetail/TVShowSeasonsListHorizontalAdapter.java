package com.ravijeet.teleportal.tvShowDetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Season;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravijeet on 3/2/18.
 */

public class TVShowSeasonsListHorizontalAdapter extends RecyclerView.Adapter<TVShowSeasonsListHorizontalAdapter.ViewHolder>{

    private List<Season> seasonList;
    private TvShowDetailPresenter tvShowDetailPresenter;

    TVShowSeasonsListHorizontalAdapter(List<Season> seasonList, TvShowDetailPresenter tvShowDetailPresenter){
        this.seasonList = seasonList;
        this.tvShowDetailPresenter = tvShowDetailPresenter;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView seasonPoster;
        TextView seasonNumber, seasonAirDate;

        public ViewHolder(View itemView) {
            super(itemView);

            seasonPoster = itemView.findViewById(R.id.seasonPoster);
            seasonNumber = itemView.findViewById(R.id.seasonNumber);
            seasonAirDate = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            tvShowDetailPresenter.onSeasonItemClick(view.getContext(),
                    seasonList.get(getAdapterPosition()).getSeasonNumber(), seasonList.get(getAdapterPosition()).getSeasonPosterPath());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.season_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Season season = seasonList.get(position);

        Picasso.with(holder.seasonPoster.getContext())
                .load(Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE + season.getSeasonPosterPath())
                .placeholder(R.drawable.banner_placeholder)
                .into(holder.seasonPoster);

        holder.seasonNumber.setText(String.format(holder.seasonNumber.getContext()
                .getString(R.string.season), season.getSeasonNumber()+""));

        holder.seasonAirDate.setText(String.format(holder.seasonAirDate.getContext()
                .getString(R.string.air_date), season.getAirDate()));

    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }
}
