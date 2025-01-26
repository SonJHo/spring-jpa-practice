package jpabook.jpashop.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import jpabook.jpashop.domain.item.Item
import org.springframework.stereotype.Repository


@Repository
class ItemRepository(
    private val em : EntityManager
) {
    fun save(item : Item){
        if(item.id == null){
            em.persist(item)
        }else{
            em.merge(item)
        }
    }

    fun findOne( id : Long): Item? {
        return em.find(Item::class.java, id)
    }

    fun findAll(): MutableList<Item>? {
        return em.createQuery("select i from Item i", Item::class.java)
            .resultList
    }
}