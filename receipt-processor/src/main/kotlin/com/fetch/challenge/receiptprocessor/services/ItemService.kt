package com.fetch.challenge.receiptprocessor.services

import com.fetch.challenge.receiptprocessor.database.dao.ItemRepository
import com.fetch.challenge.receiptprocessor.database.models.Item
import com.fetch.challenge.receiptprocessor.database.models.Receipt
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val idGeneratorService: IdGeneratorService,
    private val itemRepository: ItemRepository
) {
    fun createItem(shortDescription: String, price: Float, receipt: Receipt): Item? {
        return itemRepository.save(
            Item(
                null,
                idGeneratorService.generateId(),
                receipt,
                shortDescription,
                price
            )
        )
    }

}
