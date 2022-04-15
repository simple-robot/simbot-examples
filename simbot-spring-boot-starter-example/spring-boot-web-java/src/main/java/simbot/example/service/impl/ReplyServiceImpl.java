package simbot.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import simbot.example.entity.Reply;
import simbot.example.repository.ReplyRepository;
import simbot.example.service.ReplyService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 回复服务接口实现。
 *
 * @author ForteScarlet
 */
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final EntityManager entityManager;

    @Override
    public String reply(String keyword) {
        final Reply found = replyRepository.findByKeyword(keyword);
        return found == null ? null : found.getContent();
    }

    @Override
    public Reply addReply(String keyword, String content) {
        final Reply entity = new Reply();
        entity.setKeyword(keyword);
        entity.setContent(content);
        return replyRepository.save(entity);
    }

    @Override
    public void deleteReply(String keyword) {
        replyRepository.deleteByKeyword(keyword);
    }

    @Override
    public List<Reply> replies(Reply condition) {
        if (condition == null) {
            return replyRepository.findAll();
        }

        final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("keyword", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("content", ExampleMatcher.GenericPropertyMatcher::contains);

        return replyRepository.findAll(Example.of(condition, exampleMatcher));
    }
}
