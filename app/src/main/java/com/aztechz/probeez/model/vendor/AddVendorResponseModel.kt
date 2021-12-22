package com.aztechz.probeez.model.vendor

data class AddVendorResponseModel(
    val `data`: Data? = null,
    val message: String? = null,
    val statusCode: String? = null
)

data class Data(
    val __v: Int = 0,
    val _id: String? = null,
    val createDate: String? = null,
    val userId: String? = null,
    val vendorName: String? = null
)
