package jpabook.jpashop.service

import jpabook.jpashop.domain.Delivery
import jpabook.jpashop.domain.Order
import jpabook.jpashop.domain.OrderItem
import jpabook.jpashop.repository.ItemRepository
import jpabook.jpashop.repository.MemberRepository
import jpabook.jpashop.repository.OrderRepository
import jpabook.jpashop.repository.OrderSearch
import org.hibernate.internal.build.AllowSysOut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class OrderService @Autowired protected constructor(
    private var orderRepository: OrderRepository,
    private var memberRepository: MemberRepository,
    private var itemRepository: ItemRepository,
    ) {

    //주문
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {
        //엔티티 조회
        val member = memberRepository.findOne(memberId)
        val item = itemRepository.findOne(itemId)

        //배송정보 생성
        val delivery = Delivery()
        delivery.address = member?.address

        //주문 상품 생성
        val orderItem = OrderItem.createItemOrder(item!!, item.price!!, count)

        //주문 생성
        val order = Order.createOrder(member!!, delivery, orderItem)

        //주문 저장
        orderRepository.save(order)
        return order.id!!


    }


    //취소

    @Transactional
    fun cancelOrder(orderId : Long){
        val order = orderRepository.findOne(orderId)
        order!!.cancel()
    }


    fun findOrders(orderSearch : OrderSearch): MutableList<Order> {
        return orderRepository.findAllByString(orderSearch)
    }
}