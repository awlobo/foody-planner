package com.awlobo.foodyplanner.core

import androidx.annotation.LayoutRes

class SimpleRecyclerAdapter<Data : Any>(
    private val data: MutableList<Data>,
    @LayoutRes layoutID: Int,
    private val onBindView: BaseViewHolder<Data>.(data: Data) -> Unit
) : BaseRecyclerAdapter<Data>(data) {

    override val layoutItemId: Int = layoutID

    override fun onBindViewHolder(holder: BaseViewHolder<Data>, position: Int) {
        holder.onBindView(dataList[position])

    }

}