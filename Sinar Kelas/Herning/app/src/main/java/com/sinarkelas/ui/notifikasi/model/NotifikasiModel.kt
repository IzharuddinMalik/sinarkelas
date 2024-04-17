package com.sinarkelas.ui.notifikasi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotifikasiModel(

    @SerializedName("idnotifiasi") var idNotifikasi : String?,
    @SerializedName("idadmin") var idAdmin : String?,
    @SerializedName("nama_admin") var namaAdmin : String?,
    @SerializedName("pesannotif") var pesanNotif : String?,
    @SerializedName("tanggalnotifikasi") var tanggalNotifikasi : String?,
    @SerializedName("gambarnotif") var gambarNotif : String?
) : Parcelable{
    constructor() : this(null, null, null, null, null, null)
}