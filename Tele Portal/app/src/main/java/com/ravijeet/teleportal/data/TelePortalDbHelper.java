package com.ravijeet.teleportal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ravijeet.teleportal.data.TelePortalContract.*;

/**
 * Created by Ravijeet on 3/10/18.
 */

public class TelePortalDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "teleportal.db";

    public TelePortalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_DETAIL_TABLE =
                "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                        FavoriteEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                        FavoriteEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        FavoriteEntry.COLUMN_POSTER_PATH + " TEXT, " +
                        FavoriteEntry.COLUMN_BACKGROUND_POSTER_PATH + " TEXT, " +
                        FavoriteEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                        FavoriteEntry.COLUMN_OVERVIEW + " TEXT );";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
