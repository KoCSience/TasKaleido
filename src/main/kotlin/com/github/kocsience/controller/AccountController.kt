package com.github.kocsience.controller

import com.github.kocsience.domain.Account
import com.github.kocsience.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/accounts", "/accounts/")
class AccountController(private val accountService: AccountService) {
    @Autowired
    lateinit var account: Account
//    エンドポイント:
//    accounts: GET list
//    accounts/register/: POST createAccount
//    accounts/login/: POST login
//    accounts/UUID/: GET show, DELETE delete
//    accounts/UUID/edit/: PUT update, GET showEdit?

    // https://stackoverflow.com/questions/12395115/spring-missing-the-extension-file
    @GetMapping("/css/{file}.{ext}")
    fun cssPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/css/$file.$ext"
    }

    @GetMapping("/js/{file}.{ext}")
    fun jsPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/js/$file.$ext"
    }

    @GetMapping("", "list.html")
    fun list(model: Model): String {
        // いずれなくなるかも？
        model.addAttribute("accounts", accountService.findAll())
        return "accounts/list"
    }

    @GetMapping("subordinate.html")
    fun showSubordinate(@RequestParam("id", required = false) id: Int?, model: Model): String {
        model.addAttribute(
            "account",
            if (id != null) accountService.find(id)
            else accountService.vanillaAccount()
        )
        return "accounts/subordinate"
    }


    @GetMapping("register.html")
    fun register() = "accounts/register"

    @PostMapping("register.html")
    fun create(@ModelAttribute account: Account): String {
        println("Account: $account")
        accountService.save(account)
        println("Account: $account")
        return "redirect:subordinate.html?id=${account.id}"
    }

    @GetMapping("login.html")
    fun showLogin() = "accounts/login"

    @PostMapping("login.html")
    fun login(@ModelAttribute loginForm: LoginForm): String {
        val account = accountService.find(loginForm.id)
        if (account == null) {
            println("cannot find account id.")
            return "accounts/login"
        }
        if (account.password == loginForm.password) {
            println("login successful")
            this.account = account
            return "redirect:subordinate.html?id=${account.id}"
        }
        println("wrong password")
        return "accounts/login"
    }

    class LoginForm(val id: Int, val password: String)

    @GetMapping("logout.html")
    fun showLogout() = "accounts/logout"
}