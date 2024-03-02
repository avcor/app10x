package com.example.app10x.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
                    WeatherServiceResponse.Failure -> showSnackBar("Something went wrong") {
                        weatherViewModel.getWeatherData()
                    }

                    WeatherServiceResponse.Loading -> binding.progressCircular.visibility =
                        View.VISIBLE

                    WeatherServiceResponse.NoData -> showSnackBar("No Data") {
                        weatherViewModel.getWeatherData()
                    }

                    is WeatherServiceResponse.Success -> setWeatherDataOnScreen(it)
                }
            }
        }
    }

    private fun setWeatherDataOnScreen(res: WeatherServiceResponse.Success) {
        binding.apply {
            todayLocation.text = weatherViewModel.city
            currentWeatherData = "${res.currentWeather?.getCelsius()} \u2103"
            res.forecastWeather?.get4DayAverage()?.let { forecastAdapter.updateList(it) }
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun showSnackBar(txt: String, fnAction: (() -> Unit)?) {
        binding.progressCircular.visibility = View.GONE
        Snackbar.make(binding.root, txt, Snackbar.LENGTH_INDEFINITE).setAction("RETRY") {
            fnAction?.invoke()
        }.show()
    }

}