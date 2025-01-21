package jpabook.jpashop.domain.item

import jakarta.persistence.*
import jpabook.jpashop.domain.Category
import jpabook.jpashop.exception.NotEnoughStockException


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null

    var name: String? = null
    var price: Int? = null
    var stockQuantity: Int = 0

    @ManyToMany(mappedBy = "items")
    var categories : MutableList<Category>  = mutableListOf()


    //비즈니스 로직
    /**
     * stock 증가
     */
    fun addStock(quantity : Int){
        stockQuantity += quantity
    }

    fun removeStock(quantity : Int){
        val newValue = stockQuantity - quantity
        if(newValue < 0){
            throw NotEnoughStockException("need more stock")
        }
        stockQuantity = newValue
    }

}