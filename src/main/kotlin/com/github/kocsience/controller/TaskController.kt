package com.github.kocsience.controller

import com.github.kocsience.domain.Task
import com.github.kocsience.service.TaskService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/tasks", "/tasks/")
class TaskController(private val taskService: TaskService) {
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
    fun list(model: Model): String {
        model.addAttribute("tasks", taskService.findAll())
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

//    @GetMapping("{uuid}")
//    fun show(@PathVariable uuid: String, model: Model): String {
//        model.addAttribute("account", accountService.find(uuid))
//        return "accounts/uuid"
//    }

    @GetMapping("task.html")
    fun show(@RequestParam("id", required = false) id: Int, model: Model): String {
        model.addAttribute("task", taskService.find(id))
        return "tasks/task"
    }
}