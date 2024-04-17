package com.sinarkelas.ui.kelas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sinarkelas.R
import com.sinarkelas.ui.kelas.fragment.*
import kotlinx.android.synthetic.main.activity_dashboard_home_dosen.*

class DetailKelasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kelas)

        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragDiskusiKelas.newInstance()
        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frameLayoutDetailKelas, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_diskusikelas -> {
                val fragment = FragDiskusiKelas.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_presensi -> {
                val fragment = FragPresensiKelas()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_tugas -> {
                val fragment = FragTugasKelas()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_videocall -> {
                val fragment = FragVidCallKelas()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_anggotakelas -> {
                val fragment = FragAnggotaKelas()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}