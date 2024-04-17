package com.sinarkelas.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var idDosen : String? = ""
    var idMahasiswa : String? = ""
    var statusUser : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        llLoginSebagaiDosenPilihanLogin.setOnClickListener {
            startActivity(Intent(this, LoginDosenActivity::class.java))
        }

        cvLoginSebagaiMahasiswaPilihanLogin.setOnClickListener {
            startActivity(Intent(this, LoginMahasiswaActivity::class.java))
        }

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")
        idMahasiswa = sharedPreferences.getString("idMahasiswa", "")
        statusUser = sharedPreferences.getString("statusUser", "")
        Log.i("idDosen", " === $idDosen")
        Log.i("idMahasiswa", " === $idMahasiswa")
        Log.i("STATUSUSER", " === $statusUser")

        if (statusUser == "1") {
            if (idDosen != "") {
                startActivity(Intent(this, DashboardHomeDosen::class.java))
                finish()
            }
        } else {
            if (idMahasiswa != "") {
                startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
                finish()
            }
        }
    }
}