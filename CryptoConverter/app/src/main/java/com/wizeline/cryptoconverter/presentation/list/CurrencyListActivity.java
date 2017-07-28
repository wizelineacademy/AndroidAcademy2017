package com.wizeline.cryptoconverter.presentation.list;

import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.wizeline.cryptoconverter.ConverterApplication;
import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.conversionList.ListAdapter;
import com.wizeline.cryptoconverter.data.model.Conversion;

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
        presenter = new CurrencyListPresenter(this, ConverterApplication.getConversionRepo());
        init();
    }

    @Override
    public void showLoading() {
        if (!swipeRefresh.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(recyclerView, error, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    @Override
    public void showList(List<Conversion> conversionList) {
        adapter.setItems(conversionList);
    }

    private void init() {
        initRecyclerView();

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(() -> presenter.fetchList());

        presenter.onCreate();
    }

    private void initRecyclerView() {
        adapter = new ListAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.currency_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.currency) {
            showCurrencyDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void showCurrencyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Currency");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String currency = input.getText().toString();
            presenter.setCurrency(currency);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
