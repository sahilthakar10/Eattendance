package com.example.eattendace.Employee.Model

import com.google.gson.annotations.SerializedName

data class DataDT(

	@field:SerializedName("_id")
	val id: Int? = null,

	@field:SerializedName("DOB")
	val dOB: String? = null,

	var fullName: String? = null,
	val mobile: Long? = null,
	val employeeCode: String? = null,
	val fingerPrint: Boolean? = null,
	val attendances: ArrayList<AttendancesDT?>? = null
)