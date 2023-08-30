package com.github.kocsience.service

import com.github.kocsience.domain.Account
import com.github.kocsience.repository.AccountRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AccountService(private val accountRepository: AccountRepository) {
    fun find(id: String) = accountRepository.findById(id).getOrNull()

    fun findAll() = accountRepository.findAll()

    fun save(account: Account) = accountRepository.save(account)
}