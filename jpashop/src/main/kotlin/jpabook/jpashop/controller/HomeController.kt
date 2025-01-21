package jpabook.jpashop.controller

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeController {

    val log = KotlinLogging.logger{}
    @RequestMapping("/")
    fun home(): String {
        log.info { "home controller" }
        return "home"
    }
}