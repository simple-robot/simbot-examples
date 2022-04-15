package simbot.example.service;

import org.springframework.lang.Nullable;
import simbot.example.entity.Reply;

import java.util.List;

/**
 * 自动回复的服务接口。
 *
 * @author ForteScarlet
 */
public interface ReplyService {

    /**
     * 提供关键词，返回需要回复的话。
     *
     * @param keyword 关键词
     * @return 回复语句，或null。
     */
    @Nullable
    String reply(String keyword);


    /**
     * 增加一对儿关键词。
     *
     * @param keyword 关键词
     * @param content 回复内容
     * @return saved value.
     */
    Reply addReply(String keyword, String content);

    /**
     * 删除某关键词。
     *
     * @param keyword 关键词
     */
    void deleteReply(String keyword);

    /**
     * 查询所有的关键词回复记录。可以提供一个实体作为查询条件。
     *
     * @param condition 条件，可以为null。
     * @return list
     *
     */
    List<Reply> replies(@Nullable Reply condition);

}
