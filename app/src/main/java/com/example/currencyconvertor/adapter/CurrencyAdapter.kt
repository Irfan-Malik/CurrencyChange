package com.example.currencyconvertor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertor.databinding.CurrencyItemLayoutBinding
import com.example.currencyconvertor.remote.model.currency_rates.CurrencyItem

class CurrencyAdapter(
    private val context: Context,
    private val list: List<CurrencyItem>
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding =
            CurrencyItemLayoutBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(viewHolder, i)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewHolder: ViewHolder, position: Int) {
            with(viewHolder.binding) {
                with(list[position]) {
                    txtCurrency.text = key
                    value.toString().let {
                        price.text = it
                    }
                }
            }
        }
    }
}