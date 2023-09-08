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
    fun register(): String {
        return if (session.account == null) {
            "redirect:/accounts/login.html?from='tasks/register.html'"
        } else {
            "tasks/register"
        }
    }

    @PostMapping("register.html")
    fun create(@RequestParam("from", required = false) from: String?, @ModelAttribute newTask: Task): String {
        if (session.account == null) {
            return "redirect:/accounts/login.html?from='tasks/register.html'"
        }

        newTask.account = session.account!!
        println("Task before save: $newTask")
        taskService.save(newTask)
        session.account = accountService.find(session.account!!.id!!)
        println("Task after save: $newTask")

        return if (from != null) {
            "redirect:/$from"
        } else {
            "redirect:task.html?id=${newTask.id}"
        }
    }

    @PutMapping("update.html")
    fun update(
        @RequestParam id: Int,
        @RequestParam("from", required = false) from: String?,
        @ModelAttribute task: Task
    ): String {
        if (session.account == null) {
            return "redirect:/accounts/login.html?from='tasks/update.html'"
        }
        task.account = session.account!!
        println("update Task: $task , ${session.account}")
        taskService.save(task.copy(id = id)) // なんかcopyしてる
        return if (from != null) {
            "redirect:/$from"
        } else {
            "redirect:task.html?id=${id}"
        }
    }

    @PostMapping("worker_update.html")
    fun updateWorker(
        @RequestParam("taskId") taskId: Int,
        @RequestParam("from", required = false) from: String?,
        @ModelAttribute taskForm: TaskForm
    ): String {
        val task: Task? = taskService.find(taskId)
        if (task != null) {
            task.account = accountService.find(taskForm.account) // change account
            taskService.save(task.copy(id = taskId)) // タスクを保存
        }

//        println("update Task: $task")
        return if (from != null) {
            "redirect:/$from"
        } else {
            "redirect:task.html?id=${taskId}"
        }
    }

    data class TaskForm(
        val account: Int
    )

    @GetMapping("task.html")
    fun show(@RequestParam("id", required = false) id: Int?, model: Model): String {
        val task = if (id != null) taskService.find(id)
            ?: taskService.vanillaTask() else taskService.vanillaTask() // キモい表記だけど許して
        model.addAttribute("task", task)
        return "tasks/task"
    }
}