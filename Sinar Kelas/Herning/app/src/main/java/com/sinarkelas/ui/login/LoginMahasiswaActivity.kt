package com.sinarkelas.ui.login

import android.content.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.objects.Update
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.sinarkelas.R
import com.sinarkelas.contract.LoginMahasiswaContract
import com.sinarkelas.presenter.LoginMahasiswaPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_login_mahasiswa.*

class LoginMahasiswaActivity : AppCompatActivity(), LoginMahasiswaContract.loginView {

    private var presenter : LoginMahasiswaContract.loginPresenter? = null
    private var stylingUtil : StylingUtil? = null
    private var getToken : String? = ""
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_mahasiswa)

        //============= Check Update playstore ==============
        val appUpdaterUtils: AppUpdaterUtils = AppUpdaterUtils(this)
            .withListener(object : AppUpdaterUtils.UpdateListener {
                override fun onSuccess(
                    update: Update?,
                    isUpdateAvailable: Boolean
                ) {
                    if (isUpdateAvailable) {
                        AppUpdater(this@LoginMahasiswaActivity)
                            .setTitleOnUpdateAvailable("Aplikasi terbaru telah tersedia")
                            .setContentOnUpdateAvailable("Silahkan perikasa update terbaru dari aplikasi ini")
                            .setButtonUpdate("Update Sekarang?")
                            .setButtonUpdateClickListener(DialogInterface.OnClickListener { dialogInterface, i ->
                                val appPackageName =
                                    packageName // getPackageName() from Context or Activity object
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=$appPackageName")
                                        )
                                    )
                                } catch (anfe: ActivityNotFoundException) {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                        )
                                    )
                                }
                            })
                            .setButtonDismiss("")
                            .setButtonDoNotShowAgain("")
                            .setIcon(R.drawable.logo512pixelbaru) // Notification icon
                            .showAppUpdated(true)
                            .start() // Dialog could not be dismissable
                    }
                }

                override fun onFailed(error: AppUpdaterError?) {
                    Log.d("AppUpdater Error", "Something went wrong")
                }
            })
        appUpdaterUtils.start()

        presenter = LoginMahasiswaPresenter(this)

        stylingUtil = StylingUtil()
        stylingUtil?.montserratBoldTextview(this, tvJudulLoginMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvLoginMahasiswa)

        var emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var emailPatternCoId : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"

        tietEmailLoginMahasiswa.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var emailLoginMahasiswa = tietEmailLoginMahasiswa.text.toString().trim()
                if (s!!.length>0){
                    if (emailLoginMahasiswa.matches(emailPattern.toRegex()) || emailLoginMahasiswa.matches(emailPatternCoId.toRegex())){
                        cvLoginMahasiswa.isEnabled = true
                        cvLoginMahasiswa.setCardBackgroundColor(resources.getColor(R.color.maroonSinar))
                        tvEmailLoginMahasiswaWrong.visibility = View.GONE
                    } else{
                        cvLoginMahasiswa.isEnabled = false
                        cvLoginMahasiswa.setCardBackgroundColor(resources.getColor(R.color.grey_font))
                        tvEmailLoginMahasiswaWrong.visibility = View.VISIBLE
                    }
                } else{
                    cvLoginMahasiswa.isEnabled = false
                    cvLoginMahasiswa.setCardBackgroundColor(resources.getColor(R.color.grey_font))
                    tvEmailLoginMahasiswaWrong.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        cvLoginMahasiswa.setOnClickListener {
            login()
        }

        //============= Firebase ==============
        FirebaseMessaging.getInstance().subscribeToTopic("test")
        getToken = FirebaseInstanceId.getInstance().token
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun successLogin(idMahasiswa: String, statusUser : String) {
        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("idMahasiswa", idMahasiswa)
        editor.putString("statusUser", statusUser)
        editor.commit()

        intent = Intent(this, DashboardHomeMahasiswa::class.java)
        startActivity(intent).also {
            finish()
        }
    }

    fun login(){

        val email = tietEmailLoginMahasiswa.text.toString()
        val password = tietPasswordLoginMahasiswa.text.toString()

        if (email.isBlank()){
            Toast.makeText(this, "Email Tidak Boleh Kosong!", Toast.LENGTH_LONG).show()
        }

        if (password.isBlank()){
            Toast.makeText(this, "Password Tidak Boleh Kosong!", Toast.LENGTH_LONG).show()
        }

        presenter?.loginMahasiswa(email, password, getToken!!)
    }
}