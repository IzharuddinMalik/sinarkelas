package com.sinarkelas.ui.login

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.AppUpdaterUtils
import com.github.javiersantos.appupdater.enums.AppUpdaterError
import com.github.javiersantos.appupdater.objects.Update
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.sinarkelas.R
import com.sinarkelas.contract.LoginDosenContract
import com.sinarkelas.presenter.LoginDosenPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_login_dosen.*

class LoginDosenActivity : AppCompatActivity(), LoginDosenContract.loginView {

    private var presenter : LoginDosenContract.loginPresenter? = null
    private var stylingUtil : StylingUtil? = null
    private var getToken : String? = ""
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_dosen)

        //============= Check Update playstore ==============
        val appUpdaterUtils: AppUpdaterUtils = AppUpdaterUtils(this)
            .withListener(object : AppUpdaterUtils.UpdateListener {
                override fun onSuccess(
                    update: Update?,
                    isUpdateAvailable: Boolean
                ) {
                    if (isUpdateAvailable) {
                        AppUpdater(this@LoginDosenActivity)
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

        presenter = LoginDosenPresenter(this)

        stylingUtil = StylingUtil()

        stylingUtil?.montserratBoldTextview(this, tvJudulLoginDosen)
        stylingUtil?.montserratBoldTextview(this, tvLoginDosen)

        var emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var emailPatternCoId : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"

        tietEmailLoginDosen.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var emailLoginDosen = tietEmailLoginDosen.text.toString().trim()
                if (s!!.length>0){
                    if (emailLoginDosen.matches(emailPattern.toRegex()) || emailLoginDosen.matches(emailPatternCoId.toRegex())){
                        llLoginDosen.isEnabled = true
                        llLoginDosen.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_border))
                        tvEmailLoginDosenWrong.visibility = View.GONE
                    } else{
                        llLoginDosen.isEnabled = false
                        llLoginDosen.setBackgroundDrawable(resources.getDrawable(R.drawable.round_disable_button_border))
                        tvEmailLoginDosenWrong.visibility = View.VISIBLE
                    }
                } else{
                    llLoginDosen.isEnabled = false
                    llLoginDosen.setBackgroundDrawable(resources.getDrawable(R.drawable.round_disable_button_border))
                    tvEmailLoginDosenWrong.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        llLoginDosen.setOnClickListener {
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

    override fun successLogin(idDosen : String, statusUser : String, statusAkun : String, tipeAkun : String) {
        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("idDosen", idDosen)
        editor.putString("statusUser", statusUser)
        editor.putString("statusAkun", statusAkun)
        editor.putString("tipeAkun", tipeAkun)
        editor.commit()

        intent = Intent(this, DashboardHomeDosen::class.java)
        startActivity(intent).also {
            finish()
        }
    }

    fun login(){
        val email = tietEmailLoginDosen.text.toString()
        val password = tietPasswordLoginDosen.text.toString()

        if (email.isBlank()){
            Toast.makeText(this, "Email Tidak Boleh Kosong!", Toast.LENGTH_LONG).show()
        }

        if (password.isBlank()){
            Toast.makeText(this, "Password Tidak Boleh Kosong!", Toast.LENGTH_LONG).show()
        }

        presenter?.loginDosen(email, password, getToken!!)
    }
}
