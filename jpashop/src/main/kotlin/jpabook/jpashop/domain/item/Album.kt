package jpabook.jpashop.domain.item

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity


@Entity
@DiscriminatorValue("A")
class Album : Item() {
    var artist : String? = null
    var etc : String? = null
}