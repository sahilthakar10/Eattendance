package com.example.eattendace.SplaceScreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eattendace.R
import com.example.eattendace.sharedPref


class FragmentSplaceScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_splace_screen, container, false)

        val pref =
            requireContext().getSharedPreferences(sharedPref.sharedPrefName, 0)

        Handler().postDelayed({

            if (pref.getBoolean(sharedPref.checkSignIn , false)){

                findNavController().navigate(R.id.action_fragmentSplaceScreen_to_fragmentDashboard)
                return@postDelayed
            }
            findNavController().navigate(R.id.action_fragmentSplaceScreen_to_fragmentCompanySignIn)


        }, 2000)

        return root
    }
}