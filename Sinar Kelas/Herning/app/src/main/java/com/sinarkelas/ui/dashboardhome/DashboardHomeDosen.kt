package com.sinarkelas.ui.dashboardhome

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.fragment.FragHomeDosen
import com.sinarkelas.ui.dashboardhome.fragment.FragKelasDosen
import com.sinarkelas.ui.dashboardhome.fragment.FragLainnyaDosen
import com.sinarkelas.ui.notifikasi.NotifikasiActivity
import com.sinarkelas.util.AlarmService
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_dashboard_home_dosen.*
import java.util.concurrent.TimeUnit

class DashboardHomeDosen : AppCompatActivity() {

    private var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_home_dosen)

        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragHomeDosen.newInstance()
        addFragment(fragment)

        stylingUtil = StylingUtil()

        startService(Intent(this, AlarmService::class.java))

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.frameLayout, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_beranda -> {
                val fragment = FragHomeDosen.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_kelas -> {
                val fragment = FragKelasDosen()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.nav_informasi -> {
//                val fragment = FragInformasiDosen()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.nav_lainnya -> {
                val fragment = FragLainnyaDosen()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @Override
    override fun onBackPressed() {
        dialogKeluar()
    }

    fun dialogKeluar(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_keluaraplikasi)

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val ya = dialog.findViewById<CardView>(R.id.cvDialogKeluar)
        val tidak = dialog.findViewById<TextView>(R.id.tvDialogTidakJadi)
        val tvKeluar = dialog.findViewById<TextView>(R.id.tvDialogKeluar)
        val tvTidakJadi = dialog.findViewById<TextView>(R.id.tvDialogTidakJadi)

        stylingUtil?.montserratSemiBoldTextview(this, tvKeluar)
        stylingUtil?.montserratSemiBoldTextview(this, tvTidakJadi)

        ya.setOnClickListener {
            this.finish()
        }

        tidak.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}