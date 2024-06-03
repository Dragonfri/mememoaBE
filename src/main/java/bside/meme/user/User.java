package bside.meme.user;

import bside.meme.content.Content;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id @Column(name = "user_id")
    private Long userId;
    private String name;
    private String email;
    private String profileIMG;
    private String accessToken;
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Content> contents = new ArrayList<>();
}
