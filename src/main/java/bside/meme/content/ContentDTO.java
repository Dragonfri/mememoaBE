package bside.meme.content;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentDTO {
    private Long contentId;
    private List<String> tags;
    private String imageUrl;

    // 생성자, 게터 및 세터는 생략할 수 있습니다.
}