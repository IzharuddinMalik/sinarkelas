package com.sinarkelas.contract

interface MahasiswaPresensiContract {

    interface mahasiswaPresensiView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun successKirim()
    }

    interface mahasiswaPresensiPresenster{
        fun kirimPresensiMahasiswa(idPresensiKelas : String, idDosen : String, idKelas : String, idMahasiswa : String, statusPresensi : String)
    }
}