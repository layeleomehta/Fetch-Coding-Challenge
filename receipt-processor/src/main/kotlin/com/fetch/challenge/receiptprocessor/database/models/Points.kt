package com.fetch.challenge.receiptprocessor.database.models

import org.hibernate.Hibernate
import javax.persistence.*

@Table
@Entity
data class Points(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    var id: Long?,
    var externalId: String,
    @OneToOne(optional = false)
    @JoinColumn(name = "receipt_id", nullable = true)
    var receipt: Receipt,
    var points: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Points

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(externalId = $externalId , receipt = $receipt , points = $points )"
    }
}
