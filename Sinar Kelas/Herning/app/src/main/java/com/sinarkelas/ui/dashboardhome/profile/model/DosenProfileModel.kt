package com.sinarkelas.ui.dashboardhome.profile.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DosenProfileModel (

    @SerializedName("namadosen") var namaDosen : String? = null,
    @SerializedName("email") var emailDosen : String? = null,
    @SerializedName("password") var password : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null,
    @SerializedName("jenjangpengajar") var jenjangPengajar : String? = null,
    @SerializedName("enddate") var endDate : String? = null,
    @SerializedName("statusakun") var statusAkun : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null, null, null)
}