package jpabook.jpashop.domain.item

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@Entity
@DiscriminatorValue("B")
class Book : Item() {

    var author :String? = null
    var isbn :String? = null
}