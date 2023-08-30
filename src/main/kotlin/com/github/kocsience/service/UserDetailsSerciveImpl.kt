package com.github.kocsience.service

import com.github.kocsience.repository.AccountRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

//import com.github.kocsience.service.AccountService

@Component
@Transactional
class UserDetailsSerciveImpl(private val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
//            throw IllegalArgumentException("username must not be null.")
            error("user name must not be null.")
        }

        val account = accountRepository.findById(username).orElseThrow { UsernameNotFoundException(username) }

        return User.withUsername(account.username)
            .password(account.password)
            .roles("USER")
            .build()
    }
}