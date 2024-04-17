package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AgendaModel(

    @SerializedName("idagenda") var idAgenda : String?,
    @SerializedName("judulagenda") var judulAgenda : String?,
    @SerializedName("deskripsiagenda") var deskripsiAgenda : String?,
    @SerializedName("tanggalagenda") var tanggalAgenda : String?,
    @SerializedName("jamagenda") var jamAgenda : String?,
    @SerializedName("tanggaldibuat") var tanggalDibuat : String?
) : Parcelable{
    constructor() : this(null, null, null, null, null, null)
}