package com.example.eattendace.Company

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eattendace.NetworkManager.NetworkResponse
import com.example.eattendace.R
import kotlinx.android.synthetic.main.fragment_signup.view.*
import kotlinx.android.synthetic.main.fragment_signup.view.pBar
import kotlinx.android.synthetic.main.fragment_signup.view.username
import java.lang.Exception
import java.util.*

class FragmentCompanySignUp : Fragment() {

    private var companyViewModel : CompanyViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_signup, container, false)

        companyViewModel = ViewModelProvider(this)[CompanyViewModel::class.java]

        root.rel_click.setOnClickListener {
            if (findNavController().currentDestination!!.id == R.id.fragmentCompanySignIn){
                findNavController().navigate(R.id.action_fragmentCompanySignUp_to_fragmentCompanySignIn)
            }
        }

        root.b_signup.setOnClickListener {
            val imm =
                (Objects.requireNonNull(requireContext())
                    .getSystemService(
                        Activity.INPUT_METHOD_SERVICE
                    ) as InputMethodManager)
            imm.hideSoftInputFromWindow(root.getWindowToken(), 0)
            try {
                if (isEmpty(root.company_name)){
                    root.company_name.setError("Please Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.username)){
                    root.username.setError("Please Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.e_password)){
                    root.e_password.setError("Please Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.mobile_no)){
                    root.mobile_no.setError("Please Fill This Up")
                    return@setOnClickListener
                }
                root.b_signup.isEnabled = false
                root.pBar.isEnabled = true

                companyViewModel!!.signUp(root.username.text.toString() , root.e_password.text.toString() ,  root.company_name.text.toString())

            }catch (e : Exception){
                e.printStackTrace()
            }
        }

        companyViewModel?.response?.observe(viewLifecycleOwner , Observer {

            when(it){
                is NetworkResponse.ERROR -> {
                    root.b_signup.isEnabled = true
                    root.pBar.isEnabled = false
                    it.error.printStackTrace()
                }

                is NetworkResponse.SUCCESS.auth ->{
                    root.b_signup.isEnabled = true
                    root.pBar.isEnabled = false

                    if (it.response.statusCode == 200){
                        Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()
                        if (findNavController().currentDestination!!.id == R.id.fragmentCompanySignUp){
                            findNavController().navigate(R.id.action_fragmentCompanySignUp_to_fragmentCompanySignIn)
                        }
                        return@Observer
                    }
                    Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()

                }
            }
        })
        return root
    }

    private fun isEmpty(etText: EditText): Boolean {
        return if (etText.text.toString().trim().length > 0) false else true
    }
}