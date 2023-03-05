package com.fetch.challenge.receiptprocessor.controllers.models

data class ProcessReceiptBody(
    val retailer: String,
    val purchaseDate: String,
    val purchaseTime: String,
    val items: List<ReceiptItem>,
    val total: String
)

data class ReceiptItem(
    val shortDescription: String,
    val price: String
)
