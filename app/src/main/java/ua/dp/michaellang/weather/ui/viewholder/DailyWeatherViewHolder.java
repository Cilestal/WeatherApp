package ua.dp.michaellang.weather.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.network.model.Forecast.DayNight;
import ua.dp.michaellang.weather.network.model.Forecast.MinMax;
import ua.dp.michaellang.weather.utils.AssetsUtils;

import java.util.Date;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class DailyWeatherViewHolder extends BaseViewHolder<DailyForecast> {
    @BindView(R.id.item_daily_weather_day) TextView mDayTextView;
    @BindView(R.id.item_daily_weather_date) TextView mDateTextView;
    @BindView(R.id.item_daily_weather_image) ImageView mImageView;
    @BindView(R.id.item_daily_weather_temperature_max) TextView mTemperatureMaxTextView;
    @BindView(R.id.item_daily_weather_temperature_min) TextView mTemperatureMinTextView;
    @BindView(R.id.item_daily_weather_wind) TextView mWindTextView;

    public DailyWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBindView(Context context, DailyForecast data) {
        long mils = data.getEpochDate() * 1000;
        Date date = new Date(mils);
        DayNight day = data.getDay();

        MinMax temperature = data.getTemperature();
        double maxValue = temperature.getMaximum().getValue();
        double minValue = temperature.getMinimum().getValue();

        double windSpeedValue = data.getDay().getWind().getSpeed().getValue();

        String dayStr = context.getString(R.string.day_of_week, date);
        mDayTextView.setText(dayStr);

        String dateStr = context.getString(R.string.abbreviated_date, date);
        mDateTextView.setText(dateStr);

        //ImageUtils.loadWeatherIcon(context, mImageView, day.getIcon());
        Drawable drawable = AssetsUtils.getWeatherIcon(context, day.getIcon());
        mImageView.setImageDrawable(drawable);

        String maxTempStr = context.getString(R.string.temperature, maxValue);
        String minTempStr = context.getString(R.string.temperature, minValue);

        mTemperatureMaxTextView.setText(maxTempStr);
        mTemperatureMinTextView.setText(minTempStr);

        String windSpeedStr = context.getString(R.string.wind, windSpeedValue);
        mWindTextView.setText(windSpeedStr);
    }
}
