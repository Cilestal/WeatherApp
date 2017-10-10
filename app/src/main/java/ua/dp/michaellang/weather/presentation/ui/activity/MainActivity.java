package ua.dp.michaellang.weather.presentation.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.presentation.ui.adapter.SectionsPagerAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector {
    @Inject SectionsPagerAdapter mSectionsPagerAdapter;
    @Inject DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.container) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setToolbar(R.id.toolbar, null);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
}
