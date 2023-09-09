package com.github.kocsience.controller

import com.github.kocsience.service.AccountService
import com.github.kocsience.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

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
    fun showManagement(@RequestParam("id", required = false) id: Int?, model: Model): String {
        model.addAttribute("accounts", accountService.findAll())

        val account = if (id != null) accountService.find(id) else null
        model.addAttribute("selectedAccount", account)
        model.addAttribute("tasks", account?.tasks) // 誰のtaskなのか指定できるようにする
        model.addAttribute("task", taskService.vanillaTask())

        val current = LocalDateTime.now()
        val nextSaturday: LocalDateTime = if (current.dayOfWeek == DayOfWeek.SATURDAY) {
            current
        } else {
            val daysUntilSaturday = DayOfWeek.SATURDAY.value - current.dayOfWeek.value
            current.plusDays(daysUntilSaturday.toLong())
        }
        val currentYearMonth = YearMonth.now()
        val lastDayOfMonth: LocalDate = currentYearMonth.atEndOfMonth()
        model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
        model.addAttribute(
            "tomorrow",
            LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        )
        model.addAttribute("nextSaturday", nextSaturday.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
        model.addAttribute("endOfMonth", lastDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))

        return "management"
    }

    @PostMapping
    fun pshowManagement(@RequestParam("id", required = false) id: Int?, model: Model): String {
        return showManagement(id, model)
    }
}
