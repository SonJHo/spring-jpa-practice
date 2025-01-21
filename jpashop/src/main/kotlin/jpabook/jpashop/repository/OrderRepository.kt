package jpabook.jpashop.repository

import ch.qos.logback.core.util.StringUtil
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.Order
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils


@Repository
class OrderRepository(
    private val em: EntityManager,
) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order? {
        return em.find(Order::class.java, id)
    }

    fun findAll(orderSearch: OrderSearch): MutableList<Order>? {

        val jpql = "select o from Order o join o.member m"

        return em.createQuery(
            jpql, Order::class.java
        ).setMaxResults(1000).resultList


    }

    fun findAllByCriteria(orderSearch: OrderSearch): MutableList<Order>? {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(Order::class.java)
        val o = cq.from(Order::class.java)
        val m = o.join<Any, Any>("member", JoinType.INNER)
        val criteria: MutableList<Predicate> = mutableListOf()

        if (orderSearch.orderStatus != null) {
            val status = cb.equal(o.get<String>("status"), orderSearch.orderStatus)
            criteria.add(status)
        }

        if(StringUtils.hasText(orderSearch.memberName)){
            val name = cb.like(m.get<String>("name"), "%" + orderSearch.memberName + "%")
            criteria.add(name)
        }
        cq.where(cb.and(*criteria.toTypedArray()))
        val query = em.createQuery(cq).setMaxResults(1000)
        return query.resultList
    }
}