package com.github.kocsience.controller

import com.github.kocsience.domain.Account
import com.github.kocsience.service.AccountService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/accounts", "/accounts/")
class AccountController(private val accountService: AccountService) {
//    エンドポイント:
//    accounts: GET list
//    accounts/register/: POST createAccount
//    accounts/login/: POST login
//    accounts/UUID/: GET show, DELETE delete
//    accounts/UUID/edit/: PUT update, GET showEdit?
    @GetMapping("/css/{path}")
    fun cssPath(@PathVariable path: String) : String {
        return "/css/${path}"
    }

    @GetMapping("/js/{path}")
    fun jsPath(@PathVariable path: String) : String {
        return "/js/${path}"
    }

    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("accounts", accountService.findAll())
        return "accounts/list"
    }

    @GetMapping("register")
    fun register() = "register"

    @PostMapping("register")
    fun create(@ModelAttribute account: Account): String {
        println("Account: $account")
        accountService.save(account)
        //        return "redirect:accounts/${account.uuid}"
        return "redirect:${account.id}"
    }

//    @GetMapping("{uuid}")
//    fun show(@PathVariable uuid: String, model: Model): String {
//        model.addAttribute("account", accountService.find(uuid))
//        return "accounts/uuid"
//    }

    @GetMapping("{id}")
    fun show(@PathVariable id: Int, model: Model): String {
        model.addAttribute("account", accountService.find(id))
        return "subordinate"
    }
}