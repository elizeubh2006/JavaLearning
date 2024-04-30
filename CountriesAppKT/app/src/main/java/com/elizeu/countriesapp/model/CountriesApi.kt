package com.elizeu.countriesapp.model

import io.reactivex.Single
import retrofit2.http.GET

/*
* A classe Single no código abaixo é usada para representar uma operação assíncrona que pode emitir
* um único valor ou um erro. No contexto do Retrofit e RxJava,
* ela é usada para fazer uma chamada de rede que espera uma resposta única,
* como o carregamento de uma lista de países de um endpoint JSON.
* Quando você chama getCountries(), o Retrofit prepara a chamada de rede para o endpoint especificado
* no @GET. Ao invés de retornar um Call, que é o tipo padrão do Retrofit, ele retorna um Single.
* Isso significa que quando a chamada de rede é feita, você receberá uma List<CountryModel>
se for bem-sucedida, ou um erro se algo der errado durante a chamada de rede. */
interface CountriesApi {
    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<CountryModel>>

}