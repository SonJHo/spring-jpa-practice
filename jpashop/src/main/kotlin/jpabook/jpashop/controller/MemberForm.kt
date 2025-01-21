package jpabook.jpashop.controller

import jakarta.validation.constraints.NotEmpty

class MemberForm {


    @NotEmpty(message = "회원 이름은 필수 입니다")
    var name :String? = null
    var city : String? = null
    var street :String? = null
    var zipcode : String? = null
}