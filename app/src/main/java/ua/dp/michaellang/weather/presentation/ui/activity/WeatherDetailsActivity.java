package ua.dp.michaellang.weather.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.DailyForecast;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.presentation.presenter.WeatherDetailsPresenter;
import ua.dp.michaellang.weather.presentation.ui.adapter.DailyWeatherAdapter;
import ua.dp.michaellang.weather.presentation.ui.adapter.HourlyWeatherAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseActivity;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;
import ua.dp.michaellang.weather.presentation.view.WeatherDetailsView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Date: 17.09.2017
 *
 * @author Michael Lang
 */
public class WeatherDetailsActivity extends BaseActivity implements WeatherDetailsView {
    public static final String EXTRA_CITY_NAME = "ua.dp.michaellang.weather.ui.activity.EXTRA_CITY_NAME";
    public static final String EXTRA_CITY_KEY = "ua.dp.michaellang.weather.ui.activity.EXTRA_CITY_KEY";

    @BindView(R.id.item_temperature_image) ImageView mTemperatureIV;
    @BindView(R.id.item_temperature_value) TextView mCurrentTempTV;
    @BindView(R.id.item_temperature_max) TextView mTemperatureMaxTV;
    @BindView(R.id.item_temperature_min) TextView mTemperatureMinTV;

    @BindView(R.id.activity_weather_details_fb) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.content_weather_details_content_layout) View mContentLayout;
    @BindView(R.id.content_weather_details_pb) ProgressBar mProgressBar;

    @BindView(R.id.content_weather_details_hourly_info_rv) RecyclerView mHourlyRV;
    @BindView(R.id.content_weather_details_daily_info_rv) RecyclerView mDailyRV;

    @Inject WeatherDetailsPresenter mPresenter;
    @Inject DailyWeatherAdapter mDailyWeatherAdapter;
    @Inject HourlyWeatherAdapter mHourlyWeatherAdapter;
    @Inject AssetsUtils mAssetsUtils;

    private Boolean mIsFavorite;
    private String mCityCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        ButterKnife.bind(this);

        String cityName = getIntent().getStringExtra(EXTRA_CITY_NAME);
        mCityCode = getIntent().getStringExtra(EXTRA_CITY_KEY);

        setToolbar(R.id.toolbar, cityName);

        initRecyclerViews();
        showBackButton();

        startLoading();
    }

    private void initRecyclerViews() {
        mHourlyRV.setLayoutManager(new LinearLayoutManager(this));
        mDailyRV.setLayoutManager(new LinearLayoutManager(this));

        mHourlyRV.setAdapter(mHourlyWeatherAdapter);
        mDailyRV.setAdapter(mDailyWeatherAdapter);
    }

    @Override
    public void onDailyWeatherLoaded(ArrayList<DailyForecast> data) {
        setDayTemperature(data.get(0));

        //отображаем погоду на неделю
        for (int i = 1; i < data.size(); i++) {
            mDailyWeatherAdapter.addElement(data.get(i));
        }

        mDailyWeatherAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHourlyWeatherLoaded(List<HourlyForecast> data) {
        //текущую отображаем, остальные в адаптер
        setCurrentTimeTemperature(data.get(0));
        for (int i = 1; i < data.size(); i++) {
            mHourlyWeatherAdapter.addElement(data.get(i));
        }
        mHourlyWeatherAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadComplete() {
        showProgress(false);
        showContent(true);
    }

    @Override
    public void onAddToFavoriteSuccess() {
        onIsFavoriteChecked(!mIsFavorite);
    }

    @Override
    public void onFavoriteActionFail() {
        onError(R.string.error_add_to_favorite);
    }

    @Override
    public void onIsFavoriteChecked(boolean result) {
        mIsFavorite = result;
        updateFloatingActionButton();
    }

    @Override
    public void onRemoveFromFavoriteSuccess() {
        onIsFavoriteChecked(!mIsFavorite);
    }

    private void updateFloatingActionButton() {
        if (mIsFavorite != null) {
            mFloatingActionButton.setVisibility(View.VISIBLE);

            if (mIsFavorite) {
                mFloatingActionButton.setImageResource(R.drawable.ic_remove_favorite);
            } else {
                mFloatingActionButton.setImageResource(R.drawable.ic_add_favorite);
            }
        }
    }

    @OnClick(R.id.activity_weather_details_fb)
    void setFloatingActionButtonClick() {
        if (mIsFavorite != null) {
            if (mIsFavorite) {
                mPresenter.removeFromFavorite(mCityCode);
            } else {
                mPresenter.addToFavorite(mCityCode);
            }
        }
    }

    @Override
    public void onError(@StringRes int stringResId) {
        Snackbar.make(mContentLayout, stringResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> startLoading())
                .show();
        showProgress(false);
        showContent(false);
    }

    private void setDayTemperature(DailyForecast data) {
        double min = data.getTemperature().getMinimum().getValue();
        double max = data.getTemperature().getMaximum().getValue();

        String minTempStr = getString(R.string.temperature, min);
        String maxTempStr = getString(R.string.temperature, max);

        mTemperatureMaxTV.setText(maxTempStr);
        mTemperatureMinTV.setText(minTempStr);
    }

    private void setCurrentTimeTemperature(HourlyForecast currentTemp) {
        //устанавливаем текущую температуру
        double currentTempValue = currentTemp.getTemperature().getValue();
        mCurrentTempTV.setText(getString(R.string.temperature, currentTempValue));

        //ImageUtils.loadWeatherIcon(this, mTemperatureIV, currentTemp.getWeatherIcon());
        Drawable drawable = mAssetsUtils.getWeatherIcon(this, currentTemp.getWeatherIcon());
        mTemperatureIV.setImageDrawable(drawable);
    }

    private void startLoading() {
        mPresenter.onStart();
        mPresenter.loadWeather(mCityCode);
        mPresenter.checkIsFavorite(mCityCode);
    }

    private void showProgress(boolean flag) {
        if (flag) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(GONE);
        }
    }

    private void showContent(boolean flag) {
        if (flag) {
            mContentLayout.setVisibility(View.VISIBLE);
        } else {
            mContentLayout.setVisibility(GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public static Intent newIntent(Context context, String cityName, String cityKey) {
        Intent intent = new Intent(context, WeatherDetailsActivity.class);
        intent.putExtra(EXTRA_CITY_NAME, cityName);
        intent.putExtra(EXTRA_CITY_KEY, cityKey);
        return intent;
    }

    public static void start(Context context, String cityName, String cityKey) {
        Intent starter = newIntent(context, cityName, cityKey);
        context.startActivity(starter);
    }
}
