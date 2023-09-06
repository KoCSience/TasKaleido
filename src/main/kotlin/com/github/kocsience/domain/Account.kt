package com.github.kocsience.domain

import jakarta.persistence.*

//import org.springframework.data.annotation.*

/**
 * @property id id
 * @property name 名前
 * @property email メール1
 * @property profile プロフィール(255文字?)
 * @property busynessStatus 0: 暇 → 4 忙
 * @property tasks あんまり上手く動いてないかも
 */
@Entity
@Table(name = "accounts")
data class Account(
//    @Id
//    @GeneratedValue
//    @Column(name = "uuid", columnDefinition = "CHAR(36)")
//    val uuid: String = UUID.randomUUID().toString(),
//    @GeneratedValue
//    val username: String = uuid,
//    val uuid: String = "a",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // これをつけると DB で自動採番されるようになる
    @Column(name = "account_id")
    val id: Int?,
    val password: String,
    var name: String,
    var email: String,
    var profile: String,
    var busynessStatus: Int? = null,
    // 誕生日
    // 入社年月日
    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL])
    val tasks: MutableList<Task> = mutableListOf()
)
