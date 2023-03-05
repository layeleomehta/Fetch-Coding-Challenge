package com.fetch.challenge.receiptprocessor.database.dao

import com.fetch.challenge.receiptprocessor.database.models.Receipt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository: JpaRepository<Receipt, Long> {
}
