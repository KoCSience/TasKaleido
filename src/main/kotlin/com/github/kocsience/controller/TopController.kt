package com.github.kocsience.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class TopController {
    @GetMapping("/", "/index.html")
    fun hello(model: Model): String {
        model.addAttribute("message", "hi!")
        return "top"
    }

    @GetMapping("logout")
    fun showLogout(): String {
        return "logout"
    }

    @PostMapping("logout")
    fun logout(): String {
        // TODO
        return "top"
    }

    @GetMapping("/login")
    fun showLogin(): String {
        return "login"
    }

    @PostMapping("/login")
    fun login(): String {
        // TODO
        return "top"
    }
}