package com.example.currencyconvertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyconvertor.databinding.ActivityMainBinding
import com.example.currencyconvertor.ui.CurrencyFragment
import com.example.currencyconvertor.util.FragmentStack
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentStack.addFragmentToContainer(R.id.container,
        supportFragmentManager,CurrencyFragment(),"CurrencyFragment")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}