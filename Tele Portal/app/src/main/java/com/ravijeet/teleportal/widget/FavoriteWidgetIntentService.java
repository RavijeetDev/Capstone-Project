package com.ravijeet.teleportal.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.data.TelePortalContract;
import com.ravijeet.teleportal.util.Utility;


/**
 * Created by Ravijeet on 3/12/18.
 */

public class FavoriteWidgetIntentService extends RemoteViewsService {

    private static final String YEAR_FORMAT = "yyyy";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext());
    }

    class ListRemoteViewFactory implements RemoteViewsFactory{

        Context mContext;
        Cursor mCursor;

        public ListRemoteViewFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            if (mCursor != null) mCursor.close();

            mCursor = mContext.getContentResolver().query(
                    TelePortalContract.FavoriteEntry.CONTENT_URI,
                    new String[]{TelePortalContract.FavoriteEntry.COLUMN_TITLE, TelePortalContract.FavoriteEntry.COLUMN_RELEASE_DATE},
                    null,
                    null,
                    null);

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mCursor == null) return 0;
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            if (mCursor.getCount() != 0) {
                mCursor.moveToPosition(position);

                String year = Utility.getFormattedDate(YEAR_FORMAT, mCursor.getString(1));
                rv.setTextViewText(R.id.name, mCursor.getString(0));
                rv.setTextViewText(R.id.year, year);

            }


            return rv;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
