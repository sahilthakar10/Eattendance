package com.example.eattendace.NetworkManager

import com.example.eattendace.Company.AuthModelDTO
import com.example.eattendace.Employee.Model.ResponseDT

sealed class NetworkResponse {

        object START : NetworkResponse()

        data class ERROR(val error: Throwable) : NetworkResponse()
        data class ERROR_RESPONSE(val messageId: Int) : NetworkResponse()

        sealed class SUCCESS<out T>(val  response: T) : NetworkResponse() {
                data class auth(val res : AuthModelDTO) : SUCCESS<AuthModelDTO>(res)

                data class employeeList(val res : ResponseDT) : SUCCESS<ResponseDT>(res)
        }
}