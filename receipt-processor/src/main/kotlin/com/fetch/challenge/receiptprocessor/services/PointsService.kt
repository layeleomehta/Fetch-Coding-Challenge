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
        return processTotalIsMultipleOfQuarterPoints("3495.50")
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
        // gets the cents from the total.
        // First replaces the '.' with a comma in the total, and splits on the comma, assigning the second val to cents.
        // Kotlin has an issue splitting on a '.' because of regex, so this is a way around it.
        val cents = total.replace(".", ",").let { commaSeparatedTotal ->
            commaSeparatedTotal.split(",")[1]
        }

        // round dollar value occurs only when there are no cents.
        return if(cents == "00"){
            50
        } else{
            0
        }
    }

    // returns 25 points for a multiple of 0.25, 0 points otherwise.
    private fun processTotalIsMultipleOfQuarterPoints(total: String): Int {
        val cents = total.replace(".", ",").let { commaSeparatedTotal ->
            commaSeparatedTotal.split(",")[1]
        }

        // multiples of 0.25 only occur when cents are 00, 25, 50 or 75.
        return if(cents == "00" || cents == "25" || cents == "50" || cents == "75"){
            25
        } else{
            0
        }

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
