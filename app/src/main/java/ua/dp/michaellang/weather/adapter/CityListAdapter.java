package ua.dp.michaellang.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.ui.viewholder.CityViewHolder;
import ua.dp.michaellang.weather.utils.KeyValuePair;

import java.util.HashMap;
import java.util.List;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public class CityListAdapter extends BaseAdapter<City, CityViewHolder> {
    private HashMap<String, HourlyForecast> mForecastHashMap;

    public CityListAdapter(Context context) {
        super(context);
        mForecastHashMap = new HashMap<>();
    }

    public CityListAdapter(Context context, List<City> data) {
        super(context, data);
        mForecastHashMap = new HashMap<>();
    }

    @Override
    public CityViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        City data = mData.get(position);
        holder.onBindView(mContext, data);

        String key = data.getKey();
        if (mForecastHashMap.containsKey(key)) {
            holder.onHourlyForecastLoaded(mContext, mForecastHashMap.get(key));
        }
    }

    public void updateWeather(KeyValuePair<String, HourlyForecast> data) {
        String key = data.getKey();
        mForecastHashMap.put(key, data.getValue());

        for (City city : mData) {
            if (city.getKey().equals(key)) {
                int index = mData.indexOf(city);
                notifyItemChanged(index);
                break;
            }
        }
    }
}
