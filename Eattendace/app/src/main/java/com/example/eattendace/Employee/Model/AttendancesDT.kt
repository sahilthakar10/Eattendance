package com.example.eattendace.Employee.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttendancesDT(

    @field:SerializedName("_id")
	val id: Int? = null,

    var attend: String? = null,
    val date: String? = null,
    val createdAt: String? = null,
    val employeeCode: String? = null
)