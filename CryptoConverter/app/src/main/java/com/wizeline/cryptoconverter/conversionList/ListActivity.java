package com.wizeline.cryptoconverter.conversionList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.databinding.ActivityListBinding;
import com.wizeline.cryptoconverter.mvvm.ViewModel;
import com.wizeline.cryptoconverter.mvvm.ViewModelActivity;

public class ListActivity extends ViewModelActivity {
    ListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        binding.setViewModel(viewModel);
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {
        viewModel = new ListViewModel(savedViewModelState, this);
        return viewModel;
    }
}
