package jpabook.jpashop.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany


@Entity
class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null
    var name: String? = null

    @Embedded
    var address: Address? = null

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()
}