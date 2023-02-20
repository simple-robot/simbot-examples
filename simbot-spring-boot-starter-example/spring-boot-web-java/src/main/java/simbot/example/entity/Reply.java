package simbot.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 回复内容。
 *
 * @author ForteScarlet
 */
@Data
@Entity
@Table
public class Reply {
    /**
     * id.
     */
    @Id
    @GeneratedValue
    private Long id;


    private String keyword;

    /**
     * 回复内容.
     */
    private String content;

}
