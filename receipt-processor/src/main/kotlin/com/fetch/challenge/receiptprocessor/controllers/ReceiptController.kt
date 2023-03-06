package com.fetch.challenge.receiptprocessor.controllers

import com.fetch.challenge.receiptprocessor.controllers.models.ProcessReceiptBody
import com.fetch.challenge.receiptprocessor.controllers.models.ProcessReceiptResponse
import com.fetch.challenge.receiptprocessor.controllers.models.ReceiptResponse
import com.fetch.challenge.receiptprocessor.services.ItemService
import com.fetch.challenge.receiptprocessor.services.PointsService
import com.fetch.challenge.receiptprocessor.services.ReceiptService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/receipts")
@CrossOrigin
class ReceiptController(
    private val receiptService: ReceiptService,
    private val itemService: ItemService,
    private val pointsService: PointsService
) {

    @GetMapping("/{id}")
    fun getReceiptById(@PathVariable id: String): ResponseEntity<Any> {
        return receiptService.findReceiptByExternalId(id)?.let {receipt ->
            ResponseEntity.ok(ReceiptResponse(receipt))
        } ?: ResponseEntity.notFound().build()
    }

    // processes a receipt by creating a unique Receipt entry in the DB, as well as a unique Item entry for
    // every item purchased in the receipt, with reference to the Receipt entry created.
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Receipt is invalid")
    }

    // Calculates and saves the amount of points a receipt is worth from a specified receipt id.
    @GetMapping("/{id}/points")
    fun calculateAndSavePoints(@PathVariable id: String): ResponseEntity<Any> {
        return receiptService.findReceiptByExternalId(id)?.let {receipt ->
            pointsService.processPoints(receipt)?.let {
                return ResponseEntity.ok().build()
            }
        } ?: ResponseEntity.notFound().build()
    }

}
