package com.example.eattendace.Employee

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eattendace.Company.AuthModelDTO
import com.example.eattendace.Employee.Model.ResponseDT
import com.example.eattendace.NetworkManager.ApiRepository
import com.example.eattendace.NetworkManager.NetworkResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class EmpViewModel  : ViewModel(){

    var response: MutableLiveData<NetworkResponse>? = MutableLiveData()

    fun empAdd(fullName : String , mobileNo : Int , password : String, eCode : String, dob : String){

        ApiRepository.empAdd(fullName , mobileNo , password , eCode , dob)
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

    fun empCheckIn(password : String?, eCode : String?, fingerPrint : Int){
        ApiRepository.empCheckIn(eCode , password , fingerPrint)
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
    fun empCheckOut(password : String?, eCode : String?, fingerPrint : Int){
        ApiRepository.empCheckOut(eCode , password , fingerPrint)
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

    fun empList(){
        ApiRepository.getEmployee()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<ResponseDT>() {
                override fun onSuccess(listModel: ResponseDT) {
                    response?.postValue(NetworkResponse.SUCCESS.employeeList(listModel))
                }

                override fun onError(e: Throwable) {
                    response?.postValue(NetworkResponse.ERROR(e))
                }
            })
    }

    fun empDelete(ecode : String?){
        ApiRepository.deleteEmp(ecode)
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


    fun empUpdate(ecode : String? ,fullName : String? ,mobileNo : Long? ){
        ApiRepository.empDetailUpdate(ecode ,fullName , mobileNo )
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