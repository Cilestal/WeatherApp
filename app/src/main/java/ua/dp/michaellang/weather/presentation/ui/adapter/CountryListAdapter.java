package ua.dp.michaellang.weather.presentation.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.presentation.ui.base.BaseAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseViewHolder;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Inject;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryListAdapter extends BaseAdapter<Region, CountryListAdapter.CountryViewHolder> {
    private Consumer<Region> mItemSelectedConsumer;
    private AssetsUtils mAssetsUtil;

    @Inject
    public CountryListAdapter(Context context, AssetsUtils assetsUtil) {
        super(context);
        mAssetsUtil = assetsUtil;
    }

    public void setItemSelectedConsumer(Consumer<Region> itemSelectedConsumer) {
        mItemSelectedConsumer = itemSelectedConsumer;
    }

    @Override
    public CountryViewHolder getViewHolder(LayoutInflater inflater,
            ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view, mItemSelectedConsumer, mAssetsUtil);
    }

    public static class CountryViewHolder extends BaseViewHolder<Region> {
        @BindView(R.id.item_country_title) TextView mTextView;
        @BindView(R.id.item_country_image) ImageView mImageView;

        private final PublishSubject<Region> mPublishSubject = PublishSubject.create();
        private final AssetsUtils mAssetsUtils;

        public CountryViewHolder(View itemView, Consumer<Region> consumer, AssetsUtils assetsUtils) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mPublishSubject.subscribe(consumer);
            mAssetsUtils = assetsUtils;
        }

        @Override
        public void onBindView(Context context, final Region country) {
            mTextView.setText(country.getLocalizedName());

            Drawable drawable = mAssetsUtils.getFlag(context, country.getId());
            mImageView.setImageDrawable(drawable);

            itemView.setOnClickListener(v -> {
                if (mPublishSubject != null) {
                    mPublishSubject.onNext(country);
                }
            });
        }


    }
}
