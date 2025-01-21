package jpabook.jpashop.domain

import jakarta.persistence.*


@Entity
class Delivery {


    @Id @GeneratedValue
    @Column(name = "delivery_id")
    val id :Long? = null

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order :Order? = null
    @Embedded
    var address :Address? = null

    @Enumerated(EnumType.STRING)
    var status :DeliveryStatus? = null
}
