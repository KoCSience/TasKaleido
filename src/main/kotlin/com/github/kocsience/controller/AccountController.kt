package com.github.kocsience.controller

import com.github.kocsience.domain.Account
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
@RequestMapping("/accounts", "/accounts/")
class AccountController(private val accountService: AccountService, private val taskService: TaskService) {
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

    @GetMapping("", "list.html")
    fun list(model: Model): String {
        // いずれなくなるかも？
        model.addAttribute("accounts", accountService.findAll())
        return "accounts/list"
    }

    @GetMapping("subordinate.html")
    fun showSubordinate(
        @RequestParam("id", required = false) id: Int?,
        @RequestParam("busynessStatus", required = false) busynessStatus: Int?,
        model: Model
    ): String {
        val account = (if (id != null) {
            println("id=$id")
            accountService.find(id) // 見つからなかったらvanilla
        } else if (session.account != null) {
            println("found account session:")
            println(session.account!!.name)
            // この段階だとaccount.tasksが初期化できていないっぽい？
            session.account
        } else {
            println("no id param & not login yet")
            // no id param & not login yet
            return "redirect:/accounts/login.html"
        }) ?: accountService.vanillaAccount()

        model.addAttribute("account", account)
        model.addAttribute("tasks", account.tasks)
        model.addAttribute("busynessStatusColor", busynessStatusColorChange(account.busynessStatus))
        
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

        return "accounts/subordinate"
    }

    @PostMapping("subordinate.html")
    fun refBusynessStatus(
        @RequestParam("id", required = false) id: Int?,
        @RequestParam("busynessStatus", required = false) busynessStatus: Int?,
        model: Model
    ): String {
        // TODO: refactoring したい
        if (busynessStatus != null) {
            println("busynessStatus: $busynessStatus")
            val account = session.account
            if (account != null) {
                account.busynessStatus = busynessStatus
                accountService.replace(account)
            } else {
                println("cannot set \"busynessStatus\" because by cannot find session")
            }
        }

        val account = if (id != null) {
            accountService.find(id) ?: accountService.vanillaAccount()
        } else {
            accountService.vanillaAccount()
        }

        model.addAttribute("account", account)
        model.addAttribute("tasks", account.tasks)
        model.addAttribute("busynessStatusColor", busynessStatusColorChange(account.busynessStatus))
        return "redirect:/accounts/subordinate.html" // キモい実装
    }

    fun busynessStatusColorChange(busynessStatus: Int?): String {
        return when (busynessStatus) {
            0 -> "primary"
            1 -> "info"
            2 -> "success"
            3 -> "warning"
            4 -> "danger"
            else -> "dark"
        }
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
    fun login(@RequestParam("from", required = false) from: String?, @ModelAttribute loginForm: LoginForm): String {
        val account = accountService.find(loginForm.id)
        if (account == null) {
            println("cannot find account id.")
            return "accounts/login"
        }
        if (account.password == loginForm.password) {
            println("login successful")

            val now = LocalDateTime.now()
            val year = now.year
            val month = now.monthValue
            val day = now.dayOfMonth
            val hour = now.hour
            val minute = now.minute
            val second = now.second

            println(String.format("%d/%d/%d %02d:%02d:%02d", year, month, day, hour, minute, second))
            account.lastLoginDate = String.format("%d/%d/%d %02d:%02d:%02d", year, month, day, hour, minute, second)
            accountService.save(account.copy(id=account.id))
            session.account = account

            return if (from == null) "redirect:subordinate.html?id=${account.id}" else "/${from}"
        }
        println("wrong password")

        return "accounts/login"
    }

    class LoginForm(val id: Int, val password: String)

    @GetMapping("logout.html")
    fun showLogout() = "accounts/logout"
}