package ua.dp.michaellang.weather.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.adapter.CountryListAdapter;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.presenter.CountryListPresenter;
import ua.dp.michaellang.weather.presenter.CountryPresenterImpl;
import ua.dp.michaellang.weather.view.CountryListView;

import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryListDialog extends DialogFragment
        implements CountryListView, SearchView.OnQueryTextListener {

    public static final String EXTRA_COUNTRY = "ua.dp.michaellang.wether.ui.fragment.EXTRA_COUNTRY";
    public static final String EXTRA_ERROR_MESSAGE = "ua.dp.michaellang.wether.ui.fragment.EXTRA_ERROR_MESSAGE";

    @BindView(R.id.dialog_country_list_progress) ProgressBar mProgressBar;
    @BindView(R.id.dialog_country_list_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.dialog_country_list_search) SearchView mSearchView;

    private CountryListAdapter mAdapter;

    private List<Region> mData;
    private CountryListPresenter mPresenter;

    private Consumer<Region> mItemSelectedConsumer = new Consumer<Region>() {
        @Override
        public void accept(Region country) throws Exception {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_COUNTRY, country);
            int resultCode = Activity.RESULT_OK;

            sendResult(intent, resultCode);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CountryListAdapter(getContext(), mItemSelectedConsumer);
        mPresenter = new CountryPresenterImpl(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
        mPresenter.loadCountryList();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_country_list_title)
                .setView(getDialogView())
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
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
                .filter(new Predicate<Region>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull Region region) throws Exception {
                        return region.getLocalizedName()
                                .toLowerCase()
                                .contains(query.toLowerCase());
                    }
                })
                .toList()
                .subscribe(new Consumer<List<Region>>() {
                    @Override
                    public void accept(List<Region> regions) throws Exception {
                        mAdapter.setData(regions);
                        mAdapter.notifyDataSetChanged();
                    }
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
