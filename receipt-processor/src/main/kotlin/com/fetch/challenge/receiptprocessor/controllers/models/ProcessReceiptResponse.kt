package com.fetch.challenge.receiptprocessor.controllers.models

import com.fetch.challenge.receiptprocessor.database.models.Item
import com.fetch.challenge.receiptprocessor.database.models.Receipt

data class ProcessReceiptResponse(
    val id: String,
    val retailer: String,
    val items: Set<Item>,
    val total: String
) {
    constructor(receipt: Receipt): this(
        receipt.externalId,
        receipt.retailer,
        receipt.items,
        receipt.total.toString()
    )
}
