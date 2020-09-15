package com.awlobo.foodyplanner.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

fun ViewGroup.inflater(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)


fun <T : Any> RecyclerView.setBaseAdapter(
    dataList: MutableList<T>,
    @LayoutRes layoutID: Int,
    onBindView: BaseViewHolder<T>.(data: T) -> Unit
): SimpleRecyclerAdapter<T> {
    val recyclerAdapter = SimpleRecyclerAdapter(dataList, layoutID, onBindView)
    adapter = recyclerAdapter
    this.isSaveEnabled = true
    return recyclerAdapter
}

