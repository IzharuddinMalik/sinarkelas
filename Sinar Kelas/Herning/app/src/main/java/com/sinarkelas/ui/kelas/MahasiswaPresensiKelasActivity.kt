package com.sinarkelas.ui.kelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.MahasiswaPresensiContract
import com.sinarkelas.presenter.MahasiswaPresensiKelasPresenter
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_mahasiswa_presensi_kelas.*

class MahasiswaPresensiKelasActivity : AppCompatActivity(), MahasiswaPresensiContract.mahasiswaPresensiView {

    var idPresensi : String? = ""
    var tanggalPresensi : String? = ""
    var idPilihPresensi = arrayOf("0","1", "2")
    var pilihPresensi = arrayOf("Pilih Kehadiran","Hadir", "Izin")
    var idPilihPresensiKirim : String? = ""
    var presenter : MahasiswaPresensiContract.mahasiswaPresensiPresenster? = null
    var idDosenKirim : String? = ""
    var idMahasiswaKirim : String? = ""
    var idKelasKirim : String? = ""

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mahasiswa_presensi_kelas)

        presenter =
            MahasiswaPresensiKelasPresenter(this)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        idDosenKirim = idDosen
        idKelasKirim = idKelas
        idMahasiswaKirim = idMahasiswa

        idPresensi = intent.getStringExtra("idPresensiKelas")
        tanggalPresensi = intent.getStringExtra("tanggalPresensi")

        tvTanggalMahasiswaPresensi.text = tanggalPresensi

        var adapter = this?.let { ArrayAdapter(it, R.layout.inflater_spinner, pilihPresensi) }
        adapter?.setDropDownViewResource(R.layout.inflater_spinner)

        spinPilihPresensi.setAdapter(adapter)
        spinPilihPresensi.setSelection(0, false)
        spinPilihPresensi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idPilihPresensiKirim = idPilihPresensi[position]
                Log.i("IDPRESENSIKIRIM", " === " + idPilihPresensi)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                idPilihPresensiKirim = idPilihPresensi[0]
            }
        }

        cvKirimPresensiKelasMahasiswa.setOnClickListener {
            kirimPresensi()
        }

        ivBackPresensiKelas.setOnClickListener {
            var intent = Intent(this, DetailPresensiKelasActivity::class.java)
            intent.putExtra("idPresensiKelas", idPresensi)
            intent.putExtra("tanggalPresensi", tanggalPresensi)
            startActivity(intent)
            finish()
        }

    }

    fun kirimPresensi(){
        presenter?.kirimPresensiMahasiswa(idPresensi!!,idDosenKirim!!, idKelasKirim!!, idMahasiswaKirim!!, idPilihPresensiKirim!!)
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

    override fun successKirim() {
        var intent = Intent(this, DetailPresensiKelasActivity::class.java)
        intent.putExtra("idPresensiKelas", idPresensi)
        intent.putExtra("tanggalPresensi", tanggalPresensi)
        startActivity(intent)
        finish()
    }
}