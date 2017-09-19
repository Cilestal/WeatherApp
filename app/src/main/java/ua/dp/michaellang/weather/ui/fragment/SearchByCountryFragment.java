package ua.dp.michaellang.weather.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.utils.AssetsUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
public class SearchByCountryFragment extends Fragment {
    private static final int REQUEST_COUNTRY = 0;
    private static final String DIALOG_COUNTRY_LIST = "DIALOG_COUNTRY_LIST";

    @BindView(R.id.fragment_search_by_country_content) View mContentView;
    @BindView(R.id.fragment_search_by_country_button) Button mButton;
    @BindView(R.id.fragment_search_by_country_image) ImageView mImageView;

    private CountryListDialog mCountryListDialog;
    private Region mCountry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_country, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragment_search_by_country_button)
    public void onSearchByCountryButtonClick() {
        if (mCountryListDialog != null && mCountryListDialog.isVisible()) {
            return;
        }

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mCountryListDialog = CountryListDialog.newInstance();
        mCountryListDialog.setTargetFragment(SearchByCountryFragment.this, REQUEST_COUNTRY);
        mCountryListDialog.show(fragmentManager, DIALOG_COUNTRY_LIST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_COUNTRY) {
            if (data != null) {
                if (resultCode != RESULT_OK) {
                    int errorMessage = CountryListDialog.getErrorMessage(data);
                    onError(errorMessage);
                } else {
                    Region country = CountryListDialog.getCountry(data);
                    onDialogItemSelected(country);
                }
            }
        }
    }

    private void onDialogItemSelected(Region country) {
        mCountry = country;
        mButton.setText(mCountry.getLocalizedName());
        //ImageUtils.loadFlagIcon(getContext(), mImageView, country.getId(), true);

        Drawable drawable = AssetsUtils.getFlag(getContext(), country.getId());
        mImageView.setImageDrawable(drawable);

        showFragment();
    }

    private void showFragment() {
        FragmentManager sfm = getActivity().getSupportFragmentManager();

        CityListFragment fragment =
                CityListFragment.newInstance(mCountry.getId());

        sfm.beginTransaction()
                .replace(R.id.fragment_search_by_country_content, fragment)
                .commit();
    }

    public void onError(@StringRes int stringResId) {
        // TODO: 15.09.2017
        Timber.e(getString(stringResId));
        Snackbar.make(mContentView, stringResId, Snackbar.LENGTH_LONG).show();
    }

    public static SearchByCountryFragment newInstance() {
        return new SearchByCountryFragment();
    }
}