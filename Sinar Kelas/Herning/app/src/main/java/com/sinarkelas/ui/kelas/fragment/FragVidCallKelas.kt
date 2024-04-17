package com.sinarkelas.ui.kelas.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sinarkelas.R
import com.sinarkelas.ui.videocallgroup.activities.LoginActivity
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.frag_vidcallkelas.*

class FragVidCallKelas : Fragment() {

    private var stylingUtil : StylingUtil? = null

    companion object{
        fun newInstance() : FragVidCallKelas {
            val fragment = FragVidCallKelas()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.frag_vidcallkelas, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var tipeAkun : String? = sharedPreferences.getString("tipeAkun", "")

        llJoinRoomVidCall.setOnClickListener {
            if (tipeAkun.equals("0")){
                // User tipe akun trial
                dialogUpgradePro()
            } else if (tipeAkun.equals("1")){
                // User tipe akun basic
                dialogUpgradePro()
            } else{
                // User tipe akun pro
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

    }

    fun dialogComingSoon(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_comingsoon)

        stylingUtil = StylingUtil()

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        dialog.show()
    }

    fun dialogUpgradePro(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_upgradepro)

        stylingUtil = StylingUtil()

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        var tvInfo = dialog.findViewById<TextView>(R.id.tvJudulUpgradeAkunDialogVideo)
        stylingUtil?.montserratBoldTextview(requireContext(), tvInfo)

        dialog.show()
    }

}