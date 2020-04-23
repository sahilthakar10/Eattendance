package com.example.eattendace.Employee

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eattendace.Employee.Model.AttendancesDT
import com.example.eattendace.Employee.Model.DataDT
import com.example.eattendace.NetworkManager.AppUtil
import com.example.eattendace.R
import kotlinx.android.synthetic.main.list_check.view.*

class EmpCheckListAdapter(val context : Context? ,var isShowing : Boolean = true) : RecyclerView.Adapter<EmpCheckListAdapter.ViewHolder>() {


    private var items : ArrayList<DataDT>? = ArrayList();
    private var onEmpCheckListener: onCheck? = null
    private var onModifyClickListener : modify? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_check, parent , false))
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    fun setOnEmpCheckListener(onCheck: onCheck?) {
        this.onEmpCheckListener = onCheck
    }

    fun setOnEmpEditListener(onModify: modify?) {
        this.onModifyClickListener = onModify
    }

    fun remove(position: Int){

        items?.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.e_name.setText(items?.get(position)?.fullName)
        holder.e_id.setText(items?.get(position)?.employeeCode)
        if (isShowing){
            holder.b_checkout.visibility = VISIBLE
            holder.b_checkin.visibility = GONE
            if (items?.get(position)?.attendances?.size!! %2 ==0){
                holder.b_checkout.visibility = GONE
                holder.b_checkin.visibility = VISIBLE
            }
            return
        }
    }

    fun update(position: Int , fullName: String?){
        items!!.get(position).fullName = fullName
        notifyDataSetChanged()
    }

    fun checkIntoggle(position: Int){
        val obj : AttendancesDT? = AttendancesDT()
        obj?.attend = "CheckIn"
        items?.get(position)?.attendances?.add( items!!.get(position).attendances?.size!! , obj )
        notifyDataSetChanged()
    }

    fun checkOutToggele(position: Int){

        val obj : AttendancesDT? = AttendancesDT()
        obj?.attend = "CheckOut"
        items?.get(position)?.attendances?.add( items!!.get(position).attendances?.size!! , obj )

        notifyDataSetChanged()
    }

    fun setData(listModle: ArrayList<DataDT>?){

        this.items = listModle;
        notifyDataSetChanged()
    }


    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val e_name = view.e_name
        val e_id = view.e_id
        val b_checkin = view.b_checkin
        val b_checkout = view.b_checkout
        val b_edit = view.edit
        val b_delete = view.delete

        init {

            if (isShowing){
                view.editLayout.visibility = GONE
            }

            b_edit.setOnClickListener {
                onModifyClickListener?.onEdit(items?.get(adapterPosition)?.employeeCode ,items?.get(adapterPosition)?.fullName,items?.get(adapterPosition)?.mobile , adapterPosition)
            }

            b_delete.setOnClickListener {
                onModifyClickListener?.onDelete(items?.get(adapterPosition)?.employeeCode , adapterPosition)
            }

            b_checkout.setOnClickListener {
                if (!AppUtil.isOnline(it.context)){
                    Toast.makeText(context , "No Internet Connection", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                onEmpCheckListener?.onCheckOut(items?.get(adapterPosition)?.employeeCode , adapterPosition)

            }

            b_checkin.setOnClickListener {
                if (!AppUtil.isOnline(it.context)){
                    Toast.makeText(context , "No Internet Connection", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                onEmpCheckListener?.onCheckIn(items?.get(adapterPosition)?.employeeCode , adapterPosition)

            }
        }



    }

    interface onCheck{
        fun onCheckIn(eCode : String? , position: Int)
        fun onCheckOut(eCode : String?, position: Int)
    }

    interface modify{
        fun onEdit(eCode : String? ,fullName : String? ,mobileNo: Long? , position: Int )
        fun onDelete(eCode : String? , position: Int)
    }
}



