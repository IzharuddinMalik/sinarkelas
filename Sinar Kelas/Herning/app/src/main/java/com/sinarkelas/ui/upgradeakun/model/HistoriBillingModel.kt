package com.sinarkelas.ui.upgradeakun.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoriBillingModel(

    @SerializedName("idbilling") var idBilling : String?,
    @SerializedName("kodebilling") var kodeBilling : String?,
    @SerializedName("iddosen") var idDosen : String?,
    @SerializedName("namadosen") var namaDosen : String?,
    @SerializedName("namapay") var namaPay : String?,
    @SerializedName("totalbill") var totalBill : String?,
    @SerializedName("datebill") var dateBill : String?,
    @SerializedName("statusbill") var statusBill : String?
) : Parcelable{
    constructor() : this(null, null, null, null, null, null, null, null)
}