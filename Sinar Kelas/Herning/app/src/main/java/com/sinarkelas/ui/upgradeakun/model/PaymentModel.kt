package com.sinarkelas.ui.upgradeakun.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentModel(

    @SerializedName("idpay") var idPay : String?,
    @SerializedName("namapay") var namaPay : String?,
    @SerializedName("serverkey") var serverKey : String?,
    @SerializedName("clientkey") var clientKey : String?,
    @SerializedName("status") var status : String?
) : Parcelable{
    constructor() : this(null, null, null, null, null)
}