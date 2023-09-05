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
    fun cssPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/css/$file.$ext"
    }

    @GetMapping("/js/{file}.{ext}")
    fun jsPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/js/$file.$ext"
    }




//    @GetMapping("", "/", "list.html")
//    fun top(@RequestParam("id", required = false) id: Int?, model: Model): String {
//        return if (id == null) {
//            list(model)
//        } else {
//            showSubordinate(id, model)
//        }
//    }

    @GetMapping("list.html")
    fun list(model: Model): String {
        // いずれなくなるかも？
        model.addAttribute("accounts", accountService.findAll())
        return "accounts/list"
    }

    @GetMapping("subordinate.html")
    fun showSubordinate(@RequestParam("id", required = false) id: Int?, model: Model): String {
        if (id != null) model.addAttribute("account", accountService.find(id))
        return "accounts/subordinate"
    }


    @GetMapping("register.html")
    fun register() = "accounts/register"

    @PostMapping("register.html")
    fun create(@ModelAttribute account: Account): String {
        println("Account: $account")
        accountService.save(account)
        println("Account: $account")
        //        return "redirect:accounts/${account.uuid}"
        return "redirect:subordinate.html?id=${account.id}"
    }

    @PostMapping("Subordinate.html")
    fun ref_busyness_status(@RequestParam("id", required = false)id: Int?, model: Model): String{
        if (id == 0) {

        }

        else if (id == 1) {
            model.addAttribute("account", accountService.find(id))
            return "accounts/subordinate"
        }


        else if (id == 2) {
            model.addAttribute("account", accountService.find(id))
            return "accounts/subordinate"
        }

        else if (id == 3) {
            model.addAttribute("account", accountService.find(id))
            return "accounts/subordinate"
        }

        else if (id == 4) {
            model.addAttribute("account", accountService.find(id))
            return "accounts/subordinate"
        }
    }
//    @GetMapping("{uuid}")
//    fun show(@PathVariable uuid: String, model: Model): String {
//        model.addAttribute("account", accountService.find(uuid))
//        return "accounts/uuid"
//    }

}