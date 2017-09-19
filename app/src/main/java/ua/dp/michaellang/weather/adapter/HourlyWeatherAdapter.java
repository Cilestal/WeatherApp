package ua.dp.michaellang.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.ui.viewholder.HourlyWeatherViewHolder;

import java.util.List;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class HourlyWeatherAdapter extends BaseAdapter<HourlyForecast, HourlyWeatherViewHolder> {
    public HourlyWeatherAdapter(Context context) {
        super(context);
    }

    public HourlyWeatherAdapter(Context context, List<HourlyForecast> data) {
        super(context, data);
    }

    @Override
    public HourlyWeatherViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_hourly_weather, parent, false);
        return new HourlyWeatherViewHolder(view);
    }
}
