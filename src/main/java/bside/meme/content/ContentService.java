package bside.meme.content;

import bside.meme.user.User;
import bside.meme.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    public Content createContent(Content content, Long userId) {
        return contentRepository.save(content);
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            content.setUser(userOptional.get());
//            return contentRepository.save(content);
//        } else {
//            throw new RuntimeException("User not found");
//        }
    }

    public Page<Content> getAllContents(Pageable pageable) {
        return contentRepository.findAll(pageable);
    }

    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    public Content updateContent(Long id, Content contentDetails) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            content.setTitle(contentDetails.getTitle());
            content.setDetail(contentDetails.getDetail());
            content.setDate(contentDetails.getDate());
            content.setShareCnt(contentDetails.getShareCnt());
            content.setTags(contentDetails.getTags());
            content.setCategory(contentDetails.getCategory());
            return contentRepository.save(content);
        } else {
            throw new RuntimeException("Content not found");
        }
    }

    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

    public Page<Content> searchContentsByTitlePaginated(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contentRepository.findByTitleContaining(title, pageable);
    }

    public Page<Content> searchContentsByTagsPaginated(List<String> tags, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contentRepository.findAll(ContentSpecification.hasTags(tags), pageable);
    }

    public Page<Content> getContentsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contentRepository.findAll(pageable);
    }

//    public Page<ContentDTO> getPaginatedContents(Pageable pageable) {
//        Page<Content> contentsPage = contentRepository.findAll(pageable);
//        return contentsPage.map(this::mapToContentDTO);
//    }

    private ContentDTO mapToContentDTO(Content content) {
        ContentDTO dto = new ContentDTO();
        dto.setContentId(content.getContentId());
        dto.setTags(content.getTags());
        if (!content.getImages().isEmpty()) {
            dto.setImageUrl(content.getImages().get(0).getUrl());
        }
        return dto;
    }
    public Page<Content> searchContentsByTitleAndTagsPaginated(String title, List<String> tags, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contentRepository.findByTitleContainingAndTagsIn(title, tags, pageable);
    }
    public Page<Content> searchContentsByCategoryPaginated(Category category, int page, int size) {
        return contentRepository.findByCategory(category, PageRequest.of(page, size));
    }
}
