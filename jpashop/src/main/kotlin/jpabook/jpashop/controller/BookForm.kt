package jpabook.jpashop.controller

data class BookForm(
    var id: Long? = null, // id는 읽기 전용으로 설정
    var price: Int? = null,  // 반드시 입력
    var stockQuantity: Int = 0,// 반드시 입력
    var author: String = "",// 기본값 설정
    var name: String = "",// 기본값 설정
    var isbn: String = "",    // 기본값 설정
)