package ua.dp.michaellang.weather.presentation.ui.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import ua.dp.michaellang.weather.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Inject Navigator mNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setToolbar(@IdRes int toolbarId, @Nullable String title) {
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        if (title != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setToolbar(@IdRes int toolbarId, @StringRes int titleId, Object... args) {
        String title = getString(titleId, args);
        setToolbar(toolbarId, title);
    }

    protected final void showBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
