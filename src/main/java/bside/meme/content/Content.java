package bside.meme.content;

import bside.meme.image.Image;
import bside.meme.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@SequenceGenerator(
        name = "content_seq_generator",
        sequenceName = "content_seq",
        initialValue = 100,
        allocationSize = 1
)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_seq_generator")
    @Column(name = "content_id")
    private long contentId;

    private String title;
    private String detail;
    private LocalDateTime date;
    @ColumnDefault("0")
    private int shareCnt;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> Tags;

    @ManyToOne
    @JsonIgnore // 무한 루프 방지를 위해 user 필드는 직렬화하지 않음
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "content")
    private List<Image> images = new ArrayList<>();

    //1. 웃김, 2.고마움, 3.분노, 4.귀여운, 5.미안
    @Enumerated(EnumType.STRING)
    private Category category;
}
