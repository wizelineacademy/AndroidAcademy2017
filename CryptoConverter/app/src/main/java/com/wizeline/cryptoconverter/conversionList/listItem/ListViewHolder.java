package com.wizeline.cryptoconverter.conversionList.listItem;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.databinding.ListItemBinding;

/**
 * Created by Nicole Terc on 7/27/17.
 */

public class ListViewHolder extends RecyclerView.ViewHolder {

    ListItemViewModel viewModel;
    ListItemBinding binding;

    public ListViewHolder(View itemView) {
        super(itemView);
        binding = ListItemBinding.bind(itemView);
        viewModel = new ListItemViewModel(itemView.getContext());
        binding.setViewModel(viewModel);
    }

    public void bindItem(Conversion item) {
        viewModel.setItem(item);
    }
}
