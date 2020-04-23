package com.example.eattendace.Company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eattendace.NetworkManager.ApiRepository
import com.example.eattendace.NetworkManager.NetworkResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CompanyViewModel  : ViewModel(){

    var response: MutableLiveData<NetworkResponse>? = MutableLiveData()

    fun signIn(username : String , password : String){

        ApiRepository.login(username , password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<AuthModelDTO>() {
                override fun onSuccess(model: AuthModelDTO) {

                    response?.postValue(NetworkResponse.SUCCESS.auth(model))
                }

                override fun onError(e: Throwable) {
                    response?.postValue(NetworkResponse.ERROR(e))
                }
            })
    }
    fun signUp(username : String , password : String , companyname : String){

        ApiRepository.signup(username , password , companyname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<AuthModelDTO>() {
                override fun onSuccess(model: AuthModelDTO) {
                    response?.postValue(NetworkResponse.SUCCESS.auth(model))
                }

                override fun onError(e: Throwable) {
                    response?.postValue(NetworkResponse.ERROR(e))
                }
            })
    }

}