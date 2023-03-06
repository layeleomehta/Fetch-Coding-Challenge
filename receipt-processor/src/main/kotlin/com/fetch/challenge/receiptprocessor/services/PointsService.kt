package com.fetch.challenge.receiptprocessor.services

import com.fetch.challenge.receiptprocessor.database.dao.PointsRepository
import com.fetch.challenge.receiptprocessor.database.models.Points
import com.fetch.challenge.receiptprocessor.database.models.Receipt
import org.springframework.stereotype.Service
import java.util.regex.Pattern

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
        return processTotalIsRoundDollarAmountPoints("35.35")
    }

    // returns one point for every alphanumeric character in the retailer name.
    private fun processAlphanumericRetailerPoints(retailer: String): Int {
        var points = 0

        // iterates through every character, checks if it's alphanumeric and appends point if alphanumeric.
        for (c in retailer)
        {
            if (c in 'A'..'Z' || c in 'a'..'z' || c in '0'..'9') {
                points += 1
            }
        }

        return points
    }

    // returns 50 points for round dollar amount, 0 points otherwise.
    private fun processTotalIsRoundDollarAmountPoints(total: String): Int {
        // gets the cents by splitting on the dot in the 'total' string.
        val cents = total
        println(cents)
        return 0
        // round dollar value occurs only when there are no cents.
//        return if(cents == "00"){
//            50
//        } else{
//            0
//        }
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
