package com.sinarkelas.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.sinarkelas.R
import com.sinarkelas.contract.CekEmailDosenContract
import com.sinarkelas.contract.RegisterDosenContract
import com.sinarkelas.presenter.CekEmailDosenPresenter
import com.sinarkelas.presenter.RegisterDosenPresenter
import com.sinarkelas.ui.aktivasiakun.AktivasiAkun
import com.sinarkelas.ui.login.LoginActivity
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterDosenContract.registerView, CekEmailDosenContract.cekEmailDosenView {

    private var presenter : RegisterDosenContract.registerPresenter? = null
    private var presenterCekEmail : CekEmailDosenContract.cekEmailDosenPresenter? = null
    private var stylingUtil : StylingUtil? = null
    private var successCek : String? = ""
    private var messageCek : String? = ""
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterDosenPresenter(this)
        presenterCekEmail = CekEmailDosenPresenter(this)

        stylingUtil = StylingUtil()

        stylingUtil?.montserratBoldTextview(this, tvJudulDaftarSebagaiDosen)
        stylingUtil?.montserratBoldTextview(this, tvDaftarSebagaiDosenRegister)

        var emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var emailPatternCoId : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"

        tietEmailRegisterDosen.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var emailDosen = tietEmailRegisterDosen.text.toString().trim()
                if (s!!.length>0){
                    if (emailDosen.matches(emailPattern.toRegex()) || emailDosen.matches(emailPatternCoId.toRegex())){
                        llDaftarSebagaiDosenRegister.isEnabled = true
                        llDaftarSebagaiDosenRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_border))
                        tvEmailRegisterDosenWrong.visibility = View.GONE
                    } else{
                        llDaftarSebagaiDosenRegister.isEnabled = false
                        llDaftarSebagaiDosenRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.round_disable_button_border))
                        tvEmailRegisterDosenWrong.visibility = View.VISIBLE
                    }
                } else{
                    llDaftarSebagaiDosenRegister.isEnabled = false
                    llDaftarSebagaiDosenRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.round_disable_button_border))
                    tvEmailRegisterDosenWrong.visibility = View.VISIBLE
                }
                presenterCekEmail?.sendCekEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        tietKataSandiUlangRegisterDosen.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!!.length>0){
                    if (s.toString().equals(tietKataSandiRegisterDosen.text.toString())){
                        llDaftarSebagaiDosenRegister.isEnabled = true
                        llDaftarSebagaiDosenRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.round_button_border))
                    } else{
                        llDaftarSebagaiDosenRegister.isEnabled = false
                        llDaftarSebagaiDosenRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.round_disable_button_border))
                        tietKataSandiUlangRegisterDosen.setError("Tidak sama dengan kata sandi!")
                    }
                } else{
                    llDaftarSebagaiDosenRegister.isEnabled = false
                    llDaftarSebagaiDosenRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.round_disable_button_border))
                    tietKataSandiUlangRegisterDosen.setError("Tidak boleh kosong!")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        llDaftarSebagaiDosenRegister.setOnClickListener {
            daftar()
        }

        ivBackRegisterDosen.setOnClickListener {
            startActivity(Intent(this, PilihanRegister::class.java))
            finish()
        }
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

    override fun successRegis(namaDosen : String) {
        var intent = Intent(this, AktivasiAkun::class.java)
        intent.putExtra("namaDosen", namaDosen)
        startActivity(intent)
        finish()
    }

    fun daftar(){
        val namaLengkap = tietNamaLengkapRegisterDosen.text.toString()
        val email = tietEmailRegisterDosen.text.toString()
        val password = tietKataSandiRegisterDosen.text.toString()
        val repassword = tietKataSandiUlangRegisterDosen.text.toString()

        if (repassword != password){
            Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_LONG).show()
        } else if (namaLengkap.isBlank()){
            Toast.makeText(this, "Nama Lengkap Harus Diisi!", Toast.LENGTH_LONG).show()
        } else if (email.isBlank()){
            Toast.makeText(this, "Email Harus Diisi!", Toast.LENGTH_LONG).show()
        } else if (password.isBlank()){
            Toast.makeText(this, "Password Harus Diisi!", Toast.LENGTH_LONG).show()
        } else{
            presenter?.registerDosen(namaLengkap, email, password)
        }
    }

    override fun showLoadingCekEmail() {

    }

    override fun hideLoadingCekEmail() {

    }

    override fun showToastCekEmail(message: String) {
        messageCek = message
    }

    override fun success(success: String) {
        if (success.equals("1")){
            successCek = success
        } else{
            tietEmailRegisterDosen.setError("Gagal, Akun email sudah di gunakan!")
            successCek = success
        }
    }

    @Override
    override fun onBackPressed() {
        startActivity(Intent(this, PilihanRegister::class.java))
        finish()
    }
}