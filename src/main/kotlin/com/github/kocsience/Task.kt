package com.github.kocsience

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.*

/**
 * タスク自体
 * @property title タイトル
 * @property contents 内容
 * @property category カテゴリ
 * @property deadline 期限
 * @property notice 通知時間
 * @property difficality 難しさ
 */
@Embeddable
class Task(val title:String, var contents:String, @Enumerated(EnumType.ORDINAL) val category: TaskCategory, val deadline: Instant, val notice: Instant, val difficality: Int) {
// Kotlin+Spring+JPAでDDDの値オブジェクトを表現する - Qiita https://qiita.com/mikesorae/items/d3ef8eae3de5c12e2926
//【Spring Data JPA】マッピング（Entity） https://b1san-blog.com/post/spring/spring-jpa-mapping/#temporal
// Persisting Enums in JPA | Baeldung https://www.baeldung.com/jpa-persisting-enums-in-jpa
}