package com.ravijeet.teleportal.tvShowSeasonDetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Episode;
import com.ravijeet.teleportal.util.Constant;
import com.ravijeet.teleportal.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravijeet on 3/6/18.
 */

public class TvShowSeasonDetailAdapter extends RecyclerView.Adapter<TvShowSeasonDetailAdapter.ViewHolder> {

    private List<Episode> episodeList;

    private final String episodeAirDateFormat = "MMMM dd, yyyy";

    TvShowSeasonDetailAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView episodePoster;
        TextView episodeName;
        TextView episodeAirDate;
        TextView episodeOverView;

        public ViewHolder(View itemView) {
            super(itemView);

            episodePoster = itemView.findViewById(R.id.episodePoster);
            episodeName = itemView.findViewById(R.id.episodeName);
            episodeAirDate = itemView.findViewById(R.id.airDate);
            episodeOverView = itemView.findViewById(R.id.overview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.episode_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Episode episode = episodeList.get(position);

        Picasso.with(holder.episodePoster.getContext())
                .load(Constant.BANNER_IMAGE_URL + Constant.BACKGROUND_IMAGE_SIZE + episode.getPosterPath())
                .placeholder(R.drawable.banner_placeholder)
                .into(holder.episodePoster);

        int episodeNumber = position+1;
        holder.episodePoster.setContentDescription(episode.getEpisodeName());
        holder.episodeName.setText( episodeNumber+ ". " + episode.getEpisodeName());
        holder.episodeAirDate.setText(Utility.getFormattedDate(episodeAirDateFormat, episode.getAirDate()));
        holder.episodeOverView.setText(episode.getOverview());

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }


}
