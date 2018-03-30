package com.ravijeet.teleportal.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Ravijeet on 3/10/18.
 */

public class TelePortalContentProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private TelePortalDbHelper telePortalDbHelper;

    static final int FAVORITE = 100;
    static final int FAVORITE_WITH_ID = 101;


    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = TelePortalContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, TelePortalContract.PATH_FAVORITE, FAVORITE);
        matcher.addURI(authority, TelePortalContract.PATH_FAVORITE + "/*", FAVORITE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        telePortalDbHelper = new TelePortalDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;

        switch (uriMatcher.match(uri)){
            case FAVORITE:
                cursor = telePortalDbHelper.getReadableDatabase().query(
                        TelePortalContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case FAVORITE_WITH_ID:
                cursor = telePortalDbHelper.getReadableDatabase().query(
                        TelePortalContract.FavoriteEntry.TABLE_NAME,
                        projection,
                        TelePortalContract.FavoriteEntry.COLUMN_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder
                );
                break;

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase sqLiteDatabase = telePortalDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;

        if (uriMatcher.match(uri) == FAVORITE){
            long _id = sqLiteDatabase.insert(TelePortalContract.FavoriteEntry.TABLE_NAME,
                    null, contentValues);
            if (_id > 0) {
                returnUri = TelePortalContract.FavoriteEntry.buildFavoriteUri(_id);
            } else {
                throw new android.database.SQLException("Failed to insert row into " + uri);
            }

        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = telePortalDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsDeleted;

        if ( null == selection ) selection = "1";

        switch (match){
            case FAVORITE:
                rowsDeleted = sqLiteDatabase.delete(TelePortalContract.FavoriteEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case FAVORITE_WITH_ID:
                rowsDeleted = sqLiteDatabase.delete(TelePortalContract.FavoriteEntry.TABLE_NAME,
                        TelePortalContract.FavoriteEntry.COLUMN_ID + " = ?",
                        new String[]{TelePortalContract.FavoriteEntry.getFavoriteFromUri(uri)});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = telePortalDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsUpdated;

        switch (match) {
            case FAVORITE:
                rowsUpdated = sqLiteDatabase.update(TelePortalContract.FavoriteEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case FAVORITE_WITH_ID:
                rowsUpdated = sqLiteDatabase.update(TelePortalContract.FavoriteEntry.TABLE_NAME, contentValues,
                        TelePortalContract.FavoriteEntry.COLUMN_ID + " = ?",
                        new String[]{TelePortalContract.FavoriteEntry.getFavoriteFromUri(uri)});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
