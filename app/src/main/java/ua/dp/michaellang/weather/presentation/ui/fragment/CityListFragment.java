package ua.dp.michaellang.weather.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.presentation.inject.component.DaggerCityListComponent;
import ua.dp.michaellang.weather.presentation.inject.module.CityListModule;
import ua.dp.michaellang.weather.presentation.presenter.CityListPresenter;
import ua.dp.michaellang.weather.presentation.ui.adapter.CityListAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseFragment;
import ua.dp.michaellang.weather.presentation.view.CityListView;

import javax.inject.Inject;
import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CityListFragment extends BaseFragment
        implements CityListView, SearchView.OnQueryTextListener {
    public static final String ARG_COUNTRY_KEY = "ARG_COUNTRY_KEY";

    @Inject CityListAdapter mAdapter;
    @Inject CityListPresenter mPresenter;

    @BindView(R.id.fragment_city_list_search_view) SearchView mSearchView;
    @BindView(R.id.fragment_city_list_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_city_list_progress_bar) ProgressBar mProgressBar;

    private String mCountryId;
    private List<City> mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.onStart();
    }

    @Override
    protected void inject() throws IllegalStateException {
        DaggerCityListComponent.builder()
                .appComponent(getApplicationComponent())
                .fragmentModule(getFragmentModule())
                .cityListModule(new CityListModule(this, mCountryId))
                .build()
                .inject(this);
    }

    @Override
    public void loadArgs() {
        if (getArguments() != null) {
            mCountryId = getArguments().getString(ARG_COUNTRY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setOnQueryTextListener(this);
        mPresenter.loadCityList();
    }

    @OnClick(R.id.fragment_city_list_search_view)
    void onSearchViewClick() {
        mSearchView.setIconified(false);
    }

    private void showProgress(boolean flag) {
        if (flag) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCityListLoaded(List<City> data) {
        if (data == null) {
            return;
        }
        Timber.d("%d cities loaded.", data.size());
        mData = data;

        //загружаем погоду по городам
        mPresenter.loadCitiesWeather();

        mSearchView.setQuery("", false);
        showProgress(false);
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityWeatherLoaded(Pair<String, HourlyForecast> data) {
        mAdapter.updateWeather(data);
    }

    @Override
    public void onError(@StringRes int stringResId) {
        Snackbar.make(mRecyclerView, stringResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> {
                    mPresenter.onStart();
                    mPresenter.loadCityList();
                })
                .show();
        showProgress(false);
    }

    private boolean filterData(final String query) {
        if (mData == null) {
            return false;
        }

        Observable.fromIterable(mData)
                .filter(city -> city.getLocalizedName()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .toList()
                .subscribe(cities -> {
                    mAdapter.setData(cities);
                    mAdapter.notifyDataSetChanged();
                });

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return filterData(query);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return filterData(newText);
    }

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    public static CityListFragment newInstance(String countryId) {
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY_KEY, countryId);

        CityListFragment fragment = new CityListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
