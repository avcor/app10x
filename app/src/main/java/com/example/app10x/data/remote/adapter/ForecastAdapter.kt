package com.example.app10x.data.remote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app10x.convertKelvinToCelsiusString
import com.example.app10x.data.remote.model.ForecastPair
import com.example.app10x.databinding.ForecastCardBinding
import javax.inject.Inject

class ForecastAdapter @Inject constructor() : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var list = listOf<ForecastPair>()
    override fun getItemCount() = list.size
    inner class ViewHolder(val binding: ForecastCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            list[position].let { v ->
                degreeTextView.text = "${v.temp?.convertKelvinToCelsiusString()}  C"
                dayTextView.text = v.date?.dayOfWeek?.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ForecastCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateList(l: List<ForecastPair>) {
        list = l
        notifyDataSetChanged()
    }

}
