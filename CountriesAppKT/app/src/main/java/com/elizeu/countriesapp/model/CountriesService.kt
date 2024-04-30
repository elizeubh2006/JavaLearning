package com.elizeu.countriesapp.model

import com.elizeu.countriesapp.di.ApiModule
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object CountriesService
{
     private val BASE_URL = "https://raw.githubusercontent.com"

    private val api:CountriesApi = Retrofit.Builder() //construtor da instancia do retrofit
        .baseUrl(BASE_URL)//informa a url base onde se encontra o endpoint
        .addConverterFactory(GsonConverterFactory.create()) //instancia a lib que irá converter o json em objeto
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava2 será o adapter que irá tratar o resultado recebido de forma assíncrona como um observable
        .build() //Após construida (builder) a instancia retrofit é gerado o objeto (insntancia da classe)
        .create(CountriesApi::class.java) //cria o proxy e handler que irá interceptar as chamadas e traduzir em chamadas de rede http

    fun getCountries(): Single<List<CountryModel>> {
        return api.getCountries()
    }
}


//    /*  O Gson irá converter os dados recebidos em Single por causa da interface passada CoutriesApi
//     * O Single em conjunto com o RxJava trabalhará como um observable do design pattern observe.
//     * Os dados são recebidos de forma assíncrona e ao ter sucesso ou erro envia uma notificação ao
//     * observer (neste caso quem estiver esperando pelos dados, pois o observable não sabe quem vai usar, apenas notifica a todos)*/

/*
    aqui esta a implementação de "getCountries" da nossa api
* e conforme acima, o retrofit intercepta esta chamada de getCountries
* e converte na chamda a api (para o endpoint do annotation @GET) e realiza as conversões
*/
