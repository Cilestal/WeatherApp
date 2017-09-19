package ua.dp.michaellang.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.listener.OnCountrySelectedListener;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.ui.viewholder.CountryViewHolder;

import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryListAdapter extends BaseAdapter<Region, CountryViewHolder> {
    private OnCountrySelectedListener mListener;

    public CountryListAdapter(Context context, OnCountrySelectedListener listener) {
        super(context);
        mListener = listener;
    }

    public CountryListAdapter(Context context, List<Region> data, OnCountrySelectedListener listener) {
        super(context, data);
        mListener = listener;
    }

    @Override
    public CountryViewHolder getViewHolder(LayoutInflater inflater,
            ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view, mListener);
    }
}
