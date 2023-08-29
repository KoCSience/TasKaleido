package com.github.kocsience.repository

import com.github.kocsience.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
//interface AccountRepo : JpaRepository<Account, UUID> {
interface AccountRepo : JpaRepository<Account, Int> {

}