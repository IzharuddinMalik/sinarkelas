package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListPresensiKelasModel (
    @SerializedName("idpresensi") var idPresensi : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null
) : Parcelable{
    constructor() : this(null, null)
}