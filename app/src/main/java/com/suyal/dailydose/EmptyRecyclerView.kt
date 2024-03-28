package com.suyal.dailydose

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Gaurav Suyal on 28-03-2024.
 */

class EmptyRecyclerView : RecyclerView {
    private var mEmptyView: View? = null

    /**
     * The AdapterDataObserver calls checkIfEmpty() method every time, and it observes
     * an event that changes the content of the adapter
     */
    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    /**
     * Checks if both mEmptyView and adapter are not null.
     * Hide or show mEmptyView depending on the size of the data(item count) in the adapter.
     */
    private fun checkIfEmpty() {
        // If the item count provided by the adapter is equal to zero, make the empty View visible
        // and hide the EmptyRecyclerView.
        // Otherwise, hide the empty View and make the EmptyRecyclerView visible.
        if (mEmptyView != null && adapter != null) {
            val emptyViewVisible = adapter!!.itemCount == 0
            mEmptyView!!.visibility = if (emptyViewVisible) VISIBLE else GONE
            visibility = if (emptyViewVisible) GONE else VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
        checkIfEmpty()
    }

    /**
     * Set an empty view on the EmptyRecyclerView
     * @param emptyView refers to the empty state of the view
     */
    fun setEmptyView(emptyView: View?) {
        mEmptyView = emptyView
        checkIfEmpty()
    }
}
