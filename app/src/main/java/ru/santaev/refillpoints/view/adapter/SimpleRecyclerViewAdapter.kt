package ru.santaev.refillpoints.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class SimpleRecyclerViewAdapter<T, B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val binder: B.(T, ViewHolder<B>) -> Unit,
    private val viewCreator: ((ViewHolder<B>) -> Unit)? = null
) : RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder<B>>() {

    var items: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
        items?.get(position)?.let { binder(holder.binding, it, holder) }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResId,
            parent,
            false
        )
        return ViewHolder(binding.root, binding).also { viewCreator?.invoke(it) }
    }

    class ViewHolder<B>(itemView: View, val binding: B) : RecyclerView.ViewHolder(itemView)
}
