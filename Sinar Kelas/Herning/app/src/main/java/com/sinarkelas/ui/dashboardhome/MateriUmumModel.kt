package com.sinarkelas.ui.dashboardhome

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MateriUmumModel(

    @SerializedName("idmateriumum") var idMateriUmum : String? = null,
    @SerializedName("judulmateri") var judulMateriUmum : String? = null,
    @SerializedName("filemateri") var fileMateri : String? = null,
    @SerializedName("sumbermateri") var sumberMateri : String? = null,
    @SerializedName("created_at") var createdAt : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null)
}