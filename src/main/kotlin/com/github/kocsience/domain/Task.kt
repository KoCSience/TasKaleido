package com.github.kocsience.domain

import jakarta.persistence.*

/**
 * タスク自体
 * @property id id
 * @property title タイトル
 * @property contents 内容
 * @property deadline 期限
 */

@Entity
@Table(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // これをつけると DB で自動採番されるようになる
    @Column(name = "task_id")
    val id: Int?,
    val title: String,
    var contents: String,
    var deadline: String,

    @ManyToOne
    var account: Account? = null
)

/*
@Embeddable
class Task(@Id val id: Int, val title:String, var contents:String, @Enumerated(EnumType.ORDINAL) val category: TaskCategory, val deadline: Instant,
           val notice: Instant, val difficulty: Int) {

// Kotlin+Spring+JPAでDDDの値オブジェクトを表現する - Qiita https://qiita.com/mikesorae/items/d3ef8eae3de5c12e2926
//【Spring Data JPA】マッピング（Entity） https://b1san-blog.com/post/spring/spring-jpa-mapping/#temporal
// Persisting Enums in JPA | Baeldung https://www.baeldung.com/jpa-persisting-enums-in-jpa
}
*/