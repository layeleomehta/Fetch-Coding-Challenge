package com.fetch.challenge.receiptprocessor.database.dao

import com.fetch.challenge.receiptprocessor.database.models.Points
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PointsRepository: JpaRepository<Points, Long> {
}
