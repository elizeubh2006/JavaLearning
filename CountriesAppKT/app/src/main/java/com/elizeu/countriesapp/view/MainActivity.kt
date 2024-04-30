package com.elizeu.countriesapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elizeu.countriesapp.databinding.ActivityMainBinding
import com.elizeu.countriesapp.viewmodel.ListViewModel


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ListViewModel
    private var adapter: CountryListAdapter = CountryListAdapter(mutableListOf())

    //Inicializada no OnCreate
    private lateinit var activityMbinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate transforma a view em uma instancia na memória
        activityMbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMbinding.root)


        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        viewModel.refresh() //Em ListViewModel - busca os dados e atualiza a tela

        val countriesList = activityMbinding.countriesList

        countriesList.layoutManager = LinearLayoutManager(this)
        countriesList.adapter = adapter

        val refreshLayout = activityMbinding.swipeRefreshLayout
        refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observerViewModel()

    }

    //Este é o observador que irá notificar quando o observable (ListViewModel atributos) for alterado
    //Ele irá notificar através do
    // @SuppressLint("NotifyDataSetChanged") no método updateCountries em CountryListAdapter
    //viewModel é o ListViewModel onde esta os atributos observaveis
    private fun observerViewModel() {
        val listError = activityMbinding.listError
        val loadingView = activityMbinding.loadingView
        val countriesList = activityMbinding.countriesList

        viewModel.countries.observe(this) { countryModels ->
            if (countryModels != null) {
                countriesList.visibility = View.VISIBLE
                adapter.updateCountries(countryModels)
            }
        }

        viewModel.countryLoadError.observe(this) { isError ->
            if (isError != null){
                listError.visibility = if(isError) View.VISIBLE else View.GONE
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            if(isLoading != null){
                loadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
                if(isLoading){
                    listError.visibility = View.GONE
                    countriesList.visibility = View.GONE
                }
            }

        }

    }
}
