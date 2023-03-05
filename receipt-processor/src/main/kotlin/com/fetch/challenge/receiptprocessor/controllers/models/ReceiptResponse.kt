package com.fetch.challenge.receiptprocessor.controllers.models

import com.fetch.challenge.receiptprocessor.database.models.Receipt

data class ReceiptResponse(
    val id: String,
    val retailer: String,
    val items: List<ReceiptItem>,
    val total: String
) {
    constructor(receipt: Receipt): this(
        receipt.externalId,
        receipt.retailer,
        receipt.items.map { ReceiptItem(it.shortDescription, it.price.toString()) },
        receipt.total.toString()
    )
}
