package com.sinarkelas.ui.dashboardhome.fragment

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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sinarkelas.R
import com.sinarkelas.contract.DashboardHomeDosenContract
import com.sinarkelas.presenter.DashboardHomeDosenPresenter
import com.sinarkelas.ui.dashboardhome.GetListAgendaActivity
import com.sinarkelas.ui.dashboardhome.TambahAgendaActivity
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.frag_home_dosen.*

class FragHomeDosen : Fragment(), DashboardHomeDosenContract.dashboardView {

    private var presenter : DashboardHomeDosenContract.dashboardPresenter? = null
    private var stylingUtil : StylingUtil? = null
    private var progressDialog = CustomProgressDialog()

    companion object{
        fun newInstance() : FragHomeDosen {
            val fragment = FragHomeDosen()
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
        var v = inflater.inflate(R.layout.frag_home_dosen, container, false)

        presenter = DashboardHomeDosenPresenter(this)
        stylingUtil = StylingUtil()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var statusAkun : String? = sharedPreferences.getString("statusAkun", "")

        presenter?.getInfoDashboard(idDosen!!)

        fbTambahAgenda.setOnClickListener {
            if (statusAkun.equals("EXPIRED")){
                dialogExpired()
            } else{
                startActivity(Intent(requireContext(), TambahAgendaActivity::class.java))
            }
        }

        cvAgendaHomeDosen.setOnClickListener {
            if (statusAkun.equals("EXPIRED")){
                dialogExpired()
            } else{
                startActivity(Intent(requireContext(), GetListAgendaActivity::class.java))
            }
        }
    }

    override fun showLoading() {
        progressDialog.show(requireContext(), "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun dosenProfile(namaDosen: String, fotoProfile: String, jenjangPengajar: String) {
        tvNamaDosenHome.text = namaDosen
        tvJenjangDosenHome.text = jenjangPengajar
        tvHelloNamaDosenHome.setText(namaDosen).toString().substring(0, 10)

        stylingUtil?.montserratBoldTextview(requireActivity(), tvNamaDosenHome)
        stylingUtil?.montserratRegularTextview(requireActivity(), tvJenjangDosenHome)
        stylingUtil?.montserratBoldTextview(requireActivity(), tvHelloNamaDosenHome)

        Glide.with(requireActivity()).load("https://sinarcenter.id/sinarkelasimage/" + fotoProfile).into(ivFotoProfileDosenHome)
    }

    override fun jumlahKelas(jumlahKelasDosen: String) {
        tvJumlahKelasDosenHome.text = jumlahKelasDosen + " Kelas"
        tvJumlahKelasDosenHomeCard.text = jumlahKelasDosen

        stylingUtil?.montserratRegularTextview(requireActivity(), tvJumlahKelasDosenHome)
        stylingUtil?.montserratBoldTextview(requireActivity(), tvJumlahKelasDosenHomeCard)
    }

    override fun jumlahSiswa(jumlahSiswaDosen: String) {
        tvJumlahSiswaDosenHomeCard.text = jumlahSiswaDosen

        stylingUtil?.montserratBoldTextview(requireActivity(), tvJumlahSiswaDosenHomeCard)
    }

    override fun jumlahTugas(jumlahTugasDosen: String) {
        tvJumlahTugasDosenHomeCard.text = jumlahTugasDosen

        stylingUtil?.montserratBoldTextview(requireActivity(), tvJumlahTugasDosenHomeCard)
    }

    override fun jumlahAgenda(jumlahAgenda: String) {
        tvJumlahReminderDosenHomeCard.text = jumlahAgenda

        stylingUtil?.montserratBoldTextview(requireActivity(), tvJumlahReminderDosenHomeCard)
    }

    fun dialogExpired(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_expired)

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val infoExpired = dialog.findViewById<TextView>(R.id.tvJudulInfoExpiredAkunDialog)

        stylingUtil?.montserratBoldTextview(requireContext(), infoExpired)

        dialog.show()
    }
}