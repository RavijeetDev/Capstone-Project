package com.ravijeet.teleportal.util;

import com.ravijeet.teleportal.data.TelePortalContract;

/**
 * Created by Ravijeet on 2/27/18.
 */

public interface Constant {

    String API_BASE_URL = "http://api.themoviedb.org/3/";
    String BANNER_IMAGE_URL = "http://image.tmdb.org/t/p/";
    String YOUTUBE_URL = "https://youtu.be/";
    String YOUTUBE_SHARE_URL = "https://www.youtube.com/watch?v=";

    String CARD_IMAGE_SIZE = "w185";
    String BACKGROUND_IMAGE_SIZE = "w500";

    int CONNECTION_TIMEOUT = 5;

    String TV_SHOW_EXTRA = "tv_show";

    String TVSHOW_BACKROUND_EXTRA = "tv_show_background";
    String TVSHOW_ID_EXTRA = "tv_show_id";
    String SEASON_NUMBER_EXTRA = "season_number";

    String IMAGES_EXTRA = "images";
    String POSITION_EXTRA = "position";
    String TV_SHOW_NAME_EXTRA = "tv_show_name";
    String MEDIA_URL_EXTRA = "media_url";

    String[] FAVORITE_DETAIL_PROJECTION = {
            TelePortalContract.FavoriteEntry.COLUMN_ID,
            TelePortalContract.FavoriteEntry.COLUMN_TITLE,
            TelePortalContract.FavoriteEntry.COLUMN_VOTE_AVERAGE,
            TelePortalContract.FavoriteEntry.COLUMN_POSTER_PATH,
            TelePortalContract.FavoriteEntry.COLUMN_BACKGROUND_POSTER_PATH,
            TelePortalContract.FavoriteEntry.COLUMN_RELEASE_DATE,
            TelePortalContract.FavoriteEntry.COLUMN_OVERVIEW
    };

    int COLUMN_ID = 0;
    int COLUMN_TITLE = 1;
    int COLUMN_VOTE_AVERAGE = 2;
    int COLUMN_POSTER_PATH = 3;
    int COLUMN_BACKGROUND_POSTER_PATH = 4;
    int COLUMN_RELEASE_DATE = 5;
    int COLUMN_OVERVIEW = 6;

}
