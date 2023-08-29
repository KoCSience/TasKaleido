package com.github.kocsience.Service

import com.github.kocsience.domain.Account
import com.github.kocsience.repository.AccountRepo
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AccountService(private val accountRepo: AccountRepo) {
    fun findAll() = accountRepo.findAll()

//    fun find(uuid: UUID) = accountRepo.findById(uuid).getOrNull()

    fun find(id: Int) = accountRepo.findById(id).getOrNull()

    fun save(account: Account) = accountRepo.save(account)

    fun delete(id: Int) = accountRepo.deleteById(id)

//    fun delete(uuid: UUID) = accountRepo.deleteById(uuid)
}