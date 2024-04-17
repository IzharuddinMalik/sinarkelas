package com.sinarkelas.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import kotlinx.android.synthetic.main.activity_pilihan_register.*

class PilihanRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihan_register)

        llDaftarSebagaiDosenPilihanRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        cvDaftarSebagaiMahasiswaPilihanRegister.setOnClickListener {
            startActivity(Intent(this, RegisterMahasiswaActivity::class.java))
        }
    }
}