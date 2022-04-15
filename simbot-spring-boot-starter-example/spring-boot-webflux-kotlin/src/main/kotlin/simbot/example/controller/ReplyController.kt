package simbot.example.controller

import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import simbot.example.repository.Reply
import simbot.example.service.ReplyService

/**
 *
 *
 * @author ForteScarlet
 */
@RestController
@RequestMapping("/reply")
class ReplyController(
    private val replyService: ReplyService
) {


    /**
     * 根据条件查询所有匹配数据.
     *
     * @param condition condition
     * @return data list.
     */
    @GetMapping("/list")
    fun replies(condition: Reply?): Flow<Reply> {
        return replyService.replies(condition)
    }

    /**
     * 根据关键词查询其对应的结果。
     *
     * @param keyword keyword
     * @return 回复语句
     */
    @GetMapping("/keyword/{keyword}")
    suspend fun reply(@PathVariable keyword: String): String? {
        return replyService.reply(keyword)
    }

    /**
     * 新增一个关键词回复信息。
     *
     * @param reply data entity.
     * @return inserted entity.
     */
    @PostMapping
    suspend fun add(@RequestBody reply: Reply): Reply {
        return replyService.addReply(reply.keyword, reply.content)
    }


    /**
     * 根据关键词删除reply信息。
     *
     * @param keyword keyword
     */
    @DeleteMapping("/keyword/{keyword}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable keyword: String) {
        return replyService.deleteReply(keyword)
    }


}