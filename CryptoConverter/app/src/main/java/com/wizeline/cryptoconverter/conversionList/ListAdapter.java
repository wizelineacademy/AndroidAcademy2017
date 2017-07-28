package com.wizeline.cryptoconverter.conversionList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.conversionList.listItem.ListViewHolder;
import com.wizeline.cryptoconverter.data.model.Conversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicole Terc on 7/26/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private List<Conversion> items;

    public ListAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Conversion> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<Conversion> getItems() {
        return items;
    }

    public Conversion getItem(int position) {
        return items.get(position);
    }
}
