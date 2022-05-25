package com.yoenas.kisah25nabi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoenas.kisah25nabi.data.KisahResponse
import com.yoenas.kisah25nabi.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {

    var kisahResponse = MutableLiveData<List<KisahResponse>>()
    var isError = MutableLiveData<Throwable>()
    var isLoading = MutableLiveData<Boolean>()

    private fun getKisahNabi(responseHandler: (List<KisahResponse>) -> Unit, errorHandler: (Throwable) -> Unit) {
        ApiClient.getApiService().getKisahNabi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getDataForView() {
        isLoading.value = true

        getKisahNabi({
            isLoading.value = false
            kisahResponse.postValue(it)
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}