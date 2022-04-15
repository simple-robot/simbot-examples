package simbot.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import simbot.example.entity.Reply;
import simbot.example.repository.ReplyRepository;
import simbot.example.service.ReplyService;

/**
 * 回复服务接口实现。
 *
 * @author ForteScarlet
 */
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public Mono<String> reply(String keyword) {
        return replyRepository.findByKeyword(keyword).mapNotNull(Reply::getContent);
    }

    @Override
    public Mono<Reply> addReply(String keyword, String content) {
        final Reply entity = new Reply();
        entity.setKeyword(keyword);
        entity.setContent(content);
        return replyRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteReply(String keyword) {
        return replyRepository.deleteByKeyword(keyword);
    }

    @Override
    public Flux<Reply> replies(Reply condition) {
        if (condition == null) {
            return replyRepository.findAll();
        }

        final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("keyword", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("content", ExampleMatcher.GenericPropertyMatcher::contains);

        return replyRepository.findAll(Example.of(condition, exampleMatcher));
    }
}
