package jpabook.jpashop.service

import jpabook.jpashop.domain.Member
import jpabook.jpashop.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException


@Service
@Transactional(readOnly = true)
class MemberService(
    @Autowired
    private val memberRepository: MemberRepository,
) {
    /**
     * 회원 가입
     */
    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member) //중복회원 검증
        memberRepository.save(member)
        return member.id!!
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.name!!)

        if(!findMembers.isNullOrEmpty()){
            throw IllegalStateException("이미 존재하는 회원입니다")
        }

    }

    //회원 전체 조회
    fun findMembers(): MutableList<Member> {
        return memberRepository.findAll()!!
    }

    fun findOne(id : Long): Member? {
        return memberRepository.findOne(id)
    }


}