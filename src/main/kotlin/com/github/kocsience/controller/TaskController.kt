package com.github.kocsience.controller

import com.github.kocsience.domain.Task
import com.github.kocsience.service.AccountService
import com.github.kocsience.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/tasks", "/tasks/")
class TaskController(private val accountService: AccountService, private val taskService: TaskService) {
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


    @GetMapping("list.html")
    fun list(@RequestParam("accountId", required = false) accountId: Int?, model: Model): String {
        model.addAttribute(
            "tasks",
            if (accountId != null) accountService.find(accountId)?.tasks
            else taskService.findAll()
        )
        return "tasks/list"
    }

    @GetMapping("register.html")
    fun register() = "tasks/register"

    @PostMapping("register.html")
    fun create(@RequestParam("from", required = false) from: String?, @ModelAttribute newTask: Task): String {
        println("Task: $newTask")
        newTask.account =
            if (session.account == null) {
                accountService.vanillaAccount() // 適当 cannot saveとかしても良さそう or registerでid指定とか
            } else {
                session.account!!.tasks.add(newTask)
                session.account
            }
        taskService.save(newTask)

        return if (from != null) {
            "redirect:/$from"
        } else {
            "redirect:task.html?id=${newTask.id}"
        }
    }

    @GetMapping("task.html")
    fun show(@RequestParam("id", required = false) id: Int?, model: Model): String {
        val task = if (id != null) taskService.find(id)
            ?: taskService.vanillaTask() else taskService.vanillaTask() // キモい表記だけど許して
        model.addAttribute("task", task)
        return "tasks/task"
    }
}