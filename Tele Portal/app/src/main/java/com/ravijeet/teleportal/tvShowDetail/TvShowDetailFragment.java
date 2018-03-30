package com.ravijeet.teleportal.tvShowDetail;

import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Cast;
import com.ravijeet.teleportal.bean.Image;
import com.ravijeet.teleportal.bean.Season;
import com.ravijeet.teleportal.bean.Trailer;
import com.ravijeet.teleportal.bean.TvShow;
import com.ravijeet.teleportal.util.Constant;
import com.ravijeet.teleportal.widgets.CurvedBackgroundShape;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.ravijeet.teleportal.util.Constant.TV_SHOW_EXTRA;

/**
 * Created by Ravijeet on 3/2/18.
 */

public class TvShowDetailFragment extends Fragment implements TvShowDetailContract.View, TvShowImageListHorizontalAdapter.ImageClickedListener {

    private TvShowDetailPresenter tvShowDetailPresenter;


    private ImageView backgroundImage, posterImage, backButton;
    private FrameLayout trailerLayout;
    private TextView releaseDateTextView, tvShowNameTextView, tvShowOverViewText;
    private TextView overViewHeading, photosHeading, castHeading, seasonHeading;
    private AppCompatRatingBar ratingBar;
    private TextView shareIcon, favoriteIcon;
    private TvShow tvShow;
    private RecyclerView castListRecyclerView, seasonListRecyclerView, photoListRecyclerView;
    private FrameLayout frameLayout;


    public TvShowDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tv_show_detail_fragment, container, false);

        tvShow = getArguments().getParcelable(TV_SHOW_EXTRA);
        backgroundImage = rootView.findViewById(R.id.backgroundPoster);
        posterImage = rootView.findViewById(R.id.poster);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        frameLayout = rootView.findViewById(R.id.frame);
        tvShowNameTextView = rootView.findViewById(R.id.tvShowName);
        releaseDateTextView = rootView.findViewById(R.id.date);
        tvShowOverViewText = rootView.findViewById(R.id.descriptionTextView);
        trailerLayout = rootView.findViewById(R.id.trailerIcon);
        castListRecyclerView = rootView.findViewById(R.id.imagesList);
        photoListRecyclerView = rootView.findViewById(R.id.photoList);
        seasonListRecyclerView = rootView.findViewById(R.id.seasonListView);
        shareIcon = rootView.findViewById(R.id.share);
        favoriteIcon = rootView.findViewById(R.id.favorite);
        backButton = rootView.findViewById(R.id.backButton);
        overViewHeading = rootView.findViewById(R.id.description);
        photosHeading = rootView.findViewById(R.id.photosHeading);
        castHeading = rootView.findViewById(R.id.imagesHeading);
        seasonHeading = rootView.findViewById(R.id.seasonsHeading);


        showBasicDetail(tvShow);

        tvShowDetailPresenter.setTVShowId(tvShow.getTvShowId());
        tvShowDetailPresenter.start();
        tvShowDetailPresenter.checkIsFavorite(getActivity(), tvShow.getTvShowId());

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvShowDetailPresenter.onFavoriteClick(getActivity(), tvShow);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return rootView;
    }

    @Override
    public void setPresenter(TvShowDetailContract.Presenter presenter) {
        tvShowDetailPresenter = (TvShowDetailPresenter) presenter;
    }

    @Override
    public void onTrailerLoaded(final Trailer trailer) {
        trailerLayout.setVisibility(View.VISIBLE);
        trailerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvShowDetailPresenter.onTrailerClick(getActivity(), trailer.getKey());
            }
        });

        shareIcon.setVisibility(View.VISIBLE);
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvShowDetailPresenter.onShareClick(getActivity(), trailer.getKey());
            }
        });
    }

    @Override
    public void showBasicDetail(TvShow tvShow) {

        CurvedBackgroundShape curvedBackgroundShape = new CurvedBackgroundShape();

        frameLayout.setBackground(new ShapeDrawable(curvedBackgroundShape));

        Picasso.with(getActivity())
                .load(Constant.BANNER_IMAGE_URL + Constant.BACKGROUND_IMAGE_SIZE + tvShow.getBackdropPath())
                .placeholder(R.drawable.banner_placeholder)
                .into(backgroundImage);

        Picasso.with(getActivity())
                .load(Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE + tvShow.getPosterPath())
                .placeholder(R.drawable.card_placeholder)
                .into(posterImage);

        posterImage.setContentDescription(tvShow.getTvShowName());
        tvShowNameTextView.setText(tvShow.getTvShowName());
        ratingBar.setRating((float) (tvShow.getVoteAverage() / 2));
        releaseDateTextView.setText(tvShow.getFirstAirDate());

        if(!tvShow.getOverView().isEmpty()) {
            overViewHeading.setVisibility(View.VISIBLE);
            tvShowOverViewText.setVisibility(View.VISIBLE);
            tvShowOverViewText.setText(tvShow.getOverView());
        }


    }

    @Override
    public void onImagesLoaded(List<Image> imageList) {

        if(imageList.size() > 0) {
            photosHeading.setVisibility(View.VISIBLE);
            photoListRecyclerView.setVisibility(View.VISIBLE);
            TvShowImageListHorizontalAdapter tvShowImageListHorizontalAdapter =
                    new TvShowImageListHorizontalAdapter(imageList, this);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false);

            photoListRecyclerView.setLayoutManager(linearLayoutManager);
            photoListRecyclerView.setAdapter(tvShowImageListHorizontalAdapter);
        }
    }

    @Override
    public void onCastLoaded(List<Cast> castList) {

        castHeading.setVisibility(View.VISIBLE);
        castListRecyclerView.setVisibility(View.VISIBLE);
        TvShowCastListHorizontalAdapter tvShowCastListHorizontalAdapter =
                new TvShowCastListHorizontalAdapter(castList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        castListRecyclerView.setLayoutManager(linearLayoutManager);
        castListRecyclerView.setAdapter(tvShowCastListHorizontalAdapter);
    }

    @Override
    public void onSeasonsLoaded(List<Season> seasonList) {

        seasonHeading.setVisibility(View.VISIBLE);
        seasonListRecyclerView.setVisibility(View.VISIBLE);
        TVShowSeasonsListHorizontalAdapter tvShowSeasonsListHorizontalAdapter =
                new TVShowSeasonsListHorizontalAdapter(seasonList, tvShowDetailPresenter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);

        seasonListRecyclerView.setLayoutManager(linearLayoutManager);
        seasonListRecyclerView.setAdapter(tvShowSeasonsListHorizontalAdapter);
    }

    @Override
    public void onNoInternetConnection() {

    }

    @Override
    public void onItemsLoadingError() {

    }

    @Override
    public void showFavoriteIcon(boolean visible) {
        if (visible) {
            favoriteIcon.setText(getString(R.string.favorite_icon));
            favoriteIcon.setContentDescription(getString(R.string.delete_favorite));
        } else {
            favoriteIcon.setText(getString(R.string.dislike_icon));
            favoriteIcon.setContentDescription(getString(R.string.save_favorite));
        }
    }

    @Override
    public void displayLoadingProgressBar(boolean isVisible) {

    }

    @Override
    public void onImageClicked(int position, List<Image> imageList) {
        tvShowDetailPresenter.onImageClicked((AppCompatActivity) getActivity(), position,
                (ArrayList<Image>) imageList, tvShow.getTvShowName());
    }
}
