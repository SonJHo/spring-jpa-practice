package jpabook.jpashop.controller

import jakarta.validation.constraints.NotEmpty

class MemberForm {


    @NotEmpty(message = "회원 이름은 필수 입니다")
    var name :String? = null
    @NotEmpty(message = "도시 이름은 필수 입니다")
    var city : String? = null
    @NotEmpty(message = "거리 이름은 필수 입니다")
    var street :String? = null
    var zipcode : String? = null
}