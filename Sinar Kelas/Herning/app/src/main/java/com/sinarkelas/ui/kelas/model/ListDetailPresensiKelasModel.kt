package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListDetailPresensiKelasModel(

    @SerializedName("idpresensi") var idPresensi : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("statuspresensi") var statusPresensi : String? = null,
    @SerializedName("namamahasiswa") var namaMahasiswa : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null)
}