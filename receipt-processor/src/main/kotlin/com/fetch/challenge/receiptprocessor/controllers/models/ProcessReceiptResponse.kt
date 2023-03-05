package com.fetch.challenge.receiptprocessor.controllers.models

import com.fetch.challenge.receiptprocessor.database.models.Receipt

data class ProcessReceiptResponse(
    val id: String,
) {
    constructor(receipt: Receipt): this(
        receipt.externalId,
    )
}
