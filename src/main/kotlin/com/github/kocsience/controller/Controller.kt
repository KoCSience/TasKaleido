package com.github.kocsience.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Controller {
    @GetMapping("/", "/greeting")
    fun hello(model: Model): String {
        model.addAttribute("message", "HIHIHIHIHIHI")
        return "index"
    }
}
