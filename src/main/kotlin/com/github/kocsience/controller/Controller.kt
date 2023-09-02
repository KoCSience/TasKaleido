package com.github.kocsience.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class Controller {
    @GetMapping("/", "/index.html", "index")
    fun hello(model: Model): String {
        model.addAttribute("message", "HIHIHIHIHIHI")
        return "index"
    }

    @GetMapping("/css/{path}")
    fun cssPath(@PathVariable path: String) : String {
        return "/css/${path}"
    }

    @GetMapping("/js/{path}")
    fun jsPath(@PathVariable path: String) : String {
        return "/js/${path}"
    }
}
