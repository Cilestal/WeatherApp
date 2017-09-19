package ua.dp.michaellang.weather.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.network.model.Location.Details;
import ua.dp.michaellang.weather.ui.activity.WeatherDetailsActivity;
import ua.dp.michaellang.weather.utils.AssetsUtils;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public class CityViewHolder extends BaseViewHolder<City> {

    @BindView(R.id.item_city_name_tv) TextView mNameTextView;
    @BindView(R.id.item_city_population_tv) TextView mPopulationTextView;
    @BindView(R.id.item_city_temperature) TextView mTemperatureTextView;
    @BindView(R.id.item_city_image) ImageView mImageView;

    public CityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherDetailsActivity.start(context, data.getLocalizedName(), data.getKey());
            }
        });
    }

    public void onHourlyForecastLoaded(Context context, HourlyForecast forecast) {
        int weatherIcon = forecast.getWeatherIcon();
        //ImageUtils.loadWeatherIcon(context, mImageView, weatherIcon);
        Drawable drawable = AssetsUtils.getWeatherIcon(context, weatherIcon);
        mImageView.setImageDrawable(drawable);


        double value = forecast.getTemperature().getValue();
        String temperatureStr = context.getString(R.string.temperature, value);
        mTemperatureTextView.setText(temperatureStr);
    }
}
