package simbot.example.service

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Service
import simbot.example.repository.Reply
import simbot.example.repository.ReplyRepository

/**
 * [Reply] 操作服务接口。
 * @author ForteScarlet
 */
interface ReplyService {

    /**
     * 提供关键词，返回需要回复的话。
     */
    suspend fun reply(keyword: String): String?


    /**
     * 增加一对儿关键词。
     */
    suspend fun addReply(keyword: String, content: String): Reply

    /**
     * 删除某关键词。
     *
     * @param keyword 关键词
     * @return void
     */
    suspend fun deleteReply(keyword: String)

    /**
     * 查询所有的关键词回复记录。可以提供一个实体作为查询条件。
     *
     * @param condition 条件，可以为null。
     * @return list
     */
    fun replies(condition: Reply?): Flow<Reply>

}


/**
 * [ReplyService] 基础实现。
 */
@Service
internal class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val template: R2dbcEntityTemplate
) : ReplyService {

    override suspend fun reply(keyword: String): String? = replyRepository.findByKeyword(keyword)?.content

    override suspend fun addReply(keyword: String, content: String): Reply {
        return replyRepository.save(Reply(keyword = keyword, content = content))
    }

    override suspend fun deleteReply(keyword: String) {
        replyRepository.deleteByKeyword(keyword)
    }

    override fun replies(condition: Reply?): Flow<Reply> {
        if (condition == null) {
            return replyRepository.findAll()
        }

        var criteria = Criteria.empty()
        condition.content.ifNotBlank {
            criteria = criteria.and(Criteria.where("content").like("%$it%"))
        }
        condition.keyword.ifNotBlank {
            criteria = criteria.and(Criteria.where("keyword").like("%$it%"))
        }

        return template.select<Reply>()
            .asType<Reply>()
            .matching(Query.query(criteria))
            .flow()
    }
}

private inline fun String?.ifNotBlank(block: (String) -> Unit) {
    if (this?.isNotBlank() == true) {
        block(this)
    }
}