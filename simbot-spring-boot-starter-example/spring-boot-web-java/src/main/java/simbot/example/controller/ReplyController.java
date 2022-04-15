package simbot.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simbot.example.entity.Reply;
import simbot.example.service.ReplyService;

import java.util.List;

/**
 * @author ForteScarlet
 */
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;


    @GetMapping("/list")
    public List<Reply> replies(Reply condition) {
        return replyService.replies(condition);
    }

    @GetMapping("/keyword/{keyword}")
    public String reply(@PathVariable String keyword) {
        return replyService.reply(keyword);
    }

    @PostMapping
    public Reply add(@RequestBody Reply reply) {
        return replyService.addReply(reply.getKeyword(), reply.getContent());
    }

}
