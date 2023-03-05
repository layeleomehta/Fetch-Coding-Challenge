package com.fetch.challenge.receiptprocessor.database.models

import javax.persistence.*

@Table
@Entity
data class Receipt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    var id: Long?,
    var externalId: String,
    var retailer: String,
    var purchaseDate: String,
    var purchaseTime: String,
    var total: Float
)
