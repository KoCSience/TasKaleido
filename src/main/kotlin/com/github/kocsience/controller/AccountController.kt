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

    // https://stackoverflow.com/questions/12395115/spring-missing-the-extension-file
    @GetMapping("/css/{file}.{ext}")
    fun cssPath(@PathVariable file: String, @PathVariable ext: String) : String {
        return "/css/${file}.${ext}"
    }

    @GetMapping("/js/{file}.{ext}")
    fun jsPath(@PathVariable file: String, @PathVariable ext: String) : String {
        return "/js/${file}.${ext}"
    }

    @GetMapping("", "/", "list.html")
    fun top(@RequestParam("id") id: Int, model: Model): String {
    }

    fun list(model: Model): String {
        model.addAttribute("accounts", accountService.findAll())
        return "accounts/list"
    }

    @GetMapping
    fun show(@RequestParam("id") id: Int, model: Model): String {
        model.addAttribute("account", accountService.find(id))
        return "subordinate"
        // TODO: 変えるかも？要相談
    }


    @GetMapping("register", "register.html")
    fun register() = "accounts/register"

    @PostMapping("register", "register.html")
    fun create(@ModelAttribute account: Account): String {
        println("Account: $account")
        accountService.save(account)
        println("Account: $account")
        //        return "redirect:accounts/${account.uuid}"
        return "redirect:${account.id}"
    }
//    @GetMapping("{uuid}")
//    fun show(@PathVariable uuid: String, model: Model): String {
//        model.addAttribute("account", accountService.find(uuid))
//        return "accounts/uuid"
//    }

}