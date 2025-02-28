package jpabook.jpashop

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jpabook.jpashop.domain.*
import jpabook.jpashop.domain.item.Book
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class InitDb (
    private val initService: InitService
){
    @PostConstruct //스프릴빈 다올라오고 호풀
    fun init(){
        initService.dbInit1()
        initService.dbInit2()
    }

    companion object {


        @Component
        @Transactional
        class InitService(
            private val em : EntityManager
        ){
            fun dbInit1(){
                val member = Member()
                member.name = "userA"
                member.address = Address("서울", "1", "111")
                em.persist(member)


                val book1 = Book()
                book1.name = "jpa1 book"
                book1.price = 10000
                book1.stockQuantity = 100
                em.persist(book1)

                val book2 = Book()
                book2.name = "jpa2 book"
                book2.price = 10000
                book2.stockQuantity = 100
                em.persist(book2)

                val orderItem1 = OrderItem.createItemOrder(book1, 10000, 1)
                val orderItem2 = OrderItem.createItemOrder(book2, 10000, 2)


                val delivery = Delivery()
                delivery.address = member.address
                val order = Order.createOrder(member, delivery, orderItem1, orderItem2)

                em.persist(order)
            }

            fun dbInit2(){
                val member = Member()
                member.name = "userB"
                member.address = Address("부산", "2", "222")
                em.persist(member)

                val book1 = Book()
                book1.name = "spring1 book"
                book1.price = 10000
                book1.stockQuantity = 100
                em.persist(book1)

                val book2 = Book()
                book2.name = "spring2 book"
                book2.price = 10000
                book2.stockQuantity = 100
                em.persist(book2)

                val orderItem1 = OrderItem.createItemOrder(book1, 20000, 3)
                val orderItem2 = OrderItem.createItemOrder(book2, 40000, 4)


                val delivery = Delivery()
                delivery.address = member.address
                val order = Order.createOrder(member, delivery, orderItem1, orderItem2)

                em.persist(order)
            }

        }
    }

}