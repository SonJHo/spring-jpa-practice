package jpabook.jpashop.service

import jpabook.jpashop.domain.item.Book
import jpabook.jpashop.domain.item.Item
import jpabook.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository,
) {
    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    @Transactional
    fun updateItem(id: Long, name: String, price: Int, stockQuantity: Int) {
        val item = itemRepository.findOne(id)!!
        item.price = price
        item.name = name
        item.stockQuantity = stockQuantity
    }

    fun findItems(): MutableList<Item>? {
        return itemRepository.findAll()
    }

    fun findOne(id: Long): Item? {
        return itemRepository.findOne(id)
    }

}