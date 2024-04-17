package com.sinarkelas.contract

interface BuatKelasDosenContract {

    interface buatKelasView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun success()
    }

    interface buatKelasPresenter{
        fun buatKelas(namaMataKuliah : String, namaKelas : String, deskripsiKelas : String, idDosen : String, ikonKelas : String)
    }
}