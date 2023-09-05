package com.github.kocsience.service

import com.github.kocsience.domain.Account
import com.github.kocsience.domain.Task
import com.github.kocsience.repository.TaskRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService(private val taskRepository: TaskRepository) {
    fun find(id: Int) = taskRepository.findById(id).getOrNull()

    fun findAll(): MutableList<Task> = taskRepository.findAll()

    fun save(task: Task) = taskRepository.save(task)

    fun vanillaTask(account: Account? = null): Task {
        return Task(0, "TITLE", "CONTENTS", "0000/00/00", account)
    }
}