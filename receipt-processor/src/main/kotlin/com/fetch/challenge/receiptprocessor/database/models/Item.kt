package com.fetch.challenge.receiptprocessor.database.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.Hibernate
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Item

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(externalId = $externalId , receipt = $receipt , shortDescription = $shortDescription , price = $price )"
    }
}
