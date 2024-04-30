package com.elizeu.countriesapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elizeu.countriesapp.databinding.ItemCountryBinding
import com.elizeu.countriesapp.model.CountryModel


class CountryListAdapter(private var countries: MutableList<CountryModel>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountries(newCountries: List<CountryModel>){
        this.countries.clear()
        this.countries.addAll(newCountries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        //aqui criamos a instancia da view item_country.xml na memória usando inflate
        val bindingViewItemCountry = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CountryViewHolder(bindingViewItemCountry)
    }

    override fun getItemCount(): Int {
        return this.countries.size
    }

    /*
    position é a posição na list de Countries -> countries: MutableList<CountryModel>)
    o método CountryViewHolder esta logo abaixo, onde criandos o metodo bind para popular a view
    onBindViewHolder vai ser chamado pela RecycleView para atualizar os dados , este evento será
    acionado sempre que abrir o app ou dar o refresh com swipereresh
    */
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    //aqui associamos a view item_country.xml com a view root (activity_main.xml) e acessamos seus elementos
    inner class CountryViewHolder(private var binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root){
        private var countryName = binding.name
        private var countryCapital = binding.capital
        private var countryImageView = binding.imageView

        /*  Aqui temos nossa view (itemView -> item_country.xml) e temos country (CountryModel) que são os paises e o bind vai conectar os dois
        aqui vamos pegar os elementos do layout item_country.xml e associar as informações a eles (fun bind)*/
        fun bind( country: CountryModel){
            countryName.text = country.countryName
            countryCapital.text = country.capital

            //a imagem da banderia é associada a nossa ImageView utilizando o Coil da nossa classe Util
            Util.loadImage(countryImageView, country.flag, Util.getProgressDrawable(countryImageView.context))
        }
    }

}