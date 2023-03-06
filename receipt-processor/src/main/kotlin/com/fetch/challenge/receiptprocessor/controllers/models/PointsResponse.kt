package com.fetch.challenge.receiptprocessor.controllers.models

import com.fetch.challenge.receiptprocessor.database.models.Points


data class PointsResponse(
    val points: Int
) {
    constructor(pointsEntry: Points): this(
        pointsEntry.points
    )
}
