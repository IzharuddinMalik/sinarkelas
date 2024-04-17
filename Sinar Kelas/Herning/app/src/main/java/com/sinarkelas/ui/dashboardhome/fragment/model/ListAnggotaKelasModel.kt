package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListAnggotaKelasModel(

    @SerializedName("namamahasiswa") var namaMahasiswa : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null
) : Parcelable{
    constructor() : this(null, null)
}