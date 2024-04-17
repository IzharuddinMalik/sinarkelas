package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListInformasiModel(

    @SerializedName("idinformasi") var idInformasi : String? = null,
    @SerializedName("deskripsiinformasi") var deskripsiinformasi : String? = null,
    @SerializedName("idkelas") var idKelas : String? = null,
    @SerializedName("namalengkap") var namaLengkap : String? = null,
    @SerializedName("jumlahkomentar") var jumlahKomentar : String? = null,
    @SerializedName("create_at") var createAt : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null, null, null)
}