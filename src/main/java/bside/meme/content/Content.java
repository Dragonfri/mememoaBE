package bside.meme.content;

import bside.meme.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Content {
    @Id
    @GeneratedValue @Column(name = "content_id")
    private long contentId;

    private String title;
    private String imageAbsolutePath;
    private String imageRelativePath;
    private String detail;
    private LocalDateTime date;
    private int hit;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> Tags;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //1. 웃김, 2.고마움, 3.분노, 4.귀여운, 5.미안
    @Enumerated(EnumType.STRING)
    private Category category;
}
