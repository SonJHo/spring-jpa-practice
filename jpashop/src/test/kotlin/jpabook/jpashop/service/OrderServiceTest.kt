package jpabook.jpashop.service

import jakarta.persistence.EntityManager
import jpabook.jpashop.domain.Address
import jpabook.jpashop.domain.Member
import jpabook.jpashop.domain.OrderStatus
import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.exception.NotEnoughStockException
import jpabook.jpashop.repository.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test


@SpringBootTest
@Transactional
class OrderServiceTest @Autowired constructor(
    val em: EntityManager,
    val orderService: OrderService,
    val orderRepository: OrderRepository,
) {

    @Test
    fun 상품주문() {
        //given
        val member = createMember()
        val book = createBook()
        //when
        val orderCount = 2
        val orderId = orderService.order(member.id!!, book.id!!, orderCount)
        //then
        val getOrder = orderRepository.findOne(orderId)

        assertThat(getOrder!!.status).isEqualTo(OrderStatus.ORDER)
        assertThat(getOrder.orderItems.size).isEqualTo(1)
        assertThat(getOrder.getTotalPrice()).isEqualTo(30000 * orderCount)
        assertThat(book.stockQuantity).isEqualTo(8)
    }

    @Test
    fun 상품주문_재고수_초과() {
        //given
        val member = createMember()
        val book = createBook()
        val orderCount = 11
        //when
        val exception = assertThrows<NotEnoughStockException> {
            orderService.order(member.id!!, book.id!!, orderCount)
        }
    }

    @Test
    fun 주문취소() {

        //given
        val member = createMember()
        val book = createBook()
        val orderCount = 2
        val orderId = orderService.order(member.id!!, book.id!!, orderCount)
        //when
        orderService.cancelOrder(orderId)
        //then
        val order = orderRepository.findOne(orderId)
        assertThat(order!!.status).isEqualTo(OrderStatus.CANCLE)
        assertThat(book.stockQuantity).isEqualTo(10)
    }

    @Test
    fun 상품주문_재고수량초과() {


        //given

        //when

        //then

    }

    private fun createBook(): Book {
        val book = Book()
        book.name = "시골 JPA"
        book.price = 30000
        book.stockQuantity = 10
        em.persist(book)
        return book
    }

    private fun createMember(): Member {
        val member = Member()
        member.name = "회원1"
        member.address = Address("서울", "강가", "123-123")
        em.persist(member)
        return member
    }
}
