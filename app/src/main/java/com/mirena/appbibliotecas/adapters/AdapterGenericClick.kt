package com.mirena.appbibliotecas.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class AdapterGenericClick <T : Any, VM : ViewDataBinding>(
    @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericRecyclerBindingInterface<VM, T>,
    private val clickListener: GenericClickListener<T>? = null
) :
    ListAdapter<T, AdapterGenericClick.GenericRecyclerViewHolder>(GenericDiffUtilCallback()
    ) {
    var selectionTracker: SelectionTracker<Long>? = null

    class GenericRecyclerViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        fun <T : Any, VM : ViewDataBinding> bind(
            item: T,
            bindingInterface: GenericRecyclerBindingInterface<VM, T>,
            clickListener: GenericClickListener<T>?,
            position: Int,
            b: Boolean
        ) {
            bindingInterface.bindData(binding as VM, item, clickListener, position)
            itemView.isActivated = b
        }

        fun getItemDetails() = object :
            ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition() = bindingAdapterPosition
            override fun getSelectionKey() = itemId
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutID,
            parent,
            false
        )
        return GenericRecyclerViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBindViewHolder(holder: GenericRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
            bindingInterface,
            clickListener,
            position,
            selectionTracker?.isSelected(position.toLong()) ?: false
        )
    }

}

class GenericDiffUtilCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.toString() == newItem.toString()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

}

interface GenericRecyclerBindingInterface<VM : ViewDataBinding, T : Any> {
    fun bindData(binder: VM, model: T, clickListener: GenericClickListener<T>?, position: Int)
}

/**class GenericClickListener<T : Any>(private val clickListener: (T) -> Unit) {
  fun onClick(data: T) = clickListener(data)
}*/
