package ua.dp.michaellang.weather.ui.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;

import java.util.Date;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class HourlyWeatherViewHolder extends BaseViewHolder<HourlyForecast> {
    @BindView(R.id.item_hourly_weather_clock) TextView mClockTextView;
    @BindView(R.id.item_hourly_weather_humidity) TextView mHumidityTextView;
    @BindView(R.id.item_hourly_weather_temperature) TextView mTemperatureTextView;
    @BindView(R.id.item_hourly_weather_wind) TextView mWindTextView;

    public HourlyWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(Context context, HourlyForecast data) {
        long epochDateTime = data.getEpochDateTime();
        Date date = new Date(epochDateTime * 1000);
        String timeStr = context.getString(R.string.time, date);
        mClockTextView.setText(timeStr);

        String humidityStr = context.getString(R.string.humidity, data.getRelativeHumidity());
        mHumidityTextView.setText(humidityStr);

        double windSpeed = data.getWind().getSpeed().getValue();
        String windStr = context.getString(R.string.wind, windSpeed);
        mWindTextView.setText(windStr);

        double temperature = data.getTemperature().getValue();
        String tempStr = context.getString(R.string.temperature, temperature);
        mTemperatureTextView.setText(tempStr);
    }
}
