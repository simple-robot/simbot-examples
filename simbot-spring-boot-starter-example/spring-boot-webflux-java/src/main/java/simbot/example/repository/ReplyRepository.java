package simbot.example.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import simbot.example.entity.Reply;

/**
 * {@link Reply} 持久层交互
 *
 * @author ForteScarlet
 */
@Repository
public interface ReplyRepository extends R2dbcRepository<Reply, Long> {

    /**
     * 根据关键词查询结果
     *
     * @param keyword 关键词
     * @return found
     */
    Mono<Reply> findByKeyword(String keyword);


    /**
     * 根据关键词删除。
     *
     * @param keyword keyword
     * @return void
     */
    @Modifying
    Mono<Void> deleteByKeyword(String keyword);


}
