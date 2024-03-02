package com.example.app10x.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app10x.R
import com.example.app10x.data.remote.adapter.ForecastAdapter
import com.example.app10x.data.remote.type.WeatherServiceResponse
import com.example.app10x.databinding.ActivityMainBinding
import com.example.app10x.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG = "abcd"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var forecastAdapter: ForecastAdapter
    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.forecastRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = forecastAdapter
        }

        lifecycleScope.launch {
            weatherViewModel.currentWeatherState.collect {
                when (it) {
                    WeatherServiceResponse.Failure -> showRetrySnackBar()
                    WeatherServiceResponse.Loading -> binding.progressCircular.visibility = View.VISIBLE
                    WeatherServiceResponse.NoData -> Toast.makeText(this@MainActivity, "No data", Toast.LENGTH_SHORT).show()
                    is WeatherServiceResponse.Success -> setWeatherDataOnScreen(it)
                }
            }
        }
    }

    private fun setWeatherDataOnScreen(res: WeatherServiceResponse.Success) {
        binding.apply {
            todayLocation.text = weatherViewModel.city
            currentWeatherData = res.currentWeather?.getCelsius()
            res.forecastWeather?.get4DayAverage()?.let { forecastAdapter.updateList(it) }
            progressCircular.visibility = View.GONE
        }
    }

    private fun showRetrySnackBar(){
        Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
            .setAction("RETRY"){
                weatherViewModel.getWeatherData()
            }
            .show()
    }

}