package com.example.duangniu000.test2.Adaper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.App;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */

public abstract class AbstractAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    private Activity activity;

    protected List<T> list = new ArrayList();

    public AbstractAdapter(Activity activity) {
        this.activity = activity;
    }

    public AbstractAdapter(Fragment fragment) {
        this(fragment.getActivity());
    }

    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.onBindViewHolder(list.get(position));
    }

    public Activity getActivity() {
        return activity;
    }

    public Context getContext() {
        return activity;
    }


    protected View inflate(int res, ViewGroup p, boolean a) {
        return LayoutInflater.from(activity).inflate(res, p, a);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public void addAll(Collection<T> collection) {
        list.addAll(collection);
    }

    public void add(T t) {
        list.add(t);
    }

    public void add(int index, T t) {
        list.add(index, t);
    }

    public boolean remove(T t) {
        return list.remove(t);
    }

    public T remove(int index) {
        return list.remove(index);
    }


    protected OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, BaseHolder holder);
    }

}
