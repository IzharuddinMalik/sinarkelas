package com.sinarkelas.ui.kelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.EditKelasContract
import com.sinarkelas.presenter.EditKelasPresenter
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_edit_kelas.*

class EditKelasActivity : AppCompatActivity(), EditKelasContract.editKelasView {

    var namaMataKuliah : String? = ""
    var namaKelas : String? = ""
    var deskripsiKelas : String? = ""

    var namaMataKuliahEdit : String? = ""
    var namaKelasEdit : String? = ""
    var deskripsiKelasEdit : String? = ""

    var idDosenEdit : String? = ""
    var idKelasEdit : String? = ""

    private var presenterEditKelas : EditKelasContract.editKelasPresenter? = null

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_kelas)

        presenterEditKelas = EditKelasPresenter(this)

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")

        idDosenEdit = idDosen
        idKelasEdit = idKelas

        namaMataKuliah = intent.getStringExtra("namaMataKuliah")
        namaKelas = intent.getStringExtra("namaKelas")
        deskripsiKelas = intent.getStringExtra("deskripsiKelas")

        tietNamaMataKuliahEditKelas.setText(namaMataKuliah)
        tietNamaKelasEditKelas.setText(namaKelas)
        tietDeskripsiKelasEditKelas.setText(deskripsiKelas)

        llEditKelas.setOnClickListener {
            if (!checkMataKuliah()||!checkNamaKelas()||!checkDeskripsiKelas()){
                Toast.makeText(this, "Harap periksa isian data anda kembali!", Toast.LENGTH_LONG).show()
            } else{
                editKelas()
            }
        }
    }

    fun editKelas(){
        presenterEditKelas?.sendEditKelas(idKelasEdit!!, namaMataKuliahEdit!!, namaKelasEdit!!, deskripsiKelasEdit!!, idDosenEdit!!)
    }

    fun checkMataKuliah() : Boolean{

        namaMataKuliahEdit = tietNamaMataKuliahEditKelas.text.toString()

        if (namaMataKuliahEdit!!.isEmpty()){
            tietNamaMataKuliahEditKelas.setError("Tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkNamaKelas() : Boolean{

        namaKelasEdit = tietNamaKelasEditKelas.text.toString()

        if (namaKelasEdit!!.isEmpty()){
            tietNamaKelasEditKelas.setError("Tidak boleh kosong!")
            return false
        }

        return true
    }

    fun checkDeskripsiKelas() : Boolean{

        deskripsiKelasEdit = tietDeskripsiKelasEditKelas.text.toString()

        if (deskripsiKelasEdit!!.isEmpty()){
            tietDeskripsiKelasEditKelas.setError("Tidak boleh kosong!")
            return false
        }

        return true
    }

    override fun showToastEditKelas(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoadingEditKelas() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoadingEditKelas() {
        progressDialog.dialog.dismiss()
    }

    override fun successEditKelas() {
        startActivity(Intent(this, DetailKelasActivity::class.java)).apply {
            finish()
        }
    }
}