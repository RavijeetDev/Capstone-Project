package com.ravijeet.teleportal.categoryScreen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.TvShow;
import com.ravijeet.teleportal.widgets.EndlessScrollListener;

import java.util.ArrayList;

/**
 * Created by Ravijeet on 2/29/18.
 */

public class CategoryFragment extends Fragment implements CategoryContract.View,
        CategoryAdapter.ItemListener, EndlessScrollListener.LoadMoreListener {

    private View rootView;
    private RecyclerView categoryList;
    private GridLayoutManager gridLayoutManager;
    private CategoryAdapter tvShowCategoryAdapter;
    private ProgressBar paginationBar, progressBar;
    private TextView noInternetTextView;
    private ArrayList<TvShow> tvShows;
    private CategoryPresenter categoryPresenter;

    private String categoryType;
    private int totalPages;
    private int currentPage = 1;

    private static final String CATEGORY_TYPE = "category_type";
    private static final String CURRENT_PAGE = "current_page";
    private static final String TOTAL_PAGES = "total_pages";
    private static final String TV_SHOW_LIST = "tv_show_list";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        categoryPresenter = new CategoryPresenter();
        categoryPresenter.start();

        rootView = inflater.inflate(R.layout.category_fragment, container, false);

        categoryType = getArguments().getString(CATEGORY_TYPE);

        categoryList = rootView.findViewById(R.id.list);
        paginationBar = rootView.findViewById(R.id.progressPagination);
        progressBar = rootView.findViewById(R.id.progress);
        noInternetTextView = rootView.findViewById(R.id.error_message);

        gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
        categoryList.setLayoutManager(gridLayoutManager);

        tvShowCategoryAdapter = new CategoryAdapter(this);
        categoryList.setAdapter(tvShowCategoryAdapter);

        categoryList.setOnScrollListener(new EndlessScrollListener(this));

        categoryPresenter.setView(this);
        categoryPresenter.onAttachView();
        tvShows = new ArrayList<>();
        if(savedInstanceState == null) {
            categoryPresenter.loadTvShowList(categoryType, 1);
        }else {
            progressBar.setVisibility(View.GONE);
            currentPage = savedInstanceState.getInt(CURRENT_PAGE);
            totalPages = savedInstanceState.getInt(TOTAL_PAGES);
            tvShows = savedInstanceState.getParcelableArrayList(TV_SHOW_LIST);
            tvShowCategoryAdapter.setTvShowList(tvShows);
        }

        return rootView;
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {

    }

    @Override
    public CategoryContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public void onTvShowListLoaded(ArrayList<TvShow> tvShowArrayList, int totalPages) {
        tvShows.addAll(tvShowArrayList);
        paginationBar.setVisibility(View.GONE);
        if(this.totalPages == 0){
            this.totalPages = totalPages;
        }

        tvShowCategoryAdapter.setTvShowList(tvShowArrayList);
    }

    @Override
    public void onNoInternetConnection() {
        noInternetTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayLoadingProgressBar(boolean isVisible) {
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public static CategoryFragment newInstance(String categoryType){

        CategoryFragment categoryFragment = new CategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_TYPE, categoryType);
        categoryFragment.setArguments(bundle);

        return categoryFragment;
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int width = (int) getResources().getDimension(R.dimen.item_width);
        return screenWidth/width;
    }

    @Override
    public void onItemClicked(TvShow tvShow) {
        categoryPresenter.onItemClick(getActivity(), tvShow);
    }

    @Override
    public void loadMore() {
        if(currentPage <= totalPages) {
            paginationBar.setVisibility(View.VISIBLE);
            currentPage++;
            categoryPresenter.loadTvShowList(categoryType, currentPage);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TV_SHOW_LIST, tvShows);
        outState.putInt(CURRENT_PAGE, currentPage);
        outState.putInt(TOTAL_PAGES, totalPages);
    }
}
