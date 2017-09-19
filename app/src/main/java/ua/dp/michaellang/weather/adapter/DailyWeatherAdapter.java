package ua.dp.michaellang.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.ui.viewholder.DailyWeatherViewHolder;

import java.util.List;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class DailyWeatherAdapter extends BaseAdapter<DailyForecast, DailyWeatherViewHolder> {
    public DailyWeatherAdapter(Context context) {
        super(context);
    }

    public DailyWeatherAdapter(Context context, List<DailyForecast> data) {
        super(context, data);
    }

    @Override
    public DailyWeatherViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_daily_weather, parent, false);
        return new DailyWeatherViewHolder(view);
    }
}
