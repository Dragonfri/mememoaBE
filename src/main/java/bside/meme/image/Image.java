package bside.meme.image;

import bside.meme.content.Content;
import bside.meme.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(
        name = "image_seq_generator",
        sequenceName = "image_seq",
        initialValue = 100,
        allocationSize = 1
)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_generator")
    @Column(name = "image_id")
    private Long imageId;
    private String oriName;
    private String newName;
    private String extension;
    private String url;

    @ManyToOne
    @JsonIgnore // 무한 루프 방지를 위해 user 필드는 직렬화하지 않음
    @JoinColumn(name = "content_id", nullable = true)
    private Content content;
}
