package com.fetch.challenge.receiptprocessor.database.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
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
    var total: Float,
    @JsonIgnore
    @OneToMany(mappedBy = "receipt", orphanRemoval = true, cascade = [CascadeType.ALL])
    var items: MutableSet<Item>,
    @JsonIgnore
    @OneToOne(mappedBy = "receipt", orphanRemoval = true, cascade = [CascadeType.ALL])
    var points: Points?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Receipt

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(externalId = $externalId , retailer = $retailer , purchaseDate = $purchaseDate , purchaseTime = $purchaseTime , total = $total )"
    }
}
