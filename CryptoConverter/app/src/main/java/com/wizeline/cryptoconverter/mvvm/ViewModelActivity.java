package com.wizeline.cryptoconverter.mvvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nicole Terc on 7/28/17.
 */

public abstract class ViewModelActivity extends AppCompatActivity {
    private static final String VIEW_MODEL_STATE = "viewModelState";

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModel.State savedViewModelState = null;
        if (savedInstanceState != null) {
            savedViewModelState = savedInstanceState.getParcelable(VIEW_MODEL_STATE);
        }
        viewModel = createViewModel(savedViewModelState);
    }

    @Nullable
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            outState.putParcelable(VIEW_MODEL_STATE, viewModel.getInstanceState());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (viewModel != null) {
            viewModel.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(viewModel != null){
            viewModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(viewModel != null){
            viewModel.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (viewModel != null) {
            viewModel.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.onDestroy();
        }
    }
}
