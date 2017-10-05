package ua.dp.michaellang.weather.presentation.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.presentation.inject.HasComponent;
import ua.dp.michaellang.weather.presentation.inject.component.DaggerMainActivityComponent;
import ua.dp.michaellang.weather.presentation.inject.component.MainActivityComponent;
import ua.dp.michaellang.weather.presentation.ui.adapter.SectionsPagerAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity
        implements HasComponent<MainActivityComponent> {
    private MainActivityComponent mComponent;

    @Inject SectionsPagerAdapter mSectionsPagerAdapter;

    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.container) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initializeInjector();

        setToolbar(R.id.toolbar, null);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initializeInjector() {
        mComponent = DaggerMainActivityComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        mComponent.inject(this);
    }

    @Override
    public MainActivityComponent getComponent() {
        return mComponent;
    }
}
