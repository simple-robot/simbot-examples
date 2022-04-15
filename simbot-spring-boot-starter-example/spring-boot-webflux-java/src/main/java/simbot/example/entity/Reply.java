package simbot.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 回复内容。
 *
 * @author ForteScarlet
 */
@Data
public class Reply {
    /**
     * id.
     */
    @Id
    private Long id;


    private String keyword;

    /**
     * 回复内容.
     */
    private String content;

}
