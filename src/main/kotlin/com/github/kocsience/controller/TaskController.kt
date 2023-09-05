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

    //    エンドポイント:

    // https://stackoverflow.com/questions/12395115/spring-missing-the-extension-file
    @GetMapping("/css/{file}.{ext}")
    fun cssPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/css/$file.$ext"
    }

    @GetMapping("/js/{file}.{ext}")
    fun jsPath(@PathVariable file: String, @PathVariable ext: String): String {
        return "/js/$file.$ext"
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
    fun create(@ModelAttribute task: Task): String {
        println("Task: $task")
        taskService.save(task)
        return "redirect:task.html?id=${task.id}"
    }

    @GetMapping("task.html")
    fun show(@RequestParam("id", required = false) id: Int?, model: Model): String {
        val task = if (id != null) taskService.find(id)
            ?: taskService.vanillaTask() else taskService.vanillaTask() // キモい表記だけど許して
        model.addAttribute("task", task)
        return "tasks/task"
    }
}