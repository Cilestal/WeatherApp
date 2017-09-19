package ua.dp.michaellang.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ua.dp.michaellang.weather.ui.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public abstract class BaseAdapter<T, V extends BaseViewHolder<T>>
        extends RecyclerView.Adapter<V> {

    protected Context mContext;
    protected List<T> mData;

    public BaseAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public BaseAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return getViewHolder(inflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.onBindView(mContext, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract V getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public void addData(Collection<T> data) {
        mData.addAll(data);
    }

    public void addElement(T data){
        mData.add(data);
    }

    public void clear() {
        mData.clear();
    }

    public void setData(Collection<T> data) {
        mData = new ArrayList<>(data);
    }
}
