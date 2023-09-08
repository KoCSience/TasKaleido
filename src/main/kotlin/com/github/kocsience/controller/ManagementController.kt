package com.github.kocsience.controller

import com.github.kocsience.service.AccountService
import com.github.kocsience.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("management.html")
class ManagementController(private val accountService: AccountService, private val taskService: TaskService) {
    @Autowired
    lateinit var session: SessionHolder

    @GetMapping("/css/{path:.+}")
    fun cssPaths(@PathVariable path: String): String {
        return "/css/$path"
    }

    @GetMapping("/js/{path:.+}")
    fun jsPaths(@PathVariable path: String): String {
        return "/js/$path"
    }

    @GetMapping
    fun showManagement(model: Model): String {
        // とりあえず仮でこれで , アカウントで分けていないけど！ｗ
        model.addAttribute("accounts", accountService.findAll())
        model.addAttribute("tasks", taskService.findAll())

        model.addAttribute("task", taskService.vanillaTask(accountService.vanillaAccount())) // 誰のtaskなのか指定できるようにする
        return "management"
    }
}
