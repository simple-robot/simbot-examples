package simbot.example.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

/**
 * 回复内容数据库实体。
 */
@Table
data class Reply(
    @Id var id: Long = 0,
    val keyword: String = "",
    val content: String = ""
)


/**
 * 数据仓库交互接口。
 */
@Repository
interface ReplyRepository : CoroutineCrudRepository<Reply, Long>, CoroutineSortingRepository<Reply, Long> {

    /**
     * 根据 [Reply.keyword] 查询结果。
     */
    @Suppress("SpringDataRepositoryMethodReturnTypeInspection")
    suspend fun findByKeyword(keyword: String): Reply?

    /**
     * 根据 [Reply.keyword] 删除。
     */
    suspend fun deleteByKeyword(keyword: String)

}
