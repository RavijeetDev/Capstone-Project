package com.ravijeet.teleportal.favoriteScreen;



import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.data.TelePortalContract;

import static com.ravijeet.teleportal.util.Constant.FAVORITE_DETAIL_PROJECTION;


/**
 * Created by Ravijeet on 3/10/18.
 */

public class FavoriteTvShowFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView categoryList;
    private GridLayoutManager gridLayoutManager;
    private FavoriteTvShowAdapter favoriteTvShowAdapter;
    private TextView errorMessage;
    private static final int FAVORITE_LOADER_ID = 25;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);

        categoryList = rootView.findViewById(R.id.list);
        errorMessage = rootView.findViewById(R.id.error_message);

        gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        favoriteTvShowAdapter = new FavoriteTvShowAdapter(getActivity());
        categoryList.setLayoutManager(gridLayoutManager);
        categoryList.setAdapter(favoriteTvShowAdapter);

        getActivity().getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case FAVORITE_LOADER_ID:
                return new CursorLoader(getActivity(),
                        TelePortalContract.FavoriteEntry.CONTENT_URI,
                        FAVORITE_DETAIL_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException(getString(R.string.loader_exception) + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null || data.getCount() == 0) {
            errorMessage.setText(getString(R.string.no_favorite_movie));
            errorMessage.setVisibility(View.VISIBLE);
        }else {
            errorMessage.setVisibility(View.GONE);
            favoriteTvShowAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteTvShowAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int width = (int) getResources().getDimension(R.dimen.item_width);
        return screenWidth/width;
    }
}
