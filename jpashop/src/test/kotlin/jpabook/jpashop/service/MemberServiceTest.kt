package jpabook.jpashop.service

import jakarta.persistence.EntityManager
import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalStateException


@SpringBootTest
@Transactional
class MemberServiceTest  @Autowired constructor(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val em: EntityManager
) {


    @Test
    @Rollback(false)
    fun 회원가입() {
        //given
        val member = Member()
        member.name = "kimg"
        //when
        val savedId = memberService.join(member)
        //then
        em.flush()
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId))
    }

    @Test
    fun 중복_회원_예외() {

        //given
        val member1 = Member()
        member1.name = "kim1"

        val member2 = Member()
        member2.name = "kim1"
        //when

        val exception = org.junit.jupiter.api.assertThrows<IllegalStateException> {
            memberService.join(member1)
            memberService.join(member2)
        }
        //then

    }

}