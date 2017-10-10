package ua.dp.michaellang.weather.presentation.ui.adapter;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.presentation.ui.fragment.CityListFragment;
import ua.dp.michaellang.weather.presentation.ui.fragment.SearchByCountryFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SearchByCountryFragment.newInstance();
            case 1:
                return CityListFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.cities);
            case 1:
                return mContext.getString(R.string.bookmarks);
            default:
                return null;
        }
    }
}
