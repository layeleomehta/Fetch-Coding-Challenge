package com.fetch.challenge.receiptprocessor.database.models

import javax.persistence.*

@Table
@Entity
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    var id: Long?,
    var externalId: String,
    @ManyToOne(optional = false)
    @JoinColumn(name = "receipt_id", nullable = true)
    var receipt: Receipt,
    var shortDescription: String,
    var price: Float

)
