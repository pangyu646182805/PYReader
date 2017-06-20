package com.neuroandroid.pyreader.adapter.base;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/6/15.
 */

public interface CURD<T> {
    void add(T item);

    void add(int position, T item);

    void addAll(List<T> items);

    void addAll(int position, List<T> items);

    void remove(T item);

    void remove(int position);

    void removeAll(List<T> items);

    void retainAll(List<T> items);

    void set(T oldItem, T newItem);

    void set(int position, T item);

    void replaceAll(List<T> items);

    void clear();
}
