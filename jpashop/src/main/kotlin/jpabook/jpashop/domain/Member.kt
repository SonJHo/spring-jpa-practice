package jpabook.jpashop.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotEmpty


@Entity
class Member (
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,

    var name: String? = null,

    @Embedded
    var address: Address? = null,


    @JsonIgnore
    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()
    )