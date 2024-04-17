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
import com.sinarkelas.R
import com.sinarkelas.contract.MahasiswaEditProfileContract
import com.sinarkelas.presenter.MahasiswaEditProfilePresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import com.sinarkelas.util.StylingUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile_mahasiswa.*
import java.io.ByteArrayOutputStream

class EditProfileMahasiswa : AppCompatActivity(), MahasiswaEditProfileContract.mhsEditProfileView {

    var presenter : MahasiswaEditProfileContract.mhsEditProfilePresenter? = null
    var getNamaMhs : String? = ""
    var getEmailMhs : String? = ""
    var fotoProfile : String? = ""
    var idMahasiswaKirim : String? = ""

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
        setContentView(R.layout.activity_edit_profile_mahasiswa)

        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idmahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")
        idMahasiswaKirim = idmahasiswa

        presenter = MahasiswaEditProfilePresenter(this)

        stylingUtil = StylingUtil()

        getNamaMhs = intent.getStringExtra("namaMahasiswa")
        getEmailMhs = intent.getStringExtra("emailMahasiswa")
        fotoProfile = intent.getStringExtra("fotoMahasiswa")

        edtNamaInformasiMahasiswaProfileEdit.setText(getNamaMhs)
        edtEmailInformasiMahasiswaProfileEdit.setText(getEmailMhs)

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/" + fotoProfile).into(ivFotoMahasiswaProfileEdit)

        cvUploadFotoMahasiswaProfileEdit.setOnClickListener {
            dialogFoto()
        }

        cvEditMahasiswaProfile.setOnClickListener {
            editProfile()
        }

        stylingUtil?.montserratSemiBoldTextview(this, tvUploadFotoMahasiswaProfileEdit)
    }

    fun dialogFoto(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_pilihfotomahasiswa)

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val openKamera = dialog.findViewById<CardView>(R.id.cvOpenKameraMahasiswa)
        val openGallery = dialog.findViewById<CardView>(R.id.cvOpenGaleriMahasiswa)

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
            ivFotoMahasiswaProfileEdit.setImageBitmap(resized)
            gambarKu = resized
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val photo : Bitmap = MediaStore.Images.Media.getBitmap(this!!.contentResolver, data?.data)
            resized = Bitmap.createScaledBitmap(photo, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivFotoMahasiswaProfileEdit.setImageBitmap(resized)
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
        val namaMahasiswa = edtNamaInformasiMahasiswaProfileEdit.text.toString()
        val emailMahasiswa = edtEmailInformasiMahasiswaProfileEdit.text.toString()
        val passMahasiswa = edtPasswordInformasiMahasiswaProfileEdit.text.toString()

        var image = convertImageToStringForServer(gambarKu)

        if (namaMahasiswa.isBlank()){
            Toast.makeText(this, "Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        } else if (passMahasiswa.isBlank()){
            Toast.makeText(this, "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        } else{

            if (image != null){
                presenter?.editProfile(idMahasiswaKirim!!, namaMahasiswa, emailMahasiswa, passMahasiswa, image)
            } else{
                presenter?.editProfile(idMahasiswaKirim!!, namaMahasiswa, emailMahasiswa, passMahasiswa, "")
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun successEdit() {
        startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
        finish()
    }
}