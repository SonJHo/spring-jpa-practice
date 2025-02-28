package jpabook.jpashop.api

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jpabook.jpashop.domain.Member
import jpabook.jpashop.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MemberApiController(
    private val memberService: MemberService,
) {

    @PostMapping("/login")
    @CrossOrigin(origins = ["*"])
    fun login(@RequestBody request: Map<String, String>): ResponseEntity<Any> {

        println("디버깅")
        val email = request["email"]
        val password = request["password"]

        return if (email == "1234@naver.com"  && password == "1234"){
            ResponseEntity.ok(mapOf("message" to "로그인 성공", "token" to "abcd1234"))
        }else{
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "로그인 실패"))
        }

    }

    @GetMapping("/v1/members") //엔티티를 바로 반환하지말자
    fun membersV1(): MutableList<Member> {
        return memberService.findMembers()
    }

    @GetMapping("/v2/members")
    fun membersV2(): Result<List<MemberDto>> {
        val findMembers = memberService.findMembers()
        val memberDtos = findMembers.map { member -> MemberDto(member.name) }

        return Result(memberDtos.size, memberDtos)

    }

    data class Result<T>(
        val count : Int? = null,
        val data: T,
    )

    data class MemberDto(
        val name: String? = null,
    )

    @PostMapping("/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PostMapping("/v2/members")
    fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {

        val member = Member(name = request.name)
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PutMapping("/v2/members/{id}")
    fun updateMemberV2(
        @PathVariable("id") id: Long,
        @RequestBody @Valid request: UpdateMemberRequest,
    ): UpdateMemberResponse {

        memberService.update(id, request.name)
        val member = memberService.findOne(id)
        return UpdateMemberResponse(member!!.id!!, member.name!!)
    }

    data class UpdateMemberRequest(
        val name: String? = null,
    )

    data class UpdateMemberResponse(
        val id: Long,
        val name: String,
    )

    data class CreateMemberRequest(
        @NotEmpty
        val name: String? = null,
    )

    data class CreateMemberResponse(
        val id: Long? = null,
    )

}
