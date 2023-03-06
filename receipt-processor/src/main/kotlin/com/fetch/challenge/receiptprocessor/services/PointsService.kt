package com.fetch.challenge.receiptprocessor.services

import com.fetch.challenge.receiptprocessor.database.dao.PointsRepository
import com.fetch.challenge.receiptprocessor.database.models.Points
import com.fetch.challenge.receiptprocessor.database.models.Receipt
import org.springframework.stereotype.Service
import kotlin.math.ceil

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
        var points = 0

        // calculate and add points for alphanumeric character in retailer name.
        points += processAlphanumericRetailerPoints(receipt.retailer)

        // calculate and add points for round dollar amount with no cents.
        points += processTotalIsRoundDollarAmountPoints(receipt.total)

        // calculate and add points for multiple of 0.25.
        points += processTotalIsMultipleOfQuarterPoints(receipt.total)

        // calculate and add points for every two items on receipt.
        points += processEveryTwoItemsPoints(receipt.items.size)

        // calculate and add points for item description being a multiple of 3
        for(item in receipt.items){
            points += processItemDescriptionLengthMultipleOfThreePoints(item.shortDescription, item.price)
        }

        // calculate and add points for odd day in purchase date.
        points += processPurchaseDateDayOddPoints(receipt.purchaseDate)

        // calculate and add points for purchase time being between 2 pm and 4 pm.
        points += processPurchaseTimeBetweenTwoAndFourPMPoints(receipt.purchaseTime)

        return points
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

    // returns 5 points for every two items on the receipt.
    private fun processEveryTwoItemsPoints(receiptItemsSize: Int): Int {
        return if(receiptItemsSize % 2 == 0){
            // if the number of items is even
            // divide by two to get the number of times to add five points.
            (receiptItemsSize / 2) * 5
        } else{
            // if the number of items is odd
            // subtract one and divide by two to get the number of times to add five points.
            ((receiptItemsSize-1) / 2) * 5
        }
    }

    // if trimmed item description is a multiple of three, returns the point value of nearest rounded up integer
    // which is the result of multiplying the item price * 0.2. Returns 0 points otherwise.
    private fun processItemDescriptionLengthMultipleOfThreePoints(
        receiptItemDescription: String,
        receiptItemPrice: Float
    ): Int {
        // trim whitespace
        val trimmedDescriptionLength = receiptItemDescription.trim().length

        return if(trimmedDescriptionLength % 3 == 0) {
            ceil(receiptItemPrice * 0.2).toInt()
        } else {
            0
        }
    }

    // return 6 points if the day in the purchase date is odd. 0 points otherwise.
    private fun processPurchaseDateDayOddPoints(receiptPurchaseDate: String): Int {
        // assuming the date will always be: "YY-MM-DD" format.
        val days = receiptPurchaseDate.split("-").let { receiptPurchaseDateArray ->
            receiptPurchaseDateArray[2].toInt()
        }

        return if(days % 2 == 1) {
            6
        } else {
            0
        }
    }

    // return 10 points if time of purchase is between 14:00 and 16:00. 0 otherwise.
    private fun processPurchaseTimeBetweenTwoAndFourPMPoints(receiptPurchaseTime: String): Int {
        // assuming time will always be in 24h format: "HH:MM".
        val timeArray = receiptPurchaseTime.split(":")
        val hour = timeArray[0].toInt()
        val minute = timeArray[1].toInt()

        // hour can only be 14 or 15, never 16
        if(hour in 14..15){
            // if time is 14:00, return 0 because it is not AFTER 2 pm.
            return if(hour == 14 && minute == 0) {
                0
            } else {
                10
            }
        }

        // return 0 points because time is not between 2 pm and 4 pm.
        return 0
    }

}
