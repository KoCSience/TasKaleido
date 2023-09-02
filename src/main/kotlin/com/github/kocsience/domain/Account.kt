package com.github.kocsience.domain

import com.github.kocsience.Task
import jakarta.persistence.*
//import org.springframework.data.annotation.*
import java.util.*

/**
 * @property uuid UUID
 * @property name 名前
 *
 */
@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue
    @Column(name = "uuid", columnDefinition = "CHAR(36)")
    val uuid: String = UUID.randomUUID().toString(),
    @GeneratedValue
    val username: String = uuid,
//    val uuid: String = "a",
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // これをつけると DB で自動採番されるようになる
//    val id: Int?,
    val password: String,
    var name: String,
    var email: String,
    var profile: String
    // 誕生日
    // 入社年月日
) {
    @ElementCollection
    val tasks: MutableList<Task> = mutableListOf()

    @ElementCollection
    val skills: MutableList<String> = mutableListOf()
}
