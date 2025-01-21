package jpabook.jpashop.domain

import jakarta.persistence.Embeddable


@Embeddable
class Address(
    val city: String?,
    val street: String?,
    val zipcode: String?
) {
    constructor() : this(null, null, null)
}
