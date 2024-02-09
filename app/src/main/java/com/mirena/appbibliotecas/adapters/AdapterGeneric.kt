package com.mirena.appbibliotecas.adapters

import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class AdapterGeneric<T : Any>(
    private val dataSet: List<T>,
    @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericInterface<T>,
    private val clickListener: GenericClickListener<T>? = null

    ): RecyclerView.Adapter<AdapterGeneric.ViewHolder>() {


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun<T: Any> bind(
            item: T,
            bindingInterface: GenericInterface<T>
        ) = bindingInterface.bindData(item, view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutID, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = dataSet[position]

        holder.bind(item, bindingInterface)

    }

    override fun getItemCount(): Int =
       dataSet.size
}

class GenericClickListener<T : Any>(private val clickListener: (T) -> Unit) {
    fun onClick(data: T) = clickListener(data)
}
