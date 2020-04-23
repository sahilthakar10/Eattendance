package com.example.eattendace.Employee

import android.content.Context
import android.graphics.Color.RED
import android.graphics.Color.YELLOW
import android.hardware.camera2.params.RggbChannelVector.RED
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eattendace.Employee.Model.DataDT
import com.example.eattendace.NetworkManager.NetworkResponse
import com.example.eattendace.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_report.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Color
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.text.DateFormat
import java.util.*

@Suppress("DEPRECATION")
class FragmentReport : Fragment(){
    private var empViewModel : EmpViewModel? = null
    private var items : ArrayList<DataDT>? = ArrayList();
    private var counter : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_report, container, false)

        val toolbar: androidx.appcompat.widget.Toolbar? = root.action_bar.toolbar
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar?.setTitle("Report")
        toolbar?.setTitleTextColor(resources.getColor(R.color.title_color))
        toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        toolbar?.setNavigationOnClickListener{ requireActivity().onBackPressed() }

        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]

        empViewModel?.empList()

        empViewModel?.response?.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResponse.ERROR -> {
                    showToast("Please Try Again")
                    root.pBar.visibility = GONE
                    it.error.printStackTrace()
                }

                is NetworkResponse.SUCCESS.employeeList -> {
                    root.downloadExcel.visibility = VISIBLE
                            root.downloadText.visibility = VISIBLE
                    root.pBar.visibility = GONE
                    items = it.response.data
                }
            }
        })

        root.downloadExcel.setOnClickListener {
            if (saveExcelFile(requireContext())){
                Toast.makeText(context , "Download SuccessFully" , Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            Toast.makeText(context , "Error in Download" , Toast.LENGTH_LONG).show()
        }

        root.downloadText.setOnClickListener {
            if (saveTextFile(requireContext())){
                Toast.makeText(context , "Download SuccessFully" , Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            Toast.makeText(context , "Error in Download" , Toast.LENGTH_LONG).show()
        }

        return root
    }

    private fun saveTextFile(
        context: Context
    ) : Boolean {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return false
        }
        val folder = File(
            Environment.getExternalStorageDirectory().toString() +
                    File.separator + "TollCulator"
        )
        var success = false
        if (!folder.exists()) {
            success = folder.mkdirs()
        }
        val currentDateTimeString =
            DateFormat.getDateTimeInstance().format(Date())
        val file =
            File(Environment.getExternalStorageDirectory().path + "/TollCulator/" + currentDateTimeString + ".txt")
        val writer = FileWriter(file, true)

        for (i in 0..items?.size!!-1){
            for (j in 0..items?.get(i)?.attendances?.size!!-1) {
                writer.append("FULLNAME  " +  items?.get(i)?.fullName + "\n")
                writer.append("DOB  " +  items?.get(i)?.dOB + "\n")
                writer.append("EMPCODE  " +  items?.get(i)?.employeeCode + "\n")
                writer.append("ATTEND  " + items?.get(i)?.attendances?.get(j)?.attend + "\n")
                writer.append("DATE  " + items?.get(i)?.attendances?.get(j)?.date + "\n")
                writer.append("CREATEDAT  " + items?.get(i)?.attendances?.get(j)?.createdAt + "\n")
                writer.append("\n")
            }
        }

        writer.flush();
        writer.close();


        success = true

        return success
    }

    private fun saveExcelFile(
        context: Context
    ): Boolean {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return false
        }
        var success = false

        //New Workbook
        val wb: Workbook = HSSFWorkbook()
        var c: Cell? = null

        //Cell style for header row
        val cs = wb.createCellStyle()
        cs.fillForegroundColor = HSSFColor.LIME.index
        cs.fillPattern = HSSFCellStyle.NO_FILL

        //New Sheet
        var sheet1: Sheet? = null
        sheet1 = wb.createSheet("Attendace")


        for (i in 0..items?.size!!-1){
            for (j in 0..items?.get(i)?.attendances?.size!!-1){
                val row = sheet1.createRow(counter++)

                c = row.createCell(0)
                c.setCellValue(items?.get(i)?.fullName)
                c.cellStyle = cs

                c = row.createCell(1)
                c.setCellValue(items?.get(i)?.dOB)
                c.cellStyle = cs

                c = row.createCell(2)
                c.setCellValue(items?.get(i)?.employeeCode)
                c.cellStyle = cs

                c = row.createCell(3)
                c.setCellValue(items?.get(i)?.attendances?.get(j)?.attend)
                c.cellStyle = cs

                c = row.createCell(4)
                c.setCellValue(items?.get(i)?.attendances?.get(j)?.date)
                c.cellStyle = cs

                c = row.createCell(5)
                c.setCellValue(items?.get(i)?.attendances?.get(j)?.createdAt)
                c.cellStyle = cs
            }
        }

        val folder = File(
            Environment.getExternalStorageDirectory().toString() +
                    File.separator + "TollCulator"
        )
        if (!folder.exists()) {
            success = folder.mkdirs()
        }

        val currentDateTimeString =
            DateFormat.getDateTimeInstance().format(Date())

        val file =
            File(Environment.getExternalStorageDirectory().path + "/TollCulator/" + currentDateTimeString + ".xls")
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(file)
            wb.write(os)
            Log.w("FileUtils", "Writing file$file")
            success = true
        } catch (e: IOException) {
            Log.w("FileUtils", "Error writing $file", e)
        } catch (e: Exception) {
            Log.w("FileUtils", "Failed to save file", e)
        } finally {
            try {
                os?.close()
            } catch (ex: Exception) {
            }
        }

        Toast.makeText(context , "Download SuccessFully" , Toast.LENGTH_LONG).show()
        return success
    }

    fun isExternalStorageReadOnly(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }
    fun showToast(mes : String?){
        Toast.makeText(context , mes , Toast.LENGTH_LONG).show()
    }
}