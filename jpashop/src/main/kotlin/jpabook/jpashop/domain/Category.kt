package jpabook.jpashop.domain

import jakarta.persistence.*
import jpabook.jpashop.domain.item.Item


@Entity
class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    val id :Long? = null

    var name :String? = null

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")], // 배열 형태로 작성
        inverseJoinColumns = [JoinColumn(name = "item_id")] // 배열 형태로 작성
    )
    var items = ArrayList<Item>()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent : Category? = null

    @OneToMany(mappedBy = "parent")
    val child  = ArrayList<Category>()

    fun addChildCategory(child : Category){
        this.child.add(child)
        child.parent = this

    }
}