package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMateriUmumModel(

    @SerializedName("idmateriumum") var idMateriUmum : String? = null,
    @SerializedName("judulmateri") var judulMateri : String? = null,
    @SerializedName("filemateri") var fileMateri : String? = null,
    @SerializedName("sumbermateri") var sumberMateri : String? = null,
    @SerializedName("created_at") var tanggal : String? = null,
    @SerializedName("namaadmin") var namaAdmin : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null, null)
}