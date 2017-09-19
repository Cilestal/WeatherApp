package ua.dp.michaellang.weather.ui.activity;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class BaseActivity extends AppCompatActivity {

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

    protected final void showBackButton(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
