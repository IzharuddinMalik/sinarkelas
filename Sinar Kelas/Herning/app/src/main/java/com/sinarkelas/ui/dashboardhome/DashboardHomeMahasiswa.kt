package com.sinarkelas.ui.dashboardhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.fragment.FragHomeDosen
import com.sinarkelas.ui.dashboardhome.fragment.FragHomeMahasiswa
import com.sinarkelas.ui.dashboardhome.fragment.FragKelasMahasiswa
import com.sinarkelas.ui.dashboardhome.fragment.FragLainnyaMahasiswa
import kotlinx.android.synthetic.main.activity_dashboard_home_mahasiswa.*

class DashboardHomeMahasiswa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_home_mahasiswa)

        bottomNavMhs.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragHomeMahasiswa.newInstance()
        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frameLayoutMhs, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_berandamhs -> {
                val fragment = FragHomeMahasiswa.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_kelasmhs -> {
                val fragment = FragKelasMahasiswa()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.nav_informasimhs -> {
//                val fragment = FragInformasiMahasiswa()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.nav_lainnyamhs -> {
                val fragment = FragLainnyaMahasiswa()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}