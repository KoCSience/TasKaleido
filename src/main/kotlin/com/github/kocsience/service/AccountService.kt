package com.github.kocsience.service

import com.github.kocsience.domain.Account
import com.github.kocsience.repository.AccountRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class AccountService(private val accountRepository: AccountRepository) {
    fun find(id: Int) = accountRepository.findById(id).getOrNull()

    fun findAll(): MutableList<Account> = accountRepository.findAll()

    fun save(account: Account) = accountRepository.save(account)

    fun vanillaAccount(): Account {
        return Account(0, "PASSWORD", "NAME", "EMAIL", "PROFILE", 1, "yyyy/mm/dd hh:mm:ss")
    }

    fun delete(id: Int) = accountRepository.deleteById(id)

    fun replace(account: Account): Boolean {
        if (account.id == null) {
            return false
        }
        save(account.copy(id=account.id))
        return true
    }
}