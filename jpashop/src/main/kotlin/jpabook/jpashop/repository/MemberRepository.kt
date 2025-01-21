package jpabook.jpashop.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import jpabook.jpashop.domain.Member
import org.springframework.stereotype.Repository


@Repository
class MemberRepository(

    val em : EntityManager
) {

    fun save(member: Member){
        em.persist(member)
    }
    fun findOne(id : Long): Member? {
        return em.find(Member::class.java, id)
    }

    fun findAll(): MutableList<Member>? {

        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }

    fun findByName(name : String): MutableList<Member>? {
        return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }


}