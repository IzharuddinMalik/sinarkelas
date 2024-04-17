package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListKelasModel(

    @SerializedName("idkelas") var idKelas : String? = null,
    @SerializedName("namakelas") var namaKelas : String? = null,
    @SerializedName("deskripsikelas") var deskripsiKelas : String? = null,
    @SerializedName("iddosen") var idDosen : String? = null,
    @SerializedName("namadosen") var namaDosen : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null)
}