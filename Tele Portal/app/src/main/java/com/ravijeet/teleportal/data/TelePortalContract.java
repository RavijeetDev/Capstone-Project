package com.ravijeet.teleportal.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ravijeet on 3/10/18.
 */

public class TelePortalContract {

    public static final String CONTENT_AUTHORITY = "com.ravijeet.teleportal";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE = "favorite";

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE)
                .build();

        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKGROUND_POSTER_PATH = "background_poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildFavoriteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getFavoriteFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }
    }
}
