package com.fetch.challenge.receiptprocessor.controllers.models

import com.fetch.challenge.receiptprocessor.database.models.Receipt

data class ProcessReceiptResponse(
    val id: String,
    val retailer: String,
    val total: String
) {
    constructor(receipt: Receipt): this(
        receipt.externalId,
        receipt.retailer,
        receipt.total.toString()
    )
}
