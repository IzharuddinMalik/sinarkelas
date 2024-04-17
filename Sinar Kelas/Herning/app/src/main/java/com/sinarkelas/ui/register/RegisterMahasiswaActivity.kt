package com.sinarkelas.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.CekEmailMahasiswaContract
import com.sinarkelas.contract.RegisterMahasiswa
import com.sinarkelas.presenter.CekEmailMahasiswaPresenter
import com.sinarkelas.presenter.RegisterMahasiswaPresenter
import com.sinarkelas.ui.aktivasiakun.AktivasAkunMahasiswa
import com.sinarkelas.ui.login.LoginActivity
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register_mahasiswa.*

class RegisterMahasiswaActivity : AppCompatActivity(), RegisterMahasiswa.registerMhsView, CekEmailMahasiswaContract.cekEmailMahasiswaView {

    private var presenter : RegisterMahasiswa.registerMhsPresenter? = null
    private var stylingUtil : StylingUtil? = null
    private var presenterCekEmail : CekEmailMahasiswaContract.cekEmailMahasiswaPresenter? = null
    var successCek : String? = ""
    var messageCek : String? = ""
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_mahasiswa)

        presenter = RegisterMahasiswaPresenter(this)
        presenterCekEmail = CekEmailMahasiswaPresenter(this)

        stylingUtil = StylingUtil()

        stylingUtil?.montserratBoldTextview(this, tvJudulDaftarSebagaiMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvDaftarSebagaiMahasiswaRegister)

        var emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        var emailPatternCoId : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"

        tietEmailRegisterMahasiswa.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var emailMahasiswa = tietEmailRegisterMahasiswa.text.toString().trim()
                if (s!!.length>0){
                    if (emailMahasiswa.matches(emailPattern.toRegex()) || emailMahasiswa.matches(emailPatternCoId.toRegex())){
                        cvDaftarSebagaiMahasiswaRegister.isEnabled = true
                        cvDaftarSebagaiMahasiswaRegister.setCardBackgroundColor(resources.getColor(R.color.maroonSinar))
                        tvEmailRegisterMahasiswaWrong.visibility = View.GONE
                    } else{
                        cvDaftarSebagaiMahasiswaRegister.isEnabled = false
                        cvDaftarSebagaiMahasiswaRegister.setCardBackgroundColor(resources.getColor(R.color.grey_font))
                        tvEmailRegisterMahasiswaWrong.visibility = View.VISIBLE
                    }
                } else{
                    cvDaftarSebagaiMahasiswaRegister.isEnabled = false
                    cvDaftarSebagaiMahasiswaRegister.setCardBackgroundColor(resources.getColor(R.color.grey_font))
                    tvEmailRegisterMahasiswaWrong.visibility = View.VISIBLE
                }
                presenterCekEmail?.sendCekEmail(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        tietKataSandiUlangRegisterMahasiswa.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (s!!.length>0){
                    if (s.toString().equals(tietKataSandiRegisterMahasiswa.text.toString())){
                        cvDaftarSebagaiMahasiswaRegister.isEnabled = true
                        cvDaftarSebagaiMahasiswaRegister.setCardBackgroundColor(resources.getColor(R.color.maroonSinar))
                        Log.i("KATASANDI", " === " + tietKataSandiRegisterMahasiswa.text.toString())
                    } else{
                        cvDaftarSebagaiMahasiswaRegister.isEnabled = false
                        cvDaftarSebagaiMahasiswaRegister.setCardBackgroundColor(resources.getColor(R.color.grey_font))
                        tietKataSandiUlangRegisterMahasiswa.setError("Kata sandi tidak sama!")
                        Log.i("KATASANDI", " === " + tietKataSandiRegisterMahasiswa.text.toString())
                    }
                } else{
                    cvDaftarSebagaiMahasiswaRegister.isEnabled = false
                    cvDaftarSebagaiMahasiswaRegister.setCardBackgroundColor(resources.getColor(R.color.grey_font))
                    tietKataSandiUlangRegisterMahasiswa.setError("Kata sandi tidak boleh kosong!")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        cvDaftarSebagaiMahasiswaRegister.setOnClickListener {
            register()
        }

        ivBackRegisterMahasiswa.setOnClickListener {
            startActivity(Intent(this, PilihanRegister::class.java))
            finish()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastCekEmail(message: String) {
        messageCek = message
    }

    override fun showLoadingCekEmail() {

    }

    override fun hideLoadingCekEmail() {

    }

    override fun successCekEmail(success: String) {
        if (success.equals("1")){
            successCek = success
        } else{
            tietEmailRegisterMahasiswa.setError("Gagal, Akun email sudah di gunakan!")
            Log.i("MESSAGECEK", " === " + messageCek)
            successCek = success
        }
    }

    override fun success(namaMahasiswa : String) {
        var intent = Intent(this, AktivasAkunMahasiswa::class.java)
        intent.putExtra("namaMahasiswa", namaMahasiswa)
        startActivity(intent)
        finish()
    }

    fun register(){
        val namaLengkap = tietNamaLengkapRegisterMahasiswa.text.toString()
        val email = tietEmailRegisterMahasiswa.text.toString()
        val password = tietKataSandiRegisterMahasiswa.text.toString()
        val repassword = tietKataSandiUlangRegisterMahasiswa.text.toString()

        if (repassword != password){
            Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_LONG).show()
        } else if (namaLengkap.isBlank()){
            Toast.makeText(this, "Nama Lengkap Harus Diisi!", Toast.LENGTH_LONG).show()
        } else if (email.isBlank()){
            Toast.makeText(this, "Email Harus Diisi!", Toast.LENGTH_LONG).show()
        } else if (password.isBlank()){
            Toast.makeText(this, "Password Harus Diisi!", Toast.LENGTH_LONG).show()
        } else{
            presenter?.regisMhs(namaLengkap, email, password)
        }

    }

    @Override
    override fun onBackPressed() {
        startActivity(Intent(this, PilihanRegister::class.java))
        finish()
    }
}