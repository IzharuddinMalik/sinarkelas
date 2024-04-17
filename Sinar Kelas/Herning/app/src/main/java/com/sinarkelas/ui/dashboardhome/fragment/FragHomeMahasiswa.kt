package com.sinarkelas.ui.dashboardhome.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sinarkelas.R
import com.sinarkelas.contract.DashboardHomeMahasiswaContract
import com.sinarkelas.presenter.DashboardHomeMahasiswaPresenter
import com.sinarkelas.ui.dashboardhome.GetListAgendaMahasiswaActivity
import com.sinarkelas.ui.dashboardhome.MateriUmumModel
import com.sinarkelas.ui.dashboardhome.TambahAgendaMahasiswaActivity
import com.sinarkelas.ui.dashboardhome.adapter.AdapterHomeMahasiswa
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.frag_home_mahasiswa.*

class FragHomeMahasiswa : Fragment(), DashboardHomeMahasiswaContract.dashboardView {

    var presenter : DashboardHomeMahasiswaContract.dashboardPresenter? = null
    private var progressDialog = CustomProgressDialog()

    companion object{
        fun newInstance() : FragHomeMahasiswa {
            val fragment = FragHomeMahasiswa()
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
        var v = inflater.inflate(R.layout.frag_home_mahasiswa, container, false)
        presenter = DashboardHomeMahasiswaPresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        presenter?.sendDashboard(idMahasiswa!!)

        fbTambahAgendaMahasiswa.setOnClickListener {
            startActivity(Intent(requireContext(), TambahAgendaMahasiswaActivity::class.java))
        }

        cvAgendaHomeMahasiswa.setOnClickListener {
            startActivity(Intent(requireContext(), GetListAgendaMahasiswaActivity::class.java))
        }
    }

    override fun showLoading() {
        progressDialog.show(requireContext(), "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun showToast(message: String) {

    }

    override fun getHeaderMahasiswa(
        namaMahasiswa: String,
        emailMahasiswa: String,
        password: String,
        fotoProfile: String
    ) {
        tvNamaMahasiswaHome.text = namaMahasiswa
        tvHelloNamaMahasiswaHome.text = namaMahasiswa

        Glide.with(requireContext()).load("https://sinarcenter.id/sinarkelasimage/"+fotoProfile).into(ivFotoProfileMahasiswaHome)
    }

    override fun getJumlahKelas(jumlahKelas: String) {
        tvJumlahKelasMahasiswaHomeCard.text = jumlahKelas
    }

    override fun getJumlahTugas(jumlahTugas: String) {
        tvJumlahTugasMahasiswaHomeCard.text = jumlahTugas
    }

    override fun getJumlahAgenda(jumlahAgenda: String) {
        tvJumlahReminderMahasiswaHomeCard.text = jumlahAgenda
    }

}