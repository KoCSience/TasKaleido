package com.github.kocsience.controller

import com.github.kocsience.domain.Account
import com.github.kocsience.domain.Task
import com.github.kocsience.service.AccountService
import com.github.kocsience.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TopController(private val accountService: AccountService, private val taskService: TaskService) {
    @Autowired
    lateinit var session: SessionHolder

    // https://stackoverflow.com/questions/12395115/spring-missing-the-extension-file
    // https://www.baeldung.com/spring-mvc-pathvariable-dot
    @GetMapping("/css/{path:.+}")
    fun cssPaths(@PathVariable path: String): String {
        return "/css/$path"
    }

    @GetMapping("/js/{path:.+}")
    fun jsPaths(@PathVariable path: String): String {
        return "/js/$path"
    }

    @GetMapping("", "/", "/index.html")
    fun hello(model: Model): String {
        return "redirect:demo"
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
        val accountA = Account(1, "a", "藤原妹紅", "a@a", "a", 1, "yyyy/mm/dd hh:mm:ss")
        accountService.save(accountA)
        val accountB = Account(2, "b", "蓬莱山輝夜", "b@b", "b", 3, "yyyy/mm/dd hh:mm:ss")
        accountService.save(accountB)
        println("Account: $accountA, $accountB")
        val taskA = Task(1, "雑用", "なにかしら", "2000/01/01", accountA)
        taskService.save(taskA)
        val taskB = Task(2, "掃除", "お掃除ロボットと共同できれいにする", "2023/11/11", accountA)
        taskService.save(taskB)
        val task9 = Task(3, "プレゼン資料", "資料を探して、発表内容をまとめる", "2023/09/09", accountB)
        taskService.save(Task(4, "今日のタスク", "明日のタスクです！", "2023/09/9", accountB))
        taskService.save(Task(5, "来週のタスク", "来週のタスクです！", "2023/09/15", accountB))
        taskService.save(Task(6, "会計", "決算に向けて予算と使用額を確認する", "2023/9/28", accountB))
        println("Task: $taskA, $taskB, $task9")
        return "redirect:/management.html"
    }
}