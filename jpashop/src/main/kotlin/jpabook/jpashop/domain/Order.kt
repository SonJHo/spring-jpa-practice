package jpabook.jpashop.domain

import jakarta.persistence.*
import java.lang.IllegalStateException
import java.time.LocalDateTime


@Entity
@Table(name = "orders")
class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
        set(value) {
            field = value
            value?.orders?.add(this)
        }

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orderItems: MutableList<OrderItem> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery? = null
        set(value) { // 커스텀 setter
            field = value
            value?.order = this
        }
    var orderDate: LocalDateTime? = null //주문시간


    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = null //[ORDERM CANCLE]


    //연관관계 메서드

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    //생성메서드
    companion object{
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
            val order = Order()
            order.member = member
            order.delivery = delivery
            for (orderItem in orderItems) {
                order.orderItems.add(orderItem)
            }
            order.status = OrderStatus.ORDER
            order.orderDate = LocalDateTime.now()
            return order
        }

    }


    //비즈니스 로직
    /**
     * 주문 취소
     */

    fun cancel() {
        if (delivery?.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소 불가")
        }
        status = OrderStatus.CANCLE
        for (orderItem in orderItems) {
            orderItem.cancel()
        }
    }

    /**
     * 젠체 주문 가격 조회
     */
    fun getTotalPrice(): Int {
        var totalPrice = 0
        for (orderItem in orderItems) {
            totalPrice += orderItem.getTotalPrice()
        }

        return totalPrice
    }


}
