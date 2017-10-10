package ua.dp.michaellang.weather.presentation.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.presentation.presenter.CountryListPresenter;
import ua.dp.michaellang.weather.presentation.ui.adapter.CountryListAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseDialogFragment;
import ua.dp.michaellang.weather.presentation.view.CountryListView;

import javax.inject.Inject;
import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryListDialog extends BaseDialogFragment
        implements CountryListView, SearchView.OnQueryTextListener {

    public static final String EXTRA_COUNTRY = "ua.dp.michaellang.wether.ui.fragment.EXTRA_COUNTRY";
    public static final String EXTRA_ERROR_MESSAGE = "ua.dp.michaellang.wether.ui.fragment.EXTRA_ERROR_MESSAGE";

    @BindView(R.id.dialog_country_list_progress) ProgressBar mProgressBar;
    @BindView(R.id.dialog_country_list_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.dialog_country_list_search) SearchView mSearchView;

    @Inject CountryListPresenter mPresenter;
    @Inject CountryListAdapter mAdapter;

    private List<Region> mData;

    private Consumer<Region> mItemSelectedConsumer = country -> {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_COUNTRY, country);
        int resultCode = Activity.RESULT_OK;

        sendResult(intent, resultCode);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter.setItemSelectedConsumer(mItemSelectedConsumer);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
        mPresenter.loadCountryList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_country_list_title)
                .setView(getDialogView())
                .setNegativeButton(R.string.button_cancel, (dialog, which) -> dismiss())
                .create();

    }

    @OnClick(R.id.dialog_country_list_search)
    void onSearchViewClick(SearchView searchView) {
        searchView.setIconified(false);
    }

    @NonNull
    private View getDialogView() {
        View view = View.inflate(getContext(), R.layout.dialog_country_list, null);
        ButterKnife.bind(this, view);

        mSearchView.setOnQueryTextListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCountryListLoaded(List<Region> data) {
        if (data == null) {
            return;
        }
        mData = data;
        mProgressBar.setVisibility(View.GONE);
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(int errorMessage) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ERROR_MESSAGE, errorMessage);
        int resultCode = Activity.RESULT_CANCELED;
        sendResult(intent, resultCode);
        dismiss();
    }

    private boolean filterData(final String query) {
        if (mData == null) {
            return false;
        }

        Observable.fromIterable(mData)
                .filter(region -> region.getLocalizedName()
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .toList()
                .subscribe(regions -> {
                    mAdapter.setData(regions);
                    mAdapter.notifyDataSetChanged();
                });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        return filterData(query);
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        return filterData(newText);
    }

    private void sendResult(Intent intent, int resultCode) {
        int requestCode = getTargetRequestCode();
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(requestCode, resultCode, intent);
        }

        if (resultCode == Activity.RESULT_OK) {
            dismiss();
        }
    }

    public static Region getCountry(Intent intent) {
        return (Region) intent.getParcelableExtra(EXTRA_COUNTRY);
    }

    public static int getErrorMessage(Intent intent) {
        return intent.getIntExtra(EXTRA_ERROR_MESSAGE, 0);
    }

    public static CountryListDialog newInstance() {
        return new CountryListDialog();
    }
}
