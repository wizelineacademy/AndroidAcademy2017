package com.wizeline.cryptoconverter.presentation.list;

import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.conversionList.ListAdapter;
import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.data.repo.retrofit.RetrofitService;

import java.util.List;

public class CurrencyListActivity extends AppCompatActivity implements CurrencyListContract.CurrencyListView {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefresh;

    private CurrencyListContract.CurrencyListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        presenter = new CurrencyListPresenter(this, new RetrofitService(this));
        init();
    }

    @Override public void showLoading() {
        if(!swipeRefresh.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    @Override public void showError(String error) {
        Snackbar.make(recyclerView.getRootView(), error, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    @Override public void showList(List<Conversion> conversionList) {
        adapter.setItems(conversionList);
    }

    private void init() {
        //Init adapter
        adapter = new ListAdapter();

        //Init recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(() -> presenter.fetchList());

        presenter.onCreate();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
