package com.aztechz.probeez.model.vendor

data class VendorListResponseModel(
    val `data`: List<VendorListData?>? = null,
    val message: String? = null,
    val statusCode: String? = null
)
    data class VendorListData(
        val __v: Int? = 0,
        val _id: String? = null,
        val createDate: String? = null,
        val userId: String? = null,
        val vendorName: String? = null

    ) {
        override fun toString(): String {
            return vendorName.toString()
        }
    }
