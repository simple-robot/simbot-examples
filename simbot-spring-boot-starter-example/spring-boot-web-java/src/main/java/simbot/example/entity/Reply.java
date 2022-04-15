package simbot.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
