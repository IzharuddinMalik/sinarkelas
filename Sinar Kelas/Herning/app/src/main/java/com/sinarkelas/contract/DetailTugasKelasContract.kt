package com.sinarkelas.contract

import com.sinarkelas.ui.kelas.model.ListMahasiswaKumpulTugasModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface DetailTugasKelasContract {

    interface detailTugasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getDetailTugas(idTugas : String, judulTugas : String, deskripsiTugas : String, idDosen : String, namaDosen : String, tanggalUpload : String, idKelas : String, fileTugas : String, pointugas : String, tenggatWaktu : String, statusHapusTugas : String)
        fun successUploadTugas()
        fun getFileTugas(namaFile : String)
        fun getSelesaiTugas(selesaiTugas : String)
        fun getListMahasiswaKumpulTugas(data : MutableList<ListMahasiswaKumpulTugasModel> = mutableListOf())
    }

    interface detailTugasPresenter{
        fun sendDetailTugas(idTugas : String, idKelas: String, idDosen: String)
        fun sendUploadTugas(iddosen : RequestBody, idKelas: RequestBody, idMahasiswa : RequestBody, idTugas: RequestBody, fileTugas : MultipartBody.Part)
        fun getCekStatusTugasMahasiswa(idDosen : String, idKelas: String, idTugas: String, idMahasiswa: String)
        fun sendListTugasDikumpul(idDosen: String, idKelas: String, idTugas: String)
    }
}