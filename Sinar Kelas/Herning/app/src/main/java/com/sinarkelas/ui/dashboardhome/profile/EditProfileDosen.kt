package com.sinarkelas.ui.dashboardhome.profile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.sinarkelas.R
import com.sinarkelas.contract.DosenEditProfileContract
import com.sinarkelas.presenter.DosenEditProfilePresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_edit_profile_dosen.*
import java.io.ByteArrayOutputStream

class EditProfileDosen : AppCompatActivity(), DosenEditProfileContract.dosenEditView {

    var presenter : DosenEditProfileContract.dosenEditPresenter? = null
    var getNamaDosen : String? = ""
    var getEmailDosen : String? = ""
    var fotoProfileDosen : String? = ""
    var idDosenKirim : String? = ""
    var CAMERA_REQUEST : Int? = 0
    var gambarKu : Bitmap? = null

    var stylingUtil : StylingUtil? = null

    //image pick code
    private val IMAGE_PICK_CODE = 1000;
    //Permission code
    private val PERMISSION_CODE = 1001;
    var resized : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_dosen)

        presenter = DosenEditProfilePresenter(this)

        stylingUtil = StylingUtil()

        getNamaDosen = intent.getStringExtra("namaDosen")
        getEmailDosen = intent.getStringExtra("emailDosen")
        fotoProfileDosen = intent.getStringExtra("fotoDosen")

        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        idDosenKirim = idDosen

        edtNamaInformasiDosenProfileEdit.setText(getNamaDosen)
        edtEmailInformasiDosenProfileEdit.setText(getEmailDosen)

        Glide.with(this).load("https://sinarcenter.id/sinarkelasimage/" + fotoProfileDosen).into(ivFotoDosenProfileEdit)

        cvEditDosenProfile.setOnClickListener {
            editProfile()
        }

        cvUploadFotoDosenProfileEdit.setOnClickListener {
            dialogFoto()
        }

        stylingUtil?.montserratSemiBoldTextview(this, tvUploadFotoDosenProfileEdit)
    }

    fun dialogFoto(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_pilihfotodosen)

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val openKamera = dialog.findViewById<CardView>(R.id.cvOpenKameraDosen)
        val openGallery = dialog.findViewById<CardView>(R.id.cvOpenGaleriDosen)

        openKamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST!!)
        }

        openGallery.setOnClickListener {
            openImageGallery()
        }

        dialog.show()
    }

    fun openImageGallery(){
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    openImageGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //untuk mengambil gambar hasil capture camera tadi kita harus override onActivityResult dan membaca resultCode apakah sukses dan requestCode apakah dari Camera_Request
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val mphoto = (data!!.extras!!["data"] as Bitmap?)!!
            //panggil method uploadImage
            resized = Bitmap.createScaledBitmap(mphoto, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivFotoDosenProfileEdit.setImageBitmap(resized)
            gambarKu = resized
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val photo : Bitmap = MediaStore.Images.Media.getBitmap(this!!.contentResolver, data?.data)
            resized = Bitmap.createScaledBitmap(photo, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivFotoDosenProfileEdit.setImageBitmap(resized)
            gambarKu = resized
        }
    }

    fun convertImageToStringForServer(imageBitmap: Bitmap?): String? {
        val stream = ByteArrayOutputStream()
        return if (imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } else {
            null
        }
    }

    fun editProfile(){
        val namaDosen = edtNamaInformasiDosenProfileEdit.text.toString()
        val emailDosen = edtEmailInformasiDosenProfileEdit.text.toString()
        val passDosen = edtPasswordInformasiDosenProfileEdit.text.toString()
        val jenjangPengajar = edtJenjangInformasiPengajarProfileEdit.text.toString()

        var image = convertImageToStringForServer(gambarKu)

        if (namaDosen.isBlank()){
            edtNamaInformasiDosenProfileEdit.setError("Nama tidak boleh kosong")
        } else if (passDosen.isBlank()){
            edtPasswordInformasiDosenProfileEdit.setError("Password tidak boleh kosong")
        } else{

            if (image != null){
                presenter?.editProfile(idDosenKirim!!, namaDosen, emailDosen, passDosen, image, jenjangPengajar)
            } else{
                presenter?.editProfile(idDosenKirim!!, namaDosen, emailDosen, passDosen, "", jenjangPengajar)
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    override fun successEdit() {
        var intent = Intent(this, DashboardHomeDosen::class.java)
        startActivity(intent)
    }
}