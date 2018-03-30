package com.ravijeet.teleportal.favoriteScreen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.TvShow;
import com.ravijeet.teleportal.tvShowDetail.TvShowDetailActivity;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ravijeet.teleportal.util.Constant.COLUMN_BACKGROUND_POSTER_PATH;
import static com.ravijeet.teleportal.util.Constant.COLUMN_ID;
import static com.ravijeet.teleportal.util.Constant.COLUMN_OVERVIEW;
import static com.ravijeet.teleportal.util.Constant.COLUMN_POSTER_PATH;
import static com.ravijeet.teleportal.util.Constant.COLUMN_RELEASE_DATE;
import static com.ravijeet.teleportal.util.Constant.COLUMN_TITLE;
import static com.ravijeet.teleportal.util.Constant.COLUMN_VOTE_AVERAGE;
import static com.ravijeet.teleportal.util.Constant.TV_SHOW_EXTRA;


/**
 * Created by Ravijeet on 3/10/18.
 */

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.ViewHolder> {

    private static final String YEAR_FORMAT = "yyyy";

    private Cursor mCursor;
    private Context mContext;

    public FavoriteTvShowAdapter(Context context){

        mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView tvShowPoster;
        TextView tvShowName;
        TextView genreTextView;
        TextView voteAverage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvShowPoster = itemView.findViewById(R.id.poster);
            tvShowName = itemView.findViewById(R.id.tvShowName);
            genreTextView = itemView.findViewById(R.id.ratingText);
            voteAverage = itemView.findViewById(R.id.voteText);
            voteAverage.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            mCursor.moveToPosition(getAdapterPosition());
            TvShow tVShow = new TvShow();
            tVShow.setTvShowId(mCursor.getInt(COLUMN_ID));
            tVShow.setTvShowName(mCursor.getString(COLUMN_TITLE));
            tVShow.setVoteAverage(mCursor.getDouble(COLUMN_VOTE_AVERAGE));
            tVShow.setPosterPath(mCursor.getString(COLUMN_POSTER_PATH));
            tVShow.setBackdropPath(mCursor.getString(COLUMN_BACKGROUND_POSTER_PATH));
            tVShow.setFirstAirDate(mCursor.getString(COLUMN_RELEASE_DATE));
            tVShow.setOverView(mCursor.getString(COLUMN_OVERVIEW));
            Intent intent = new Intent(mContext, TvShowDetailActivity.class);
            intent.putExtra(TV_SHOW_EXTRA, tVShow);
            mContext.startActivity(intent);
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

        mCursor.moveToPosition(position);

        Picasso.with(holder.tvShowPoster.getContext())
                .load(Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE +
                        mCursor.getString(COLUMN_POSTER_PATH))
                .placeholder(R.drawable.banner_placeholder)
                .into(holder.tvShowPoster);
        holder.tvShowName.setText(mCursor.getString(COLUMN_TITLE));
        holder.voteAverage.setText(String.valueOf(mCursor.getDouble(COLUMN_VOTE_AVERAGE)));
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
