package com.github.kocsience.controller

import com.github.kocsience.domain.Account
import com.github.kocsience.domain.Task
import com.github.kocsience.service.AccountService
import com.github.kocsience.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TopController(private val accountService: AccountService, private val taskService: TaskService) {
    @GetMapping("/index.html")
    fun hello(model: Model): String {
        model.addAttribute("message", "THIS IS JUST A TEST MESSAGE!")
        return "index"
    }

    // https://stackoverflow.com/questions/12395115/spring-missing-the-extension-file
    @GetMapping("/css/{file}.{ext}")
    fun cssPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/css/$file.$ext"
    }

    @GetMapping("/js/{file}.{ext}")
    fun jsPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/js/$file.$ext"
    }

    @GetMapping("management.html")
    fun showManagement(model: Model): String {
        // とりあえず仮でこれで , アカウントで分けていないけど！ｗ
        model.addAttribute("accounts", accountService.findAll())
        model.addAttribute("task", taskService.vanillaTask(accountService.vanillaAccount()))
        return "management"
    }

    @GetMapping("subordinate.html")
    fun showSubordinate(model: Model): String {
        // とりあえず仮でこれで , アカウントで分けていないけど！ｗ
        model.addAttribute("accounts", accountService.findAll())
        return "accounts/subordinate"
    }

    @GetMapping("demo")
    fun demo(): String {
        // ↓ 動かなかった
//        val accountS = Account(0, "super", "super", "super@super", "super user")
//        accountService.save(accountS)
        val accountA = Account(1, "a", "a", "a@a", "a")
        accountService.save(accountA)
        val accountB = Account(2, "b", "b", "b@b", "b")
        accountService.save(accountB)
        println("Account: $accountA, $accountB")
        val taskA = Task(1, "a", "a", "2000/01/01", accountA)
        taskService.save(taskA)
        val taskB = Task(2, "b", "b", "2023/11/11", accountB)
        taskService.save(taskB)
        println("Task: $taskA, $taskB")
        return "index"
    }
}