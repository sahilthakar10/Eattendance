package com.example.eattendace.Company

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
import com.example.eattendace.sharedPref
import kotlinx.android.synthetic.main.fragment_signin.view.*
import java.lang.Exception
import java.util.*


class FragmentCompanySignIn : Fragment() {

    private var companyViewModel : CompanyViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_signin, container, false)

        companyViewModel = ViewModelProvider(this)[CompanyViewModel::class.java]

        root.b_signin.setOnClickListener {
            val imm =
                (Objects.requireNonNull(requireContext())
                    .getSystemService(
                        Activity.INPUT_METHOD_SERVICE
                    ) as InputMethodManager)
            imm.hideSoftInputFromWindow(root.getWindowToken(), 0)
            try {
                if (isEmpty(root.username)){
                    root.username.setError("Pls Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.password)){
                    root.password.setError("Pls Fill This Up")
                    return@setOnClickListener
                }
                root.b_signin.isEnabled = false
                root.pBar.isEnabled = true
                companyViewModel!!.signIn(root.username.text.toString() , root.password.text.toString() )
            }catch (e : Exception){
                e.printStackTrace()
            }
        }

        root.layout_linear.setOnClickListener{
            if (findNavController().currentDestination!!.id == R.id.fragmentCompanySignIn){
                findNavController().navigate(R.id.action_fragmentCompanySignIn_to_fragmentCompanySignUp)
            }
        }

        companyViewModel?.response?.observe(viewLifecycleOwner , Observer {

            when(it){
                is NetworkResponse.ERROR -> {
                    root.b_signin.isEnabled = true
                    root.pBar.isEnabled = false
                    it.error.printStackTrace()
                }

                is NetworkResponse.SUCCESS.auth ->{
                    root.b_signin.isEnabled = true
                    root.pBar.isEnabled = false
                    if (it.response.statusCode == 200){
                        Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()
                        val pref =
                            requireContext().getSharedPreferences(sharedPref.sharedPrefName, 0)
                        val editor = pref.edit()
                        editor.putBoolean(sharedPref.checkSignIn, true)
                        editor.apply()
                        if (findNavController().currentDestination!!.id == R.id.fragmentCompanySignIn){
                            findNavController().navigate(R.id.action_fragmentCompanySignIn_to_fragmentDashboard)
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