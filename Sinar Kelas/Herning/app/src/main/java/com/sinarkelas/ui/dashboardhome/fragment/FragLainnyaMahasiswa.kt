package com.sinarkelas.ui.dashboardhome.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sinarkelas.KritikdansaranActivity
import com.sinarkelas.R
import com.sinarkelas.contract.MahasiswaGetProfileContract
import com.sinarkelas.presenter.MahasiswaGetProfilePresenter
import com.sinarkelas.ui.dashboardhome.profile.EditProfileDosen
import com.sinarkelas.ui.dashboardhome.profile.EditProfileMahasiswa
import com.sinarkelas.ui.login.LoginActivity
import com.sinarkelas.ui.notifikasi.NotifikasiActivity
import com.sinarkelas.util.CustomProgressDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_lainnya_mahasiswa.*

class FragLainnyaMahasiswa : Fragment(), MahasiswaGetProfileContract.mhsProfileView {

    var presenter : MahasiswaGetProfileContract.mhsProfilePresenter? = null
    var emailMahasiswa : String? = ""
    var namaMahasiswa : String? = ""
    var fotoProfileMahasiswa : String? = ""
    private var progressDialog = CustomProgressDialog()

    companion object{
        fun newInstance() : FragLainnyaMahasiswa {
            val fragment = FragLainnyaMahasiswa()
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
        var v = inflater.inflate(R.layout.frag_lainnya_mahasiswa, container, false)
        presenter = MahasiswaGetProfilePresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idmahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        presenter?.getProfile(idmahasiswa!!)

        cvLogoutInformasiMahasiswaProfile.setOnClickListener {

            var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
            requireActivity().finish()

            startActivity(Intent(context, LoginActivity::class.java))
        }

        cvKritikSaranInformasiMahasiswaProfile.setOnClickListener {
            var intent = Intent(context, KritikdansaranActivity::class.java)
            intent.putExtra("emailpengirim", emailMahasiswa)
            intent.putExtra("namapengirim", namaMahasiswa)
            startActivity(intent)
        }

        cvEditInformasiMahasiswaProfile.setOnClickListener {
            editMahasiswa()
        }

        ivNotifikasiMahasiswa.setOnClickListener {
            startActivity(Intent(requireContext(), NotifikasiActivity::class.java))
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog.show(requireContext(), "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun getProfileMhs(namaMhs: String, emailMhs: String, fotoProfile: String) {
        tvNamaInformasiMahasiswaProfile.text = namaMhs
        tvEmailInformasiMahasiswaProfile.text = emailMhs

        namaMahasiswa = namaMhs
        emailMahasiswa = emailMhs
        fotoProfileMahasiswa = fotoProfile

        Glide.with(requireContext()).load("https://sinarcenter.id/sinarkelasimage/" + fotoProfile).into(ivFotoMahasiswaProfile)
    }

    fun editMahasiswa(){
        var intent = Intent(context, EditProfileMahasiswa::class.java)
        intent.putExtra("namaMahasiswa", namaMahasiswa)
        intent.putExtra("emailMahasiswa", emailMahasiswa)
        intent.putExtra("fotoMahasiswa", fotoProfileMahasiswa)

        Log.i("FOTOPROFILE", " === " + fotoProfileMahasiswa)
        startActivity(intent)
    }
}