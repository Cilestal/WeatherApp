package ua.dp.michaellang.weather.ui.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.listener.OnCountrySelectedListener;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.utils.AssetsUtils;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryViewHolder extends BaseViewHolder<Region> {
    @BindView(R.id.item_country_title) TextView mTextView;
    @BindView(R.id.item_country_image) ImageView mImageView;

    private OnCountrySelectedListener mListener;

    public CountryViewHolder(View itemView, OnCountrySelectedListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;
    }

    public void onBindView(Context context, final Region country) {
        mTextView.setText(country.getLocalizedName());
        //ImageUtils.loadFlagIcon(context, mImageView, country.getId(), false);

        Drawable drawable = AssetsUtils.getFlag(context, country.getId());
        mImageView.setImageDrawable(drawable);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCountrySelected(country);
                }
            }
        });
    }
}
