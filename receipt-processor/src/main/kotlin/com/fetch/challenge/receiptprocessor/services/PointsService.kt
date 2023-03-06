package com.fetch.challenge.receiptprocessor.services

import com.fetch.challenge.receiptprocessor.database.dao.PointsRepository
import com.fetch.challenge.receiptprocessor.database.models.Points
import com.fetch.challenge.receiptprocessor.database.models.Receipt
import org.springframework.stereotype.Service

@Service
class PointsService(
    private val idGeneratorService: IdGeneratorService,
    private val pointsRepository: PointsRepository
) {
    fun createPointsEntry(points: Int, receipt: Receipt): Points? {
        return pointsRepository.save(
            Points(
                null,
                idGeneratorService.generateId(),
                receipt,
                points
            )
        )
    }

    // receives the receipt object and determines how many points the receipt is worth.
    fun processPoints(receipt: Receipt): Int? {
        return null
    }

    private fun processAlphanumericRetailerPoints() {

    }

    private fun processTotalIsRoundDollarAmountPoints() {

    }

    private fun processTotalIsMultipleOfQuarterPoints() {

    }

    private fun processEveryTwoItemsPoints() {

    }

    private fun processItemDescriptionLengthMultipleOfThreePoints() {

    }

    private fun processPurchaseDateDayOddPoints() {

    }

    private fun processPurchaseTimeBetweenTwoAndFourPMPoints() {

    }

}
