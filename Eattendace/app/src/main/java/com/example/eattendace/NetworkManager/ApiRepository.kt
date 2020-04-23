package com.example.eattendace.NetworkManager

import com.example.eattendace.Company.AuthModelDTO
import com.example.eattendace.Employee.Model.ResponseDT
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {

    private val apiService: ApiService

    init {

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.208:8800")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    fun login(username : String , password : String): Single<AuthModelDTO> {
        return apiService.comsignin(username , password)
    }

    fun signup(username : String , password : String , companyname : String): Single<AuthModelDTO> {
        return apiService.comsignUp(username , password , companyname)
    }

    fun empAdd(fullName : String , mobileNo : Int , password : String, eCode : String, dob : String): Single<AuthModelDTO> {
        return apiService.empAdd(fullName , mobileNo , password , eCode , dob)
    }

    fun empCheckIn(eCode : String?, password : String?,  fingerPrint : Int): Single<AuthModelDTO> {
        return apiService.empCheckIn(eCode , password , fingerPrint )
    }

    fun empCheckOut(eCode : String?, password : String?,  fingerPrint : Int): Single<AuthModelDTO> {
        return apiService.empCheckOut(eCode , password , fingerPrint )
    }

    fun getEmployee(): Single<ResponseDT> {
        return apiService.getAttendance()
    }

    fun deleteEmp(ecode : String?) : Single<AuthModelDTO> {
        return apiService.empDelete(ecode)
    }

    fun empDetailUpdate(ecode : String? ,fullName : String? ,mobileNo : Long? ): Single<AuthModelDTO> {
        return apiService.empDetailUpdate(ecode ,fullName , mobileNo )
    }
}