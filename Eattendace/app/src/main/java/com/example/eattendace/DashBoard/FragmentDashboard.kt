package com.example.eattendace.DashBoard

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eattendace.NetworkManager.AppUtil
import com.example.eattendace.R
import com.example.eattendace.sharedPref
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.action_bar
import kotlinx.android.synthetic.main.fragment_report.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.util.*

@Suppress("DEPRECATION")
class FragmentDashboard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val toolbar: androidx.appcompat.widget.Toolbar? = root.action_bar.toolbar
        toolbar?.setTitle("Dash Board")
        toolbar?.setTitleTextColor(resources.getColor(R.color.title_color))
        toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        toolbar?.inflateMenu(R.menu.main_menu)
        toolbar?.setOnMenuItemClickListener{
            val pref =
                requireContext().getSharedPreferences(sharedPref.sharedPrefName, 0)
            val editor = pref.edit()
            editor.putBoolean(sharedPref.checkSignIn, false)
            editor.apply()

            if (findNavController().currentDestination!!.id == R.id.fragmentDashboard){
                findNavController().navigate(R.id.action_fragmentDashboard_to_fragmentSplaceScreen)
            }

            return@setOnMenuItemClickListener false
        }

        root.add_emp.setOnClickListener {
            if (!AppUtil.isOnline(requireContext())){
                Toast.makeText(context , "No Internet Connection", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (findNavController().currentDestination!!.id == R.id.fragmentDashboard){
                findNavController().navigate(R.id.action_fragmentDashboard_to_fragmentEmpAdd)
            }
        }
        root.b_check.setOnClickListener {
            if (!AppUtil.isOnline(requireContext())){
                Toast.makeText(context , "No Internet Connection", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (findNavController().currentDestination!!.id == R.id.fragmentDashboard){
                findNavController().navigate(R.id.action_fragmentDashboard_to_fragmentEmpCheck)
            }
        }
        root.emp_report.setOnClickListener {
            if (!AppUtil.isOnline(requireContext())){
                Toast.makeText(context , "No Internet Connection", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (findNavController().currentDestination!!.id == R.id.fragmentDashboard){
                findNavController().navigate(R.id.action_fragmentDashboard_to_fragmentReport)
            }
        }
        root.edit_emp.setOnClickListener {
            if (!AppUtil.isOnline(requireContext())){
                Toast.makeText(context , "No Internet Connection", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (findNavController().currentDestination!!.id == R.id.fragmentDashboard){
                findNavController().navigate(R.id.action_fragmentDashboard_to_fragmentEmpEdit)
            }
        }


        return root
    }

}