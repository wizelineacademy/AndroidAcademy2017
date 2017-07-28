package com.wizeline.omarsanchez.crashcourse.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wizeline.omarsanchez.crashcourse.R;

import java.util.ArrayList;

/**
 * Created by omarsanchez on 7/20/17.
 */

public class AdapterExample extends RecyclerView.Adapter<AdapterExample.RecyclerHolder> {
    private ArrayList<String> content;

    public AdapterExample() {
        content = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            content.add(String.valueOf(i));
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.binder(content.get(position));

    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_number);
        }

        public void binder(String text) {
            textView.setText(text);
        }
    }
}
