package com.example.eattendace.Employee

import android.annotation.SuppressLint
import android.app.Activity
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
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.eattendace.FingerprintHandler
import com.example.eattendace.MainActivity
import com.example.eattendace.NetworkManager.NetworkResponse
import com.example.eattendace.R
import kotlinx.android.synthetic.main.alert_dia.view.password
import kotlinx.android.synthetic.main.fragment_add_emplyee.view.*
import kotlinx.android.synthetic.main.fragment_add_emplyee.view.image
import kotlinx.android.synthetic.main.toolbar.view.*
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

@Suppress("DEPRECATION")
class FragmentEmpAdd : Fragment() {


    private var obj : MainActivity = MainActivity()
    private val KEY_NAME = "SwA"

    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null

    private var fingerprintManager: FingerprintManager? = null

    private var empViewModel : EmpViewModel? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_emplyee, container, false)

        val toolbar: androidx.appcompat.widget.Toolbar? = root.action_bar.toolbar
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar?.setTitle("Add Employee")
        toolbar?.setTitleTextColor(resources.getColor(R.color.title_color))
        toolbar?.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        toolbar?.setNavigationOnClickListener{ requireActivity().onBackPressed() }


        empViewModel = ViewModelProvider(this)[EmpViewModel::class.java]


        root.button_add.setOnClickListener {
                val imm =
                    (Objects.requireNonNull(requireContext())
                        .getSystemService(
                            Activity.INPUT_METHOD_SERVICE
                        ) as InputMethodManager)
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0)
            try {
                if (isEmpty(root.fullname)){
                    root.fullname.setError("Pls Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.e_password)){
                    root.password.setError("Pls Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.mobile_no)){
                    root.mobile_no.setError("Pls Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.ecode)){
                    root.ecode.setError("Pls Fill This Up")
                    return@setOnClickListener
                }else if (isEmpty(root.dob)){
                    root.dob.setError("Pls Fill This Up")
                    return@setOnClickListener
                }else if (root.image.drawable.constantState == resources.getDrawable(R.drawable.ic_fingerprint_black_24dp).constantState) {
                    Toast.makeText(context , "Please Touch Your Finger Sension" , Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                root.button_add.isEnabled = false
                root.pBar1.visibility = VISIBLE
                empViewModel!!.empAdd(root.fullname.text.toString() ,
                    root.mobile_no.text.toString().toInt() ,
                    root.e_password.text.toString() ,
                    root.ecode.text.toString() ,
                    root.dob.text.toString() )

            }catch (e : Exception){
                e.printStackTrace()
            }

        }

        empViewModel?.response?.observe(viewLifecycleOwner , Observer {

            when(it){
                is NetworkResponse.ERROR -> {
                    root.button_add.isEnabled = true
                    root.pBar1.visibility = GONE
                    it.error.printStackTrace()
                }

                is NetworkResponse.SUCCESS.auth ->{
                    root.button_add.isEnabled = true
                    root.pBar1.visibility = GONE
                    if (it.response.statusCode == 200){
                        root.fullname.setText("")
                        root.mobile_no.setText("")
                        root.e_password.setText("")
                        root.ecode.setText("")
                        root.dob.setText("")
                        Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()
                        return@Observer
                    }
                    Toast.makeText(context , it.response.message , Toast.LENGTH_LONG).show()
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
        val fph = context?.let { FingerprintHandler(root.image, it) }
        fph!!.doAuth(fingerprintManager, cryptoObject)
        return root
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

}