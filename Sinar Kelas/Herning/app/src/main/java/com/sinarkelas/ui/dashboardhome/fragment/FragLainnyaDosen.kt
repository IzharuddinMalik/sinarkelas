package com.sinarkelas.ui.dashboardhome.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
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
import com.sinarkelas.contract.DosenGetProfileContract
import com.sinarkelas.presenter.DosenGetProfilePresenter
import com.sinarkelas.ui.dashboardhome.profile.EditProfileDosen
import com.sinarkelas.ui.login.LoginActivity
import com.sinarkelas.ui.notifikasi.NotifikasiActivity
import com.sinarkelas.ui.upgradeakun.HistoriTagihanActivity
import com.sinarkelas.ui.upgradeakun.UpgradeAkunActivity
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.frag_lainnya_dosen_baru.*
import java.net.URLEncoder
import java.text.SimpleDateFormat

class FragLainnyaDosen : Fragment(), DosenGetProfileContract.dosenProfileView {

    var presenter : DosenGetProfileContract.dosenProfilePresenter? = null
    var namaDosenKirim : String? = ""
    var emailDosenKirim : String? = ""
    var fotoProfileKirim : String? = ""
    private var stylingUtil : StylingUtil? = null
    private var progressDialog = CustomProgressDialog()
    var tipeAkunDosen : String? = ""

    companion object{
        fun newInstance() : FragLainnyaDosen {
            val fragment = FragLainnyaDosen()
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
        var v = inflater.inflate(R.layout.frag_lainnya_dosen_baru, container, false)
        presenter = DosenGetProfilePresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        tipeAkunDosen = sharedPreferences.getString("tipeAkun", "")

        presenter?.getProfile(idDosen!!)

        stylingUtil = StylingUtil()

        stylingUtil?.montserratBoldTextview(requireContext(), tvEditInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvUpgradeAkunInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvHistoriPembayaranInformasiDosenProfile)
        stylingUtil?.montserratBoldTextview(requireContext(), tvLainnyaInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvKritikDanSaranInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvKebijakanPrivasiInformasiDosenProfile)
        stylingUtil?.montserratBoldTextview(requireContext(), tvContactUsInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvMailInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvWaInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvLogoutInformasiDosenProfile)

        cvEditInformasiDosenProfile.setOnClickListener {
            editProfile()
        }

        cvLogoutInformasiDosenProfile.setOnClickListener {

            var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.clear()
            editor.commit()
            requireActivity().finish()

            startActivity(Intent(context, LoginActivity::class.java))
        }

        cvKritikSaranInformasiDosenProfile.setOnClickListener {
            var intent = Intent(context, KritikdansaranActivity::class.java)
            intent.putExtra("emailpengirim", emailDosenKirim)
            intent.putExtra("namapengirim", namaDosenKirim)
            startActivity(intent)
        }

        cvWaInformasiDosenProfile.setOnClickListener {
            openWa()
        }

        cvMailInformasiDosenProfile.setOnClickListener {
            openMail()
        }

        cvUpgradeAkunInformasiDosenProfile.setOnClickListener {
            startActivity(Intent(requireContext(), UpgradeAkunActivity::class.java))
        }

        cvHistoriPembayaranInformasiDosenProfile.setOnClickListener {
            startActivity(Intent(requireContext(), HistoriTagihanActivity::class.java))
        }

        ivNotifikasiDosen.setOnClickListener {
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

    override fun getProfileDosen(namaDosen: String, emailDosen: String, fotoProfileDosen: String, endDate : String, statusAkun : String) {
        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(endDate))

        if (statusAkun.equals("EXPIRED")){
            tvAktiLangganan.text = "EXPIRED"
        } else{
            tvAktiLangganan.text = tanggalJoinFormat
        }

        tvNamaInformasiDosenProfile.text = namaDosen

        if (tipeAkunDosen.equals("0")){
            tvPaketAkunInformasiDosenProfile.text = "Akun Trial"
        } else if (tipeAkunDosen.equals("1")){
            tvPaketAkunInformasiDosenProfile.text = "Akun Basic"
        } else{
            tvPaketAkunInformasiDosenProfile.text = "Akun Pro"
        }

        stylingUtil?.montserratBoldTextview(requireContext(), tvNamaInformasiDosenProfile)
        stylingUtil?.montserratSemiBoldTextview(requireContext(), tvPaketAkunInformasiDosenProfile)
        stylingUtil?.montserratRegularTextview(requireContext(), tvJudulAktifLangganan)
        stylingUtil?.montserratBoldTextview(requireContext(), tvAktiLangganan)

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("statusAkun", statusAkun)
        editor.commit()

        namaDosenKirim = namaDosen
        emailDosenKirim = emailDosen
        fotoProfileKirim = fotoProfileDosen

        Glide.with(requireContext()).load("https://sinarcenter.id/sinarkelasimage/" + fotoProfileDosen).into(ivFotoDosenProfile)
    }

    fun editProfile(){
        var intent = Intent(context, EditProfileDosen::class.java)
        intent.putExtra("namaDosen", namaDosenKirim)
        intent.putExtra("emailDosen", emailDosenKirim)
        intent.putExtra("fotoDosen", fotoProfileKirim)

        Log.i("FOTOPROFILE", " === " + fotoProfileKirim)
        startActivity(intent)
    }

    fun openWa(){
        try {
            val packageManager: PackageManager = requireActivity().packageManager
            val i = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=" + "+6285732254043" + "&text=" + URLEncoder.encode("Hello Admin", "UTF-8")
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e("ERROR WHATSAPP", e.toString())
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
        }
    }

    fun openMail(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("official@sinarcenter.id"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(intent, ""))
    }
}