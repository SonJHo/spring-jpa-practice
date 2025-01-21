package jpabook.jpashop

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class HelloController {

    @GetMapping("hello")
    fun hello(model: Model): String { //뷰에 데이터를 넘기기위해 모델 ui에 넣어서 넘김
        model.addAttribute("data", "hello")
        return "hello"
    }
}
