package com.fetch.challenge.receiptprocessor.controllers

import com.fetch.challenge.receiptprocessor.controllers.models.ProcessReceiptBody
import com.fetch.challenge.receiptprocessor.controllers.models.ProcessReceiptResponse
import com.fetch.challenge.receiptprocessor.services.ItemService
import com.fetch.challenge.receiptprocessor.services.ReceiptService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/receipts")
@CrossOrigin
class ReceiptController(
    private val receiptService: ReceiptService,
    private val itemService: ItemService
) {
    @PostMapping("/process")
    fun processReceipt(@RequestBody requestBody: ProcessReceiptBody): ResponseEntity<Any> {
        receiptService.createReceipt(
            requestBody.retailer,
            requestBody.purchaseDate,
            requestBody.purchaseTime,
            requestBody.total.toFloat()
        )?.let { receipt ->
            // create an item entry for each item, with reference to the created receipt.
            for(item in requestBody.items) {
                itemService.createItem(
                    item.shortDescription,
                    item.price.toFloat(),
                    receipt
                )
            }
            return ResponseEntity.ok(ProcessReceiptResponse(receipt))
        }
        return ResponseEntity.ok("receipt model goes here")
    }

}
