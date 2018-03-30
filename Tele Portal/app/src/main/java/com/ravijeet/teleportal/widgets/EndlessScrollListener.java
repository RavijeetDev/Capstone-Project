package com.ravijeet.teleportal.widgets;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ravijeet on 2/29/18.
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int lastFirstVisibleItem = -1;
    private LoadMoreListener loadMoreListener;
    private boolean isLoading = false;
    private int pastTotalItemCount = 0;

    public EndlessScrollListener(LoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }
    public interface LoadMoreListener {
        void loadMore();
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();

        if(pastTotalItemCount < totalItemCount){
            pastTotalItemCount = totalItemCount;
            isLoading = false;
        }

        int visibleItemCount = layoutManager.getChildCount();

        int pastVisibleItem = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();


        if(!isLoading && pastVisibleItem >= lastFirstVisibleItem && visibleItemCount + pastVisibleItem >= totalItemCount){
            loadMoreListener.loadMore();
            isLoading = true;
        }

        lastFirstVisibleItem = pastVisibleItem;

    }


}
