package com.aztechz.probeez.model.reports

data class ReportRequestModel (
     val vendorId: String? = null,
     val userId: String? = null,
     val tasks: List<String>? = null
     )