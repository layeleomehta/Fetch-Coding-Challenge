package com.fetch.challenge.receiptprocessor.services

import com.fetch.challenge.receiptprocessor.database.dao.ReceiptRepository
import com.fetch.challenge.receiptprocessor.database.models.Receipt
import org.springframework.stereotype.Service

@Service
class ReceiptService(
    private val idGeneratorService: IdGeneratorService,
    private val receiptRepository: ReceiptRepository
) {
    fun createReceipt(
        retailer: String,
        purchaseDate: String,
        purchaseTime: String,
        total: Float
    ): Receipt? {
        return receiptRepository.save(
            Receipt(
                null,
                idGeneratorService.generateId(),
                retailer,
                purchaseDate,
                purchaseTime,
                total,
                mutableSetOf(),
                null
            )
        )
    }

    fun findReceiptByExternalId(externalId: String): Receipt? {
        return receiptRepository.findByExternalId(externalId)
    }
}
