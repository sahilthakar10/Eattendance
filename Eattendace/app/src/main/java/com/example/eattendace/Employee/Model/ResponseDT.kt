package com.example.eattendace.Employee.Model

import com.example.eattendace.Employee.Model.DataDT

data class ResponseDT(

	val statusCode: Int? = null,

	val data: ArrayList<DataDT>? = null
)