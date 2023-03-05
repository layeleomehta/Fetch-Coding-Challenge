package com.fetch.challenge.receiptprocessor.database.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PointsRepository: JpaRepository<PointsRepository, Long> {
}
