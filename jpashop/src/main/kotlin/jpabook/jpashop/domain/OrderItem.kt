package jpabook.jpashop.domain

import jakarta.persistence.*
import jpabook.jpashop.domain.item.Item
import kotlin.time.times


@Entity
class OrderItem protected constructor(){


    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null

    var orderPrice: Int? = null
    var count: Int = 0

    companion object{
        fun createItemOrder(item: Item, orderPrice : Int, count : Int): OrderItem {
            val orderItem = OrderItem()
            orderItem.item = item
            orderItem.count = count
            orderItem.orderPrice = orderPrice

            item.removeStock(count)
            return orderItem
        }
    }

    //비즈니스 로직
    fun cancel() {
        item?.addStock(count)
    }

    fun getTotalPrice(): Int {
        return orderPrice!!* count
    }

}



