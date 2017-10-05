package ua.dp.michaellang.weather.presentation.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.entity.Location.Details;
import ua.dp.michaellang.weather.presentation.ui.activity.WeatherDetailsActivity;
import ua.dp.michaellang.weather.presentation.ui.base.BaseAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseViewHolder;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public class CityListAdapter extends BaseAdapter<City, CityListAdapter.CityViewHolder> {
    private HashMap<String, HourlyForecast> mForecastHashMap;
    private AssetsUtils mAssetsUtils;

    @Inject
    public CityListAdapter(Context context, AssetsUtils assetsUtils) {
        super(context);
        mForecastHashMap = new HashMap<>();
        mAssetsUtils = assetsUtils;
    }

    @Override
    public CityViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view, mAssetsUtils);
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

    public void updateWeather(Pair<String, HourlyForecast> data) {
        String key = data.first;
        mForecastHashMap.put(key, data.second);

        for (City city : mData) {
            if (city.getKey().equals(key)) {
                int index = mData.indexOf(city);
                notifyItemChanged(index);
                break;
            }
        }
    }

    public static class CityViewHolder extends BaseViewHolder<City> {
        private AssetsUtils mAssetsUtils;

        @BindView(R.id.item_city_name_tv) TextView mNameTextView;
        @BindView(R.id.item_city_population_tv) TextView mPopulationTextView;
        @BindView(R.id.item_city_temperature) TextView mTemperatureTextView;
        @BindView(R.id.item_city_image) ImageView mImageView;

        public CityViewHolder(View itemView, AssetsUtils assetsUtils) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAssetsUtils = assetsUtils;
        }

        @Override
        public void onBindView(final Context context, final City data) {
            final String cityName = data.getLocalizedName();
            final String areaName = data.getAdministrativeArea().getLocalizedName();
            String name = context.getString(R.string.city_name, cityName, areaName);
            mNameTextView.setText(name);

            Details details = data.getDetails();
            if (details != null) {
                int population = details.getPopulation();
                if (population > 0) {
                    String populationStr = context.getString(R.string.city_population, population);
                    mPopulationTextView.setText(populationStr);
                }
            } else {
                mPopulationTextView.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(v -> {
                WeatherDetailsActivity.start(context, data.getLocalizedName(), data.getKey());
            });
        }

        public void onHourlyForecastLoaded(Context context, HourlyForecast forecast) {
            int weatherIcon = forecast.getWeatherIcon();
            //ImageUtils.loadWeatherIcon(context, mImageView, weatherIcon);
            Drawable drawable = mAssetsUtils.getWeatherIcon(context, weatherIcon);
            mImageView.setImageDrawable(drawable);


            double value = forecast.getTemperature().getValue();
            String temperatureStr = context.getString(R.string.temperature, value);
            mTemperatureTextView.setText(temperatureStr);
        }
    }

}
