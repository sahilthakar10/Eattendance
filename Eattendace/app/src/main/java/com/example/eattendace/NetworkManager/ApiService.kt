package com.example.eattendace.NetworkManager

import com.example.eattendace.Company.AuthModelDTO
import com.example.eattendace.Employee.Model.ResponseDT
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {


    @FormUrlEncoded
    @POST("/company/login")
    fun comsignin(@Field("Username") Username: String? , @Field("password") password: String?): Single<AuthModelDTO>

    @FormUrlEncoded
    @POST("/company/reg")
    fun comsignUp(@Field("Username") Username: String? , @Field("password") password: String?, @Field("companyname") companyname: String?): Single<AuthModelDTO>

    @FormUrlEncoded
    @POST("/employee/register")
    fun empAdd(@Field("fullName") fullName: String? ,
                  @Field("mobileNo") mobileNo: Int?,
                  @Field("password") password: String?,
                  @Field("eCode") eCode: String?,
                  @Field("dob") dob: String?): Single<AuthModelDTO>

    @FormUrlEncoded
    @POST("/employee/checkin")
    fun empCheckIn(
                  @Field("eCode") eCode: String?,
                  @Field("password") password: String?,
                  @Field("fingerPrint") fingerPrint: Int?): Single<AuthModelDTO>

    @FormUrlEncoded
    @POST("/employee/checkout")
    fun empCheckOut(
                  @Field("eCode") eCode: String?,
                  @Field("password") password: String?,
                  @Field("fingerPrint") fingerPrint: Int?): Single<AuthModelDTO>

    @GET("/attendance/getAttendance")
    fun getAttendance(): Single<ResponseDT>

    @FormUrlEncoded
    @PATCH("/employee/edit")
    fun empDetailUpdate(@Field("eCode") eCode : String? ,
                @Field("fullName")fullName : String?,
                @Field("mobileNo")mobileNo : Long?): Single<AuthModelDTO>

    @DELETE("/employee/delete/{eCode}")
    fun empDelete(@Path("eCode")eCode: String?): Single<AuthModelDTO>

}