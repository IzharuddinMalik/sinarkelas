package com.sinarkelas.contract

import com.sinarkelas.ui.kelas.model.ListKomentarDiskusiModel

interface DetailDiskusiKelasContract {

    interface detailDiskusiView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getDetail(idInformasi : String, deskripsiInformasi : String, fileInformasi : String, namaLengkap : String, tanggal : String, statusHapusInformasi : String, fotoProfile : String)
        fun getKomentar(data : MutableList<ListKomentarDiskusiModel> = mutableListOf())
        fun successKirimKomentar()
    }

    interface detailDiskusiPresenter{
        fun sendGetDetail(idKelas: String, idInformasi: String, idDosen : String, idMahasiswa : String)
        fun sendGetListKomentar(idKelas: String, idInformasi: String)
        fun sendKomentar(idKelas: String, idDosen: String, idMahasiswa: String, komentar : String, idInformasi: String)
    }
}