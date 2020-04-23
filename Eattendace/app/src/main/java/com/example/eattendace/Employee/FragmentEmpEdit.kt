package com.example.eattendace.Employee

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eattendace.Employee.Model.DataDT
import com.example.eattendace.NetworkManager.NetworkResponse
import com.example.eattendace.R
import kotlinx.android.synthetic.main.edit_alert_dialog.view.*
import kotlinx.android.synthetic.main.fragment_add_emplyee.view.*
import kotlinx.android.synthetic.main.fragment_check.view.*
import kotlinx.android.synthetic.main.fragment_check.view.action_bar
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class FragmentEmpEdit : Fragment() , EmpCheckListAdapter.modify{
    private var empViewModel : EmpViewModel? = null
    var empCheckListAdapter: EmpCheckListAdapter? = null
    private var items : ArrayList<DataDT>? = ArrayList();
    var alert: AlertDialog.Builder? = null
    var root : View?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_check, container, false)

        val toolbar: androidx.appcompat.widget.Toolbar? = root?.action_bar!!.toolbar
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar?.setTitle("Employee Edit/Delete")
        toolbar?.setTitleTextColor(resources.getColor(R.color.title_color))
        toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        toolbar?.setNavigationOnClickListener{ requireActivity().onBackPressed() }


        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]
        empCheckListAdapter = EmpCheckListAdapter(root?.context , false)
        empViewModel!!.empList()
        empCheckListAdapter!!.setOnEmpEditListener(this)

        root?.recyclerview!!.layoutManager = LinearLayoutManager(context)
        root?.recyclerview!!.setHasFixedSize(true)
        root?.recyclerview!!.adapter = empCheckListAdapter


        empViewModel?.response?.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResponse.ERROR -> it.error.printStackTrace()

                is NetworkResponse.SUCCESS.employeeList -> {
                    if (it.response.statusCode == 200){
                        root?.empLoad!!.visibility = View.GONE

                        if (it.response.data?.size!! > 0){
                            root?.recyclerview!!.visibility = View.VISIBLE
                            empCheckListAdapter!!.setData(it.response.data)
                            return@Observer
                        }
                        root?.recyclerview!!.visibility = View.GONE
                        root?.empLoad!!.visibility = View.GONE
                        root?.text_record!!.visibility = View.VISIBLE
                        return@Observer
                    }
                    showToast("Error In Load")
                }
            }
        })
        empViewModel?.response?.observe(viewLifecycleOwner , Observer {

            when(it){
                is NetworkResponse.ERROR -> {
                    it.error.printStackTrace()
                }

                is NetworkResponse.SUCCESS.auth ->{
                    if (it.response.statusCode == 200){
                        Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()
                        return@Observer
                    }
                    Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()
                }
            }
        })


        return root
    }

    override fun onEdit(eCode: String? , fullName : String? , mobileNo : Long? , position : Int) {

        try {
            val inflater = layoutInflater
            val alertLayout: View = inflater.inflate(R.layout.edit_alert_dialog, null)

            alertLayout.fullName.setText(fullName)
            alertLayout.mobileNo.setText(mobileNo.toString())

            alert = AlertDialog.Builder(requireContext())
            alert!!.setView(alertLayout)
            alert!!.setCancelable(false)

            alert!!.setNegativeButton("Cancel") { dialog, which -> }

            alert!!.setPositiveButton("Done") { dialog, which ->

                empCheckListAdapter!!.update(position , alertLayout.fullName.text.toString())
                empViewModel?.empUpdate(eCode , alertLayout.fullName.text.toString() ,alertLayout.mobileNo.text.toString().toLong() )

            }
            val dialog = alert!!.create()
            dialog.show()
        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    override fun onDelete(eCode: String? ,  position: Int) {
        if (position ==0){
            root?.recyclerview!!.visibility = View.GONE
            root?.empLoad!!.visibility = View.GONE
            root?.text_record!!.visibility = View.VISIBLE
        }
        empViewModel!!.empDelete(eCode)
        empCheckListAdapter!!.remove(position)

    }


    fun showToast(mes : String?){
        Toast.makeText(context , mes , Toast.LENGTH_LONG).show()
    }

}