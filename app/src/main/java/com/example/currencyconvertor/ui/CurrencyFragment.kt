package com.example.currencyconvertor.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currencyconvertor.R
import com.example.currencyconvertor.adapter.CurrencyAdapter
import com.example.currencyconvertor.databinding.CurrencyLayoutBinding
import com.example.currencyconvertor.remote.model.currency_rates.CurrencyItem
import com.example.currencyconvertor.util.Resource
import com.example.currencyconvertor.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrencyFragment : Fragment() {
    private val viewModel: CurrencyViewModel by viewModels()
    var TAG = "CurrencyFragment"
    lateinit var binding: CurrencyLayoutBinding
    val list: List<CurrencyItem> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrencyLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrencies()
        viewModel.getCurrenciesRates()
        callObserver()
        setValues()
    }

    private fun callObserver() {
        viewModel.currenciesList.observe(viewLifecycleOwner) { response ->
            updateCurrenciesAdapter(response)
        }

        viewModel.currenciesLatestRatesLiveData.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.e(TAG, "Invalid- No status Found")
                }
            }
        }

        viewModel.selectedCountryCurrency.observe(viewLifecycleOwner) {
            binding.selectedPrice.text = getString(R.string.selected) + " = " + it
        }
    }

    fun setValues() {
        binding.inputCurrency.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                val input = binding.inputCurrency.text.toString()
                if (input.isNotEmpty()) {
                    setAdapter(
                        viewModel.getConvertedCurrency(
                            input.toDouble(),
                            binding.currencySpinner.selectedItem as String
                        )
                    )
                }
            }
        })
    }

    private fun updateCurrenciesAdapter(keys: List<String>) {
        val spinnerCinemaAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item, keys
        )
        spinnerCinemaAdapter.setDropDownViewResource(R.layout.row_spinner_layout)
        binding.currencySpinner.adapter = spinnerCinemaAdapter
        binding.currencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem =
                        parent.getItemAtPosition(position).toString()
                    val input = binding.inputCurrency.text.toString()
                    if (!input.isNullOrEmpty()) {
                        setAdapter(viewModel.getConvertedCurrency(input.toDouble(), selectedItem))
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

    }

    private fun setAdapter(list: List<CurrencyItem>) {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = CurrencyAdapter(requireContext(), list)
        binding.progressBar.visibility = View.GONE
    }


}