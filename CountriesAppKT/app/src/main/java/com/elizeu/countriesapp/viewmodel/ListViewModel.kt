package com.elizeu.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elizeu.countriesapp.model.CountriesService
import com.elizeu.countriesapp.model.CountryModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel: ViewModel() {

    //estes são atributos observaveis (observable) do design pattern Observer
    //O Observer (observador) esta na MainActivity no metodo observerViewModel
    var countries: MutableLiveData<List<CountryModel>> = MutableLiveData()
    var countryLoadError: MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    /*
    * Em Kotlin, quando você usa o object para criar um singleton,
    * ele é gerenciado pela própria linguagem
    * e não precisa explicitamente de um disposable como em Java.
    * No entanto, se o seu singleton está fazendo uso de observáveis RxJava,
    * como Single ou Observable, é uma boa prática gerenciar
    * os Disposable para evitar vazamentos de memória.
    * */

    //Chamado na MainActivity.kt no onCreate para buscar os dados e atualizar a tela
    fun refresh(){
        fetchCountries()
    }



    private val disposable = CompositeDisposable()

    private fun fetchCountries(){
       // MockDeDadosParaTestAntesDaApi() //para testes sem uso de API
        loading.value = true //ativa o loading progressBar

        /*
        * 1.CountriesService.getCountries(): Inicia uma chamada de rede para buscar a lista de países.
        * 2. .subscribeOn(Schedulers.newThread()): Indica que a chamada de rede deve ser executada
        * em uma nova thread separada da thread principal. Isso é feito para não bloquear
        * a thread principal, que é responsável pela interface do usuário.
        * 3. .observeOn(AndroidSchedulers.mainThread()): Especifica que os resultados da chamada
        * de rede devem ser observados e manipulados nathread principal do Android.
        * Isso é necessário porque qualquer atualização na interface do usuário precisa
        * ser feita na thread principal.
        * 4. .subscribeWith(...): Inicia a subscrição
        * e fornece um DisposableSingleObserver, que é um tipo de observador
        * que espera receber um único resultado ou um erro.
        * */



        disposable.add(
            CountriesService.getCountries()//chamada da api
                .subscribeOn(Schedulers.newThread()) //coloca a chamda de api em uma nova thread na memória
                .observeOn(AndroidSchedulers.mainThread()) //gerencia a comunicação na thread principal
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>() {

                    override fun onSuccess(countryModels: List<CountryModel>) {
                        // Código de sucesso aqui
                        countries.postValue(countryModels) //atualiza os dados (isto ativa o observer lá da mainActivity e chama eventos)
                        countryLoadError.value = false //oculta a mensagem de erro (ativa o observer também)
                        loading.value = false //oculta o loading progressBar (observer também)
                    }

                    override fun onError(e: Throwable) {
                        // Código de erro aqui
                        countryLoadError.value = true //mostra mensagem de erro
                        loading.value = false //oculta o loading progressBar
                        e.printStackTrace() //imprime o StackTrace no output (pode ser visto no logcat)
                    }
                })
        )



    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear() //limpa da memória a instância com os dados da api
    }


    /*
    * O código abaixo foi provisório para testes
    * antes de chamar a api com os dados reais
    * */
    private fun MockDeDadosParaTestAntesDaApi() {
        loading.value = true
        val country1 = CountryModel("Albania", "Tirana", "")
        val country2 = CountryModel("Brazil", "Brasília", "")
        val country3 = CountryModel("Czechia", "Praha", "")

        val list: List<CountryModel> = listOf(country1, country2, country3)

        countries.value = list
        countryLoadError.value = false

        loading.value = false
    }
}