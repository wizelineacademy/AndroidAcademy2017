package com.wizeline.omarsanchez.weather;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wizeline.omarsanchez.weather.data.models.Day;
import com.wizeline.omarsanchez.weather.databinding.ItemBinding;

import java.util.List;

/**
 * Created by omarsanchez on 7/26/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.Holder> {
    List<Day> dayList;

    public ForecastAdapter(List<Day> dayList) {
        this.dayList = dayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setForecast(dayList.get(position));
        holder.binding.imageView.setImageResource(android.R.drawable.ic_menu_report_image);
        String url = holder.binding.getRoot().getContext().getString(R.string.image_url, dayList.get(position).getWeather().get(0).getIcon());
        Glide.with(holder.binding.getRoot().getContext()).load(url).into(holder.binding.imageView);

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemBinding binding;

        public Holder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

    }
}
