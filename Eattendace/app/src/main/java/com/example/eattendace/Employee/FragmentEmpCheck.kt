@file:Suppress("DEPRECATION")

package com.example.eattendace.Employee

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eattendace.FingerprintHandler
import com.example.eattendace.MainActivity
import com.example.eattendace.NetworkManager.NetworkResponse
import com.example.eattendace.R
import kotlinx.android.synthetic.main.alert_dia.view.*
import kotlinx.android.synthetic.main.alert_dia.view.image
import kotlinx.android.synthetic.main.alert_dia.view.password
import kotlinx.android.synthetic.main.fragment_check.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

@Suppress("DEPRECATION")
class FragmentEmpCheck : Fragment() , EmpCheckListAdapter.onCheck {
    var empCheckListAdapter: EmpCheckListAdapter? = null
    private var empViewModel : EmpViewModel? = null

    private val KEY_NAME = "SwA"
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null

    private var fingerprintManager: FingerprintManager? = null
    var alert: AlertDialog.Builder? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_check, container, false)

        val toolbar: androidx.appcompat.widget.Toolbar? = root.action_bar.toolbar
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar?.setTitle("Check In/Out")
        toolbar?.setTitleTextColor(resources.getColor(R.color.title_color))
        toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        toolbar?.setNavigationOnClickListener{ requireActivity().onBackPressed() }

        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]
        empCheckListAdapter = EmpCheckListAdapter(root.context)
        empViewModel!!.empList()
        empCheckListAdapter!!.setOnEmpCheckListener(this)

        root.recyclerview.layoutManager = LinearLayoutManager(context)
        root.recyclerview.setHasFixedSize(true)
        root.recyclerview.adapter = empCheckListAdapter


        empViewModel?.response?.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResponse.ERROR -> it.error.printStackTrace()

                is NetworkResponse.SUCCESS.employeeList -> {
                    if (it.response.statusCode == 200){
                        root.empLoad.visibility = GONE

                        if (it.response.data?.size!! > 0){
                            root.recyclerview.visibility = VISIBLE
                            empCheckListAdapter!!.setData(it.response.data)
                            return@Observer
                        }
                        root.recyclerview.visibility = GONE
                        root.empLoad.visibility = GONE
                        root.text_record.visibility = VISIBLE
                        return@Observer
                    }
                    showToast("Error In Load")
                }
            }
        })

        empViewModel?.response?.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResponse.ERROR -> it.error.printStackTrace()

                is NetworkResponse.SUCCESS.auth -> {
                    showToast(it.response.message)
                }
            }
        })

        if (checkFinger()) {
            try {

                generateKey()
                val cipher = generateCipher()
                cryptoObject = FingerprintManager.CryptoObject(cipher!!)
            } catch (fpe: MainActivity.FingerprintException) {
            }
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCheckIn(eCode : String? , position : Int) {
      alertDialog("CheckIn", eCode , position)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCheckOut(eCode : String? , position : Int) {
        alertDialog("CheckOut",eCode , position)
    }

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.M)
    fun alertDialog(check : String , eCode: String? , position: Int){
        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.alert_dia, null)
        val image: ImageView = alertLayout.image
        val fph = FingerprintHandler(image, requireContext())
        alertLayout.image.setImageDrawable(
            resources.getDrawable(R.drawable.ic_fingerprint_black_24dp, requireContext().theme)
        )

        fph.doAuth(fingerprintManager, cryptoObject)

        alert = AlertDialog.Builder(requireContext())
        alert!!.setView(alertLayout)
        alert!!.setCancelable(false)

        alert!!.setNegativeButton("Cancel") { dialog, which -> }

        alert!!.setPositiveButton("Done") { dialog, which ->

            try {
                if (check.equals("CheckIn")){
                    if (alertLayout.image.drawable.constantState == resources.getDrawable(R.drawable.ic_check_black_24dp).constantState){
                        empViewModel!!.empCheckIn(alertLayout.password.text.toString() ,eCode ,1)
                        empCheckListAdapter!!.checkIntoggle(position)
                        return@setPositiveButton
                    }
                    if (isEmpty(alertLayout.emCode)){
                        showToast("Please Enter Employee Code")
                        return@setPositiveButton
                    }
                    else if (isEmpty(alertLayout.password)){
                        showToast("Please Enter Password")
                        return@setPositiveButton
                    }
                    empViewModel!!.empCheckIn(alertLayout.password.text.toString() ,alertLayout.emCode.text.toString(),0)
                    empCheckListAdapter!!.checkIntoggle(position)
                    return@setPositiveButton
                }

                if (alertLayout.image.drawable.constantState == resources.getDrawable(R.drawable.ic_check_black_24dp).constantState){
                    empViewModel!!.empCheckOut(alertLayout.password.text.toString() ,eCode ,1)
                    empCheckListAdapter!!.checkOutToggele(position)
                    return@setPositiveButton
                }
                if (isEmpty(alertLayout.emCode)){
                    showToast("Please Enter Employee Code")
                }
                else if (isEmpty(alertLayout.password)){
                    showToast("Please Enter Password")
                    return@setPositiveButton
                }


                empViewModel!!.empCheckOut(alertLayout.password.text.toString() ,"e123",0)
                empCheckListAdapter!!.checkOutToggele(position)

            }catch (e : Exception){
                e.printStackTrace()
            }


        }
        val dialog = alert!!.create()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun checkFinger(): Boolean {


        val keyguardManager =
            requireContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        fingerprintManager =
            requireContext().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        try {
            assert(fingerprintManager != null)
            if (!fingerprintManager!!.isHardwareDetected) {
                return false
            }
            if (!fingerprintManager!!.hasEnrolledFingerprints()) {
                return false
            }
            if (!keyguardManager.isKeyguardSecure) {
                try {
                    generateKey()
                } catch (e: FingerprintException) {
                    e.printStackTrace()
                }
                return false
            }
        } catch (se: SecurityException) {
            se.printStackTrace()
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Throws(FingerprintException::class)
    fun generateKey() {
        try {
            // Get the reference to the key store
            keyStore = KeyStore.getInstance("AndroidKeyStore")

            // Key generator to generate the key
            keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            keyStore!!.load(null)
            keyGenerator!!.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or
                            KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    .build()
            )
            Log.e("Generate_Key", keyGenerator!!.generateKey().toString())
            keyGenerator!!.generateKey()
        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchProviderException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: InvalidAlgorithmParameterException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: CertificateException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: IOException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        }
    }

    @Throws(FingerprintException::class)
    fun generateCipher(): Cipher? {
        return try {
            val cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
            val key = keyStore!!.getKey(
                KEY_NAME,
                null
            ) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)
            cipher
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchPaddingException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: InvalidKeyException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: UnrecoverableKeyException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        }
    }

    class FingerprintException(e: Exception?) :
        Exception(e)

    private fun isEmpty(etText: EditText): Boolean {
        return if (etText.text.toString().trim().length > 0) false else true
    }

     fun showToast(mes : String?){
        Toast.makeText(context , mes , Toast.LENGTH_LONG).show()
    }
}