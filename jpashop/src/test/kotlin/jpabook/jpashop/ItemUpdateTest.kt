package jpabook.jpashop

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.awt.print.Book
import kotlin.test.Test


@SpringBootTest
class ItemUpdateTest {

    @Autowired
    lateinit var em : EntityManager

    @Test
    fun updateTest(){

        em.find(Book::class.java, 1)


    }

}